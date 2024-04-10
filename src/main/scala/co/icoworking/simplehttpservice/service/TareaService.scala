package co.icoworking.simplehttpservice.service

import cats.Applicative
import co.icoworking.simplehttpservice.repository.TareaRepository
import co.icoworking.simplehttpservice.model.*

trait TareaService[F[_]]:
  def encontrarTarea(id: Int): F[Tarea]

class TareaServiceImpl[F[_]: Applicative](tareaRepository: TareaRepository[F]) extends TareaService[F]:
  def encontrarTarea(id: Int): F[Tarea] = tareaRepository.findById(id)

object TareaService:
  def impl[F[_] : Applicative](tr: TareaRepository[F]): TareaService[F] = new TareaServiceImpl[F](tareaRepository = tr) {}
