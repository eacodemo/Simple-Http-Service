package co.icoworking.simplehttpservice.model

import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe.*

final case class Usuario(ID: Int,
                   nombreUsuario: String,
                   email: String,
                   contraseña: String,
                   tipo: String)
object Usuario: // objecto companiero de ADT Usuario
  given Encoder[Usuario] = new Encoder[Usuario]: // Serializacion por Circe
    final def apply(usuario: Usuario): Json = Json.obj(
      ("message", Json.fromString(usuario.tipo)),
    )
  given [F[_]]: EntityEncoder[F, Usuario] = jsonEncoderOf[F, Usuario] // instancia implicit with given Scala 3 way

//case class Tarea(ID: Int, descripcion: String, estado: String, IDHistoriaUsuario: Int)
//case class Proyecto(ID: Int, nombre: String, descripcion: Option[String], fechaInicio: Option[Date])
//case class AsociacionProyectoUsuario(ID: Int, IDProyecto: Int, IDUsuario: Int, Rol: String)
//case class HistoriaUsuario(ID: Int, detalles: String, criteriosAceptacion: String, estado: String, IDProyecto: Int, IDTareaAsociada: Option[Int])
