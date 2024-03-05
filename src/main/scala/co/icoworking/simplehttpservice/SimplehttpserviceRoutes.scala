package co.icoworking.simplehttpservice

import cats.effect.Sync
import cats.syntax.all.*
import co.icoworking.simplehttpservice.service.{HelloWorld, UserService}
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object SimplehttpserviceRoutes:

  def helloWorldRoutes[F[_]: Sync](H: HelloWorld[F]): HttpRoutes[F] =
    val dsl = new Http4sDsl[F]{}
    import dsl.*
    HttpRoutes.of[F] {
      case GET -> Root / "hello" / name =>
        for {
          greeting <- H.hello(HelloWorld.Name(name))
          resp <- Ok(greeting)
        } yield resp
    }

  def userServiceRoutes[F[_] : Sync](userService: UserService[F]): HttpRoutes[F] =
    val dsl = new Http4sDsl[F] {}
    import dsl.*
    HttpRoutes.of[F] {
      case GET -> Root / "userservice" / id =>
        for {
          user     <- userService.findUser(id.toInt)
          response <- Ok(user)
        } yield response
    }