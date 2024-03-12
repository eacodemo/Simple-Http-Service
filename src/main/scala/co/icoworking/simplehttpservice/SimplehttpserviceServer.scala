package co.icoworking.simplehttpservice

import cats.effect.Async
import cats.implicits.toSemigroupKOps
import co.icoworking.simplehttpservice.service.{HelloWorld, UserService}
import com.comcast.ip4s.*
import fs2.io.net.Network
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger

object SimplehttpserviceServer:

  def run[F[_]: Async: Network]: F[Nothing] = {
    for {
      client <- EmberClientBuilder.default[F].build // todo Change me
      helloWorldAlg: HelloWorld[F] = HelloWorld.impl[F]
      // user http service
      userService = UserService.impl[F]

      httpApp = (
        SimplehttpserviceRoutes.helloWorldRoutes[F](helloWorldAlg) <+>
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