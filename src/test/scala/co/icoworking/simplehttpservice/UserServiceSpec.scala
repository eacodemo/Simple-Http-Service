package co.icoworking.simplehttpservice

import cats.effect.IO
import co.icoworking.simplehttpservice.service.UserService
import org.http4s.{Method, Request, Response, Status}
import org.http4s.implicits.uri
import munit.CatsEffectSuite

class UserServiceSpec extends CatsEffectSuite:

  test("UserService returns status code 200") {
    assertIO(userService.map(_.status) ,Status.Ok)
  }

  private[this] val userService: IO[Response[IO]] =
    val getHW = Request[IO](Method.GET, uri"/userservice/0")
    val userService = UserService.impl[IO]
    SimplehttpserviceRoutes.userServiceRoutes(userService).orNotFound(getHW)