val Http4sVersion = "0.23.25"
val CirceVersion = "0.14.6"
val MunitVersion = "0.7.29"
val LogbackVersion = "1.4.14"
val MunitCatsEffectVersion = "1.0.7"
val doobieVersion = "1.0.0-RC5"

val metalConfVersion = "0.12.0"
lazy val root = (project in file("."))
  .settings(
    organization := "co.icoworking",
    name := "simple-http-service",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "3.3.0",
    libraryDependencies ++= Seq(
      "io.circe"        %% "circe-parser"        % CirceVersion,  // serializacion desde objectos -> JSON (text que se utliza para Intercomunicacion entre API)
      "org.http4s"      %% "http4s-ember-client" % Http4sVersion, // Cliente HTTP Consumir/Integrar HTTP Requests
      "org.http4s"      %% "http4s-ember-server" % Http4sVersion, // Servidor HTTP Exponer/Producir GET/POST/PUT/DELETE... Peticiones/Response
      "org.http4s"      %% "http4s-circe"        % Http4sVersion, // Serializar ???
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion, // DSL HTTP routes
      // dependencia de conexion con DB
      "org.tpolecat"    %% "doobie-core"        % doobieVersion, // Doobie CORE SQL TX
      "org.tpolecat"    %% "doobie-postgres"    % doobieVersion, // conecion con SQL POTGRES
      "org.tpolecat"    %% "doobie-specs2"      % doobieVersion          % Test, // para Testos unitarion
      "com.geirsson" %% "metaconfig-core"        % metalConfVersion, // conf serializer from FS => ADT
      // logs de tiempo de arrance - (ITOPS / DevOPS / Developer / QA / -> PO)
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
      // unit tests
      "org.scalameta"   %% "munit"               % MunitVersion           % Test,
      "org.typelevel"   %% "munit-cats-effect-3" % MunitCatsEffectVersion % Test,
    ),
    // assembly / assemblyMergeStrategy := {
    //  case "module-info.class" => MergeStrategy.discard
    //  case x => (assembly / assemblyMergeStrategy).value.apply(x)
    //}
  )
