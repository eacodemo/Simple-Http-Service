package co.icoworking.simplehttpservice.service


import cats.Applicative
import co.icoworking.simplehttpservice.repository.TareaRepository
//import cats.effect.IO
import cats.syntax.all.*
import co.icoworking.simplehttpservice.model.*

trait TareaService[F[_]]:
  def encontrarTarea(id: Int): F[Tarea]

class TareaServiceImpl[F[_]: Applicative](tareaRepository: TareaRepository) extends TareaService[F]:
  def encontrarTarea(id: Int): F[Tarea] = {
    val tarea = Tarea (
      ID = 1,
      descripcion = "Primer Tarea",
      estado = "en progreso",
      IDHistoriaUsuario =  1
    )
    val tareaEncontrada = tareaRepository.encontrarPorId(id)
    println(s"$tareaEncontrada")
    tarea.pure[F]
  }


object TareaService:
  def impl[F[_] : Applicative](tr: TareaRepository): TareaService[F] = new TareaServiceImpl[F](tareaRepository = tr) {}

//context bound
// idiomatic

