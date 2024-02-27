package co.icoworking.simplehttpservice.service

import cats.Applicative
import cats.syntax.all.*

import co.icoworking.simplehttpservice.model._

trait UserService[F[_]]:
  def user(id: Int): F[Usuario]


object UserService:
  def impl[F[_] : Applicative]: UserService[F] = new UserService[F]:

    def user(id: Int): F[Usuario] =
      // ??? llamar DB y obtern el resultado ?? - por composicion
      // val quizaUser = repo.findUserById(id)
      Usuario(
        ID = 0,
        nombreUsuario = "Dawid",
        email = "mail@mail.co",
        contraseña = "contra",
        tipo = "tipoDeUser"
      ).pure[F]