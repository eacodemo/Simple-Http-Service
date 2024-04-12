package co.icoworking.simplehttpservice.service

import cats.Applicative
import co.icoworking.simplehttpservice.model.*
import co.icoworking.simplehttpservice.repository.ProyectoRepository

trait ProyectoService[F[_]: Applicative]:
  def findProjectById(id: Int): F[Proyecto]
  def saveProject(guardarProyecto: Proyecto): F[Proyecto]
 

class ProyectoServiceImpl[F[_] : Applicative](proyectoRepository: ProyectoRepository[F]) extends ProyectoService[F]:
  override def findProjectById(id: Int): F[Proyecto] = proyectoRepository.findById(id)
  override def saveProject(guardarProyecto: Proyecto): F[Proyecto] = proyectoRepository.saveProject(guardarProyecto)
 

object ProyectoService:
  def impl[F[_] : Applicative](pr: ProyectoRepository[F]): ProyectoService[F] = new ProyectoServiceImpl[F](proyectoRepository = pr)
