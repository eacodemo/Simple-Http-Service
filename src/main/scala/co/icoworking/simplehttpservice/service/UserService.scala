package co.icoworking.simplehttpservice.service

import cats.Applicative
import cats.effect.IO
import cats.syntax.all.*
import co.icoworking.simplehttpservice.model.*
import co.icoworking.simplehttpservice.repository.UserRepository
import cats.effect._
import cats.effect.syntax.all._
// Iterface de Usuarion HTTP API
trait UserService[F[_]: Applicative]:
  def findUser(id: Int): F[Usuario] // HTTP GET
  def saveUser(u: Usuario): F[Usuario] // HTTP POST

class UserServiceImpl[F[_] : Applicative](userRepository: UserRepository[F]) extends UserService[F]:
  override def findUser(id: Int): F[Usuario] =
    val user = Usuario(
      ID = 0,
      nombreUsuario = "Dawid",
      email = "mail@mail.co",
      contraseña = "contra",
      tipo = "tipoDeUser"
    )
    val usarioEncontrado = userRepository.findById(id)
    println(s"$usarioEncontrado")
    usarioEncontrado // fixed!!!! violaaa!
    //user.pure[F] // fix me

  override def saveUser(u: Usuario): F[Usuario] = ??? // implemnt me

object UserService:
  def impl[F[_] : Applicative](ur: UserRepository[F]): UserService[F] = new UserServiceImpl[F](userRepository = ur)
