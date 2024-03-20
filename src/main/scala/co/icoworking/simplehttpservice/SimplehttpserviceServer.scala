package co.icoworking.simplehttpservice

import cats.effect.{Async, IO}
import co.icoworking.simplehttpservice.service.{UserService}
import com.comcast.ip4s.*
import doobie.Transactor
import doobie.util.transactor.Transactor.Aux
import fs2.io.net.Network
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger

object SimplehttpserviceServer:
  def run[F[_]: Async: Network]: F[Nothing] = {
    for {
      client <- EmberClientBuilder.default[F].build // todo Change me
      //helloWorldAlg: HelloWorld[F] = HelloWorld.impl[F]

      xa = Transactor.fromDriverManager[F](
        driver = "org.postgresql.Driver", // JDBC driver classname
        url = "jdbc:postgresql:world",    // Connect URL
        user = "postgres",                // Database user name
        password = "password",            // Database password
        logHandler = None                 // Don't setup logging for now. See Logging page for how to log events in detail
      )
      repositorioUser = new co.icoworking.simplehttpservice.repository.UserRepository(xa)
      // user http service
      userService = UserService.impl[F](ur = repositorioUser)

      httpApp = (
        //SimplehttpserviceRoutes.helloWorldRoutes[F](helloWorldAlg) <+>
          SimplehttpserviceRoutes.userServiceRoutes[F](userService)
      ).orNotFound

      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      _ <- 
        EmberServerBuilder.default[F]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(finalHttpApp)
          .build
    } yield ()
  }.useForever
