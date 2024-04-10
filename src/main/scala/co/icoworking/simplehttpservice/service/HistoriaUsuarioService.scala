package co.icoworking.simplehttpservice.service

import cats.Applicative
import co.icoworking.simplehttpservice.model.*
import co.icoworking.simplehttpservice.repository.HistoriaUsuarioRepository

trait HistoriaUsuarioService[F[_]: Applicative]:
  def findProjectById(id: Int): F[HistoriaUsuario]
  def saveHU(guardarHU: HistoriaUsuario): F[HistoriaUsuario]
 

class HistoriaUSuarioServiceImpl[F[_] : Applicative](historiaUsuarioRepository: HistoriaUsuarioRepository[F]) extends HistoriaUsuarioService[F]:
  override def findProjectById(id: Int): F[HistoriaUsuario] = historiaUsuarioRepository.findById(id)
  override def saveHU(guardarHU: HistoriaUsuario): F[HistoriaUsuario] = historiaUsuarioRepository.saveHU(guardarHU)
 

object HistoriaUsuarioService:
  def impl[F[_] : Applicative](har: HistoriaUsuarioRepository[F]): HistoriaUsuarioService[F] = 
    new HistoriaUSuarioServiceImpl[F](historiaUsuarioRepository = har)
