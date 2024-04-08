package co.icoworking.simplehttpservice

import cats.effect.kernel.Resource
import cats.effect.Async
import co.icoworking.simplehttpservice.repository.TareaRepository
import co.icoworking.simplehttpservice.service.{TareaService, UserService}
import com.comcast.ip4s.*
import doobie.Transactor
import fs2.io.net.Network
//import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger
import cats.implicits.toSemigroupKOps
import org.http4s.HttpApp

object SimplehttpserviceServer:
  def run[F[_]: Async: Network]: F[Nothing] = {

    val init: HttpApp[F] = {
      val xa = Transactor.fromDriverManager[F](
        driver = "org.postgresql.Driver", // JDBC driver classname
        url = "jdbc:postgresql:world", // Connect URL
        user = "postgres", // Database user name
        password = "password", // Database password
        logHandler = None // Don't setup logging for now. See Logging page for how to log events in detail
      )
      // user http service
      val userService = UserService.impl[F](ur = new co.icoworking.simplehttpservice.repository.UserRepository(xa))
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
      _ <- EmberServerBuilder.default[F]
          .withHost(IpHttp)
          .withPort(PortHttp)
          .withHttpApp(init)
          .build
    } yield () // definicion de application // type Safe - > Nivel de los tipos

    application.useForever // arrancamos el motor - app
  }
