package co.icoworking.simplehttpservice.config

import metaconfig._
import metaconfig.generic._

final case class DatabaseConfiguration(
    driver: String,
    url: String,
    user: String,
    password: String)

object DatabaseConfiguration:
  implicit lazy val surface: Surface[DatabaseConfiguration] = deriveSurface[DatabaseConfiguration]
  val defaultDriver = "org.postgresql.Driver"
  val defaultUrl = "jdbc:postgresql:world"
  val user = "postgres"
  val password = "password"
