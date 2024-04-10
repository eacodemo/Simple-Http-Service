package co.icoworking.simplehttpservice.model

import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe.*
import java.sql.Date

final case class Usuario(
                          id: Int,
                          nombre: String,
                          email: String,
                          contraseña: String,
                          tipo: String
                        )
object Usuario: // objecto companiero de ADT Usuario
  given Encoder[Usuario] = new Encoder[Usuario]: // Serializacion por Circe
    final def apply(usuario: Usuario): Json = Json.obj(
      ("tipo", Json.fromString(usuario.tipo)),
    )
  given [F[_]]: EntityEncoder[F, Usuario] = jsonEncoderOf[F, Usuario] // instancia implicit with given Scala 3 way

case class Tarea(
                  id: Int,
                  descripcion: String,
                  estado: String,
                  idHistoriaUsuario: Int
                )
object Tarea:
  given Encoder[Tarea] = new Encoder[Tarea]:
    override def apply(tarea: Tarea): Json = Json.obj(
      ("Estado", Json.fromString(tarea.estado)),
    )

  given [F[_]]: EntityEncoder[F, Tarea] = jsonEncoderOf[F, Tarea]

case class Proyecto(
                     id: Int,
                     nombre: String,
                     descripcion: Option[String],
                     contraseña: String,
                     fechaInicio: Option[Date]
                   )
object Proyecto:
  given Encoder[Proyecto] = new Encoder[Proyecto]:
    override def apply(proyecto: Proyecto): Json = Json.obj(
      ("id", Json.fromInt(proyecto.id)),
      ("nombre", Json.fromString(proyecto.nombre)),
    )
  given [F[_]]: EntityEncoder[F, Proyecto] = jsonEncoderOf[F,Proyecto]

case class HistoriaUsuario(id: Int,
                           detalles: String,
                           criteriosAceptacion: String,
                           estado: String,
                           idProyecto: Int,
                           idTareaAsociada: Option[Int]
                          )
object HistoriaUsuario:
  given Encoder[HistoriaUsuario] = new Encoder[HistoriaUsuario]:
    override def apply(ha: HistoriaUsuario): Json = Json.obj(
      ("id", Json.fromInt(ha.id)),
      ("detalles", Json.fromString(ha.detalles)),
      ("criterios de aceptacion", Json.fromString(ha.criteriosAceptacion)),
      ("estado", Json.fromString(ha.estado)),
    )
  given [F[_]]: EntityEncoder[F, HistoriaUsuario] = jsonEncoderOf[F,HistoriaUsuario]

//case class AsociacionProyectoUsuario(ID: Int, IDProyecto: Int, IDUsuario: Int, Rol: String)
