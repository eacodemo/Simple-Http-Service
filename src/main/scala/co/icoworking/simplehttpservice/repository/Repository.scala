package co.icoworking.simplehttpservice.repository

import doobie.util.transactor.Transactor
import cats.*
import cats.effect.*
import co.icoworking.simplehttpservice.model.*
import cats.effect.Async
import doobie._
import doobie.implicits._

object Tables{
  val UserTableName = "Usuario"
}
// Interface CRUD para Usuario
trait UserRepositoryI[F[_] : Applicative]:
  def saveUser(usuarioParaGuardar: Usuario): F[Usuario]
  def findById(id: Int): F[Usuario]
  def update(id: Int, updateData: Usuario): F[Usuario]
  def deleteUser(id: Int): F[Unit]

trait TareaRepositoryI[F[_]: Applicative]:
  def saveTarea(guardarTarea: Tarea): F[Tarea]
  def encontrarPorId(id: Int): F[Tarea]
  
class TareaRepository[F[_]: Applicative: Async](transactor: Transactor[F]) extends TareaRepositoryI[F]:
  def saveTarea(guardarTarea: Tarea): F[Tarea] =
    val insertNewTareaSql = sql"INSERT INTO Tarea ()".query[Tarea].unique
    insertNewTareaSql.transact(transactor)
  def encontrarPorId(id: Int): F[Tarea] = ???


class UserRepository[F[_] : Applicative: Async](transactor: Transactor[F]) extends UserRepositoryI[F]:
  override def saveUser(usuarioParaGuardar: Usuario): F[Usuario] =
    val insertNewUserSQL = sql"INSERT INTO ${Tables.UserTableName} ".query[Usuario].unique
    insertNewUserSQL.transact(transactor)

  override def findById(id: Int): F[Usuario] = ???
  override def update(id: Int, updateData: Usuario): F[Usuario] = ???
  override def deleteUser(id: Int): F[Unit] = ???
