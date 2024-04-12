package co.icoworking.simplehttpservice

import cats.effect.kernel.Resource
import cats.effect.{Async}
import co.icoworking.simplehttpservice.repository.{HistoriaUsuarioRepository, ProyectoRepository, TareaRepository, UserRepository}
import co.icoworking.simplehttpservice.service.{HistoriaUsuarioService, ProyectoService, TareaService, UserService}
import com.comcast.ip4s.*
import doobie.Transactor
import fs2.io.net.Network
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger
import cats.implicits.toSemigroupKOps
import org.flywaydb.core.Flyway
import org.http4s.HttpApp

import scala.annotation.nowarn

object SimplehttpserviceServer:
  def run[F[_]: Async: Network]: F[Nothing] = {
    val DriverJDBC = "org.postgresql.Driver"
    val URLJDBC = "jdbc:postgresql:world"
    val UserDabase = "postgres"
    val PasswordDabase = "password"
    def runMigrations(): Unit =
      val uri = "classpath:" + "db.migration"
      Flyway
        .configure()
        .dataSource(
          URLJDBC,
          UserDabase,
          PasswordDabase
        )
        .locations(uri)
        .load()
        .migrate()
      ()

    def connectionWithDabase = { // solo y solo una unica responsabilidad!!
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
      @nowarn val proyectosService = ProyectoService.impl[F](pr = new ProyectoRepository(xa))
      @nowarn val historiaUService = HistoriaUsuarioService.impl[F](har = new HistoriaUsuarioRepository(xa))
      
      val httpApp = (
        SimplehttpserviceRoutes.userServiceRoutes[F](userService) <+>
          SimplehttpserviceRoutes.tareaServiceRoutes[F](tareaService)//<+>
          //SimplehttpserviceRoutes.ProyectoServiceRoutes[F](proyectosService)<+>
          //SimplehttpserviceRoutes.HistoriaUsuarioServiceRoutes[F](historiaUService)
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
    } yield {
      runMigrations()
      ()
    } // definicion de application // type Safe - > Nivel de los tipos

    application.useForever // arrancamos el motor - app
  }
