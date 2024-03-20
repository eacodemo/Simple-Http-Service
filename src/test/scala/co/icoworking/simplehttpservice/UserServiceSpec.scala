//package co.icoworking.simplehttpservice
//
//import cats.effect.IO
//import co.icoworking.simplehttpservice.repository.UserRepository
//import co.icoworking.simplehttpservice.service.UserService
//import org.http4s.{Method, Request, Response, Status}
//import org.http4s.implicits.uri
//import munit.CatsEffectSuite
//import munit.Clue.generate
//
//class UserServiceSpec extends CatsEffectSuite:

  //test("UserService returns status code 200") {
    //assertIO(userService.map(_.status) ,Status.Ok)
  //}

  //private[this] val userService: IO[Response[IO]] =
    //val getHW = Request[IO](Method.GET, uri"/userservice/0")
    //val userService: UserRepository[IO] => UserService[IO] = UserService.impl[IO]
//    xa = Transactor.fromDriverManager[IO](
//      driver = "org.postgresql.Driver", // JDBC driver classname
//      url = "jdbc:postgresql:world", // Connect URL
//      user = "postgres", // Database user name
//      password = "password", // Database password
//      logHandler = None // Don't setup logging for now. See Logging page for how to log events in detail
//    )
//    val repositorioUser = new co.icoworking.simplehttpservice.repository.UserRepository(xa)
//    SimplehttpserviceRoutes.userServiceRoutes(userService(repositorioUser)).orNotFound(getHW)