//import io.circe._, io.circe.parser._
//
//val rawJson: String = """
//{
//  "foo": "bar",
//  "baz": 123,
//  "list of stuff": [ 4, 5, 6 ]
//}
//"""
//val parseResult: Either[ParsingFailure, Json] = io.circe.parser.parse(rawJson)
//
//case class Data(json: Json, edad: Int)
//
//val r: Either[ParsingFailure, Data] = for {
//  a     <- parseResult
//  val x = 1
//} yield Data( json = a, edad = x)
//
//
//val k: Unit = println("hey!")
//
//val ioa = IO.println("hey!")
//
//val program = for {
//   _ <- ioa
//   _ <- ioa
//  } yield ()
//program.unsafeRunSync()


// Como funciona Doobie ???
import doobie.*
import doobie.implicits.*
import cats.*
import cats.effect.*
import cats.implicits.*
import doobie.util.transactor.Transactor.Aux

// preparar datos en un Effecto
val program1: doobie.ConnectionIO[Int] = 42.pure[ConnectionIO]

import doobie.util.ExecutionContexts
import cats.effect.unsafe.implicits.global
// preparar transactor
val xa = Transactor.fromDriverManager[IO](
  driver = "org.postgresql.Driver",  // JDBC driver classname
  url = "jdbc:postgresql:world",     // Connect URL
  user = "postgres",                 // Database user name
  password = "password",             // Database password
  logHandler = None                  // Don't setup logging for now. See Logging page for how to log events in detail
)

// pasar xa y ejectuar - teneindo cuenta que todavia todo esta
// dentro de IO (es decir que finalmente no se ejecuto)
val k: IO[Int] = program1.transact(xa)

val kk: IO[(Int,Int)] = for {
//  a <- program1.transact(xa)
  val ab = a + 1 // tengo un GRAN PODER - puedo cambiar el mundo (estado) sin efectivamente ejecutarlo - entonces son mis deseos
} yield (ab,a) // pero no pierdo IO (deseos) - no se ejecutor
// AGILE - nos permite ir adelante
// Siempre falla lo mas temprano posible


kk.unsafeRunSync() // aqui CPU RAM LINUX JVM -> PRoduce Resultados - ejecutaa programa