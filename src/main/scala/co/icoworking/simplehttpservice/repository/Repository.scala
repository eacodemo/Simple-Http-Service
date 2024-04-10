package co.icoworking.simplehttpservice.repository

import doobie.util.transactor.Transactor
import cats.*
import cats.effect.*
import co.icoworking.simplehttpservice.model.*
import cats.effect.Async
import doobie._
import doobie.implicits._
import cats.syntax.functor._

object Tables{
  val UserTableName = "Usuario"
  val TareaTableName = "Tarea"
}
// Interface CRUD para Usuario
trait UserRepositoryI[F[_] : Applicative]:
  def saveUser(usuarioParaGuardar: Usuario): F[Usuario]
  def findById(id: Int): F[Option[Usuario]]
  def update(id: Int, updateData: Usuario): F[Usuario]
  def deleteUser(id: Int): F[Unit]

trait TareaRepositoryI[F[_]: Applicative]:
  def saveTarea(guardarTarea: Tarea): F[Tarea]
  def findById(id: Int): F[Tarea]

trait ProyectoRepositoryI[F[_]: Applicative]:
  def saveProject(guardarProyecto: Proyecto): F[Proyecto]
  def findById(id: Int): F[Proyecto]

trait HistoriaUsuarioRepositoryI[F[_] : Applicative]:
  def saveHA(guardarHA: HistoriaUsuario): F[HistoriaUsuario]
  def findById(id: Int): F[HistoriaUsuario]

class UserRepository[F[_] : Applicative: Async](transactor: Transactor[F]) extends UserRepositoryI[F]:
  
  override def saveUser(usuarioParaGuardar: Usuario): F[Usuario] =
    val insertNewUserSQL =
      (sql"INSERT INTO ${Tables.UserTableName} (id, nombre, email, contraseña, tipo) " ++
        sql"VALUES (${usuarioParaGuardar.id}, ${usuarioParaGuardar.nombre}, ${usuarioParaGuardar.email}, ${usuarioParaGuardar.contraseña}, ${usuarioParaGuardar.tipo})")
        .update
        .withUniqueGeneratedKeys[Usuario]("id", "nombre", "email", "contraseña", "tipo")
    insertNewUserSQL.transact(transactor)

  override def findById(id: Int): F[Option[Usuario]] =
    val findUserById = sql"SELECT id, nombre, email, contraseña, tipo FROM ${Tables.UserTableName} WHERE id = $id"
      .query[Usuario]
      .option
    findUserById.transact(transactor)
    
  override def update(id: Int, updateData: Usuario): F[Usuario] = ???
  override def deleteUser(id: Int): F[Unit] = 
    val deleteUser =
      sql"DELETE FROM ${Tables.UserTableName} WHERE id = $id"
        .update
        .run
        .transact(transactor)
        .void
    deleteUser


class TareaRepository[F[_]: Applicative: Async](transactor: Transactor[F]) extends TareaRepositoryI[F]:
  override def saveTarea(guardarTarea: Tarea): F[Tarea] =
    val insertNewTareaSql = sql"INSERT INTO Tarea ()".query[Tarea].unique
    insertNewTareaSql.transact(transactor)
  override def findById(id: Int): F[Tarea] = ???


class ProyectoRepository[F[_]: Applicative: Async](transactor: Transactor[F]) extends  ProyectoRepositoryI[F]:
  override def saveProject(guardarProyecto: Proyecto): F[Proyecto] = ???
  override def findById(id: Int): F[Proyecto] = ???

class HistoriaUsuarioRepository[F[_]: Applicative: Async](transactor: Transactor[F]) extends  HistoriaUsuarioRepositoryI[F]:
  override def saveHA(guardarHA: HistoriaUsuario): F[HistoriaUsuario] = ???
  override def findById(id: Int): F[HistoriaUsuario] = ???