import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"           %% "api-test-runner"          % "0.10.0",
    "org.slf4j"              % "slf4j-api"                % "2.0.17"
  ).map(_ % Test)
}
