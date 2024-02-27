package co.icoworking.simplehttpservice

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple:
  val run = SimplehttpserviceServer.run[IO]
