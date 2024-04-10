package co.icoworking.simplehttpservice.service

import cats.Applicative
import co.icoworking.simplehttpservice.model.*
import co.icoworking.simplehttpservice.repository.UserRepository
// Iterface de Usuarion HTTP API
trait UserService[F[_]: Applicative]:
  def findUserById(id: Int): F[Option[Usuario]]
  def saveUser(u: Usuario): F[Usuario]

class UserServiceImpl[F[_] : Applicative](userRepository: UserRepository[F]) extends UserService[F]:
  override def findUserById(id: Int): F[Option[Usuario]] = userRepository.findById(id)
  override def saveUser(u: Usuario): F[Usuario] = userRepository.saveUser(u)// implemnt me

object UserService:
  def impl[F[_] : Applicative](ur: UserRepository[F]): UserService[F] = new UserServiceImpl[F](userRepository = ur)
