package co.icoworking.simplehttpservice

import cats.effect.Sync
import cats.syntax.all.*
import co.icoworking.simplehttpservice.model.{Tarea, Usuario}
import co.icoworking.simplehttpservice.service.{HelloWorld, UserService}
import co.icoworking.simplehttpservice.service.TareaService
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
          user: Usuario <- userService.findUser(id.toInt)
          response      <- Ok(user)
        } yield response
    }
    
    
  def tareaServiceRoutes[F[_] : Sync](tareaService: TareaService[F]): HttpRoutes[F] =
    val dsl = new Http4sDsl[F] {}
    import dsl.*
    HttpRoutes.of[F] {
      case GET -> Root / "tareaservice" / id =>  //URI
        for {
          tarea: Tarea <- tareaService.encontrarTarea(id.toInt)
          response     <- Ok(tarea)
        } yield response
    }