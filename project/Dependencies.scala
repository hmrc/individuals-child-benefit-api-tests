import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"     % "api-test-runner_3" % "0.10.0",
    "org.slf4j"       % "slf4j-api"         % "2.0.17",
    "io.rest-assured" % "rest-assured"      % "6.0.0"
  ).map(_ % Test)
}
