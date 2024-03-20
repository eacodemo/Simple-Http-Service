package co.icoworking.simplehttpservice.repository

import doobie.util.transactor.Transactor
import cats.*
//import cats.effect.IO
//import cats.implicits.*
import cats.effect.*
import co.icoworking.simplehttpservice.model.*
//import doobie.util.transactor.Transactor.Aux
//import doobie.WeakAsync.doobieWeakAsyncForAsync
//import fs2.Compiler.Target.forConcurrent
//import fs2.Compiler.Target.forSync
import cats.effect.Async
//import cats.effect.syntax.all._
import doobie._
import doobie.implicits._

// Interface CRUD para Usuario
trait UserRepositoryI[F[_] : Applicative]:
  def saveUser(usuarioParaGuardar: Usuario): F[Usuario]
  def findById(id: Int): F[Usuario]
  def update(id: Int, updateData: Usuario): F[Usuario]
  def deleteUser(id: Int): F[Unit]

class UserRepository[F[_] : Applicative: Async](transactor: Transactor[F]) extends UserRepositoryI[F]:
  override def saveUser(usuarioParaGuardar: Usuario): F[Usuario] =
    val insertNewUserSQL = sql"INSERT INTO ...".query[Usuario].unique
    //insertNewUserSQL.transact(transactor) // executa SQL en IO!!!!
    //val program1: doobie.ConnectionIO[Int] = 42.pure[ConnectionIO]
    println(s"$transactor $usuarioParaGuardar $insertNewUserSQL")
    insertNewUserSQL.transact(transactor)

  override def findById(id: Int): F[Usuario] = ???
  override def update(id: Int, updateData: Usuario): F[Usuario] = ???
  override def deleteUser(id: Int): F[Unit] = ???
