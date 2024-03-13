package co.icoworking.simplehttpservice.repository

import doobie.util.transactor.Transactor
import doobie.*
import doobie.implicits.*
import cats.*
import cats.effect.IO
import co.icoworking.simplehttpservice.model.*
import doobie.util.transactor.Transactor.Aux

// Interface CRUD para Usuario
trait UserRepositoryI:
  def saveUser(usuarioParaGuardar: Usuario): IO[Usuario]
  def findById(id: Int): IO[Usuario]
  //def update(id: Int, updateData: Usuario): IO[Usuario]
  //def deleteUser(id: Int): IO[Unit]

class UserRepository(transactor: Aux[IO, Unit]) extends UserRepositoryI:
  def saveUser(usuarioParaGuardar: Usuario): IO[Usuario] =
    val insertNewUserSQL = sql"INSERT INTO ...".query[Usuario].unique
    //insertNewUserSQL.transact(transactor) // executa SQL en IO!!!!
    //val program1: doobie.ConnectionIO[Int] = 42.pure[ConnectionIO]
    println(s"$transactor $usuarioParaGuardar $insertNewUserSQL")
    insertNewUserSQL.transact(transactor)

  override def findById(id: Int): IO[Usuario] = ???

//  override def update(id: Int, updateData: Usuario): IO[Usuario] = ???

//  override def deleteUser(id: Int): IO[Unit] = ???
