package co.icoworking.simplehttpservice

import cats.effect.kernel.Resource
import cats.effect.Async
import co.icoworking.simplehttpservice.repository.{TareaRepository, UserRepository}
import co.icoworking.simplehttpservice.service.{TareaService, UserService}
import com.comcast.ip4s.*
import doobie.Transactor
import fs2.io.net.Network
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger
import cats.implicits.toSemigroupKOps
import org.http4s.HttpApp

object SimplehttpserviceServer:
  def run[F[_]: Async: Network]: F[Nothing] = {

    def connectionWithDabase = { // solo y solo una unica responsabilidad!!
      val DriverJDBC = "org.postgresql.Driver"
      val URLJDBC = "jdbc:postgresql:world"
      val UserDabase = "postgres"
      val PasswordDabase = "password"
      Transactor.fromDriverManager[F](
        driver     = DriverJDBC, // JDBC driver classname
        url        = URLJDBC, // Connect URL
        user       = UserDabase, // Database user name
        password   = PasswordDabase, // Database password
        logHandler = None // Don't setup logging for now. See Logging page for how to log events in detail
      )
    }

    val init: HttpApp[F] = {
      val xa = connectionWithDabase
      // user http service
      val userService = UserService.impl[F](ur = new UserRepository(xa))
      val tareaService = TareaService.impl[F](tr = new TareaRepository(xa))

      val httpApp = (
        SimplehttpserviceRoutes.userServiceRoutes[F](userService) <+>
          SimplehttpserviceRoutes.tareaServiceRoutes[F](tareaService)
        ).orNotFound

      Logger.httpApp(true, true)(httpApp)
    }
    val IpHttp: Ipv4Address = ipv4"0.0.0.0"
    val PortHttp: Port = port"8080"
    val application: Resource[F, Unit] = for {
      // Integrar Flyway DB DDL SQL 
      _ <- EmberServerBuilder.default[F]
          .withHost(IpHttp)
          .withPort(PortHttp)
          .withHttpApp(init)
          .build
    } yield () // definicion de application // type Safe - > Nivel de los tipos

    application.useForever // arrancamos el motor - app
  }
