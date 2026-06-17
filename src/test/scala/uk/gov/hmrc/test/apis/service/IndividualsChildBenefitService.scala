package uk.gov.hmrc.test.api.service

import play.api.libs.ws.StandaloneWSResponse

import uk.gov.hmrc.apitestrunner.http.HttpClient

import uk.gov.hmrc.test.api.model.CreateClaim

import scala.concurrent.Await
import scala.concurrent.duration._
import play.api.libs.json.Json
import play.api.libs.ws.JsonBodyWritables.writeableOf_JsValue
import uk.gov.hmrc.test.api.conf.TestConfiguration

class IndividualsChildBenefitService extends HttpClient {
  val host: String                        = TestConfiguration.url("icb")
  val individualsChildBenefitURL: String  = s"$host/"

  def someEndpoint(claim: CreateClaim, headerOverrides: (String, String)*): StandaloneWSResponse = {
    val payload = Json.toJsObject(claim)

    val defaultHeaders = Map(
      "Authorization" -> ??? /*auth token*/,
      "CorrelationId" -> "12345678",
      "Accept"        -> "application/vnd.hmrc.P1.0+json"
    )

    val headers = defaultHeaders ++ headerOverrides

    Await.result(
      mkRequest(individualsChildBenefitURL + "/individuals/child-benefit/child-benefit/claim")
      .withHttpHeaders((headers.toList)*)
        .post(payload),
      10.seconds
    )
  }
}
