/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.test.apis.specs.esnz

import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps

import java.util.UUID

class N013_N024_DownstreamSystemFailureSpec extends BaseSpec with CommonSteps with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    super.beforeEach()
    builder.reset()
  }

  Feature("N013-N024_Handling downstream service failures such as HTTP 500, 503 and 404 responses") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      (
        "Failure : N013_Citizen details api service is unavailable",
        "Michael",
        "Johnson",
        "1990-06-27",
        "AA000035A",
        "2023-05-01",
        503
      ),
      (
        "Failure : N014_Citizen details api is down due to internal server error",
        "Michael",
        "Johnson",
        "1990-06-27",
        "AA000026A",
        "2023-05-01",
        500
      ),
      (
        "Failure : N015_Citizen details api service returns 404 Not Found response",
        "Michael",
        "Johnson",
        "1990-06-27",
        "AA000025A",
        "2023-05-01",
        404
      ),
      (
        "Failure : N016_Individual details api service is unavailable",
        "Jess",
        "Bird",
        "1990-06-27",
        "AA000019A",
        "2023-05-01",
        503
      ),
      (
        "Failure : N017_Individual details api service is down due to internal server error",
        "Jess",
        "Bird",
        "1990-06-27",
        "AA000020A",
        "2023-05-01",
        500
      ),
      (
        "Failure : N018_Individual Relationship Details api returns 404 Not found response",
        "Kyle",
        "Parker",
        "1990-06-27",
        "AA000028A",
        "2023-05-01",
        502
      ),
      (
        "Failure : N019_Child DOB API service is unavailable",
        "Jess",
        "Bird",
        "1990-06-27",
        "AA000021A",
        "2023-05-01",
        503
      ),
      (
        "Failure : N020_Child DOB API Details service is down due to internal server error",
        "Jess",
        "Bird",
        "1990-06-27",
        "AA000022A",
        "2023-05-01",
        500
      ),
      (
        "Failure : N021_Child DOB API returns 404 Not found response",
        "Raymond",
        "Reddington",
        "1990-06-27",
        "AA000029A",
        "2023-05-01",
        200
      ),
      (
        "Failure : N022_Citizen Details API returns 504 gateway timeout response",
        "Michael",
        "Johnson",
        "1990-06-27",
        "AA000030A",
        "2023-05-01",
        504
      ),
      (
        "Failure : N023_Individual Relationship Details API returns 504 gateway timeout response",
        "Raymond",
        "Reddington",
        "1990-06-27",
        "AA000031A",
        "2023-05-01",
        504
      ),
      (
        "Failure : N024_Child DOB API returns 504 gateway timeout response",
        "Raymond",
        "Reddington",
        "1990-06-27",
        "AA000032A",
        "2023-05-01",
        504
      )
    )

    forAll(happyPathData) { (scenario, firstName, secondName, dateOfBirth, nino, bornOnOrAfter, responseStatusCode) =>
      Scenario(scenario) {

        Given("I have a valid bearer token for my privileged application")
        authenticate()

        And("I have a valid accept header")
        withValidAcceptHeaderVersion2()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        And("I have a valid correlation Id header")
        val corrId = UUID.randomUUID().toString
        withCorrIdHeader(corrId)

        When("I make a request to the child verification endpoint with a valid payload")
        iMakeARequestToTheChildVerificationEndpointWithAValidPayload(
          firstName,
          secondName,
          dateOfBirth,
          nino,
          bornOnOrAfter
        )

        Then("I get a success response")
        expectedHttpStatusCode(responseStatusCode)

        And("Success response must contain correct json body")
        if (responseStatusCode == 200) {
          expectedJsonSuccessEligibleMessage(false)
        } else if (responseStatusCode == 502) {
          expectedJsonErrorCode("DOWNSTREAM_ERROR")
          expectedJsonMessage("A downstream service is currently unavailable")
        } else {
          expectedEmptyBody
        }

        And("CorrelationId in response header is same as correlationId in request header")
        expectedCorrelationId(corrId)
      }
    }
  }
}
