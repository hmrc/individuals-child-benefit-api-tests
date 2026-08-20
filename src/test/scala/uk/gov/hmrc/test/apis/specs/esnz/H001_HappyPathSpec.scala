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
import uk.gov.hmrc.test.apis.steps.CommonSteps
import uk.gov.hmrc.test.apis.specs.BaseSpec

import java.util.UUID
import uk.gov.hmrc.test.apis.helpers.NinoPrefixGenerator

class H001_HappyPathSpec extends BaseSpec with CommonSteps with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    super.beforeEach()
    builder.reset()
  }

  Feature("H001_Claimant's child is born on or after the provided date - Happy Path Scenarios") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "statusCode"),
      ("Success : 1 child DOB on the provided date", "Laura", "Taylor", "1990-06-27", NinoPrefixGenerator.generateFirst5() + "008A", "2025-12-01", 200),
      (
        "Success : 2 Children DOB After & Before Provided Date",
        "Robert",
        "Anderson",
        "1990-06-27",
        NinoPrefixGenerator.generateFirst5() + "007A",
        "2025-12-31",
        200
      ),
      (
        "Success : 2 Children DOB After ProvidedDate with DOD",
        "Daniel",
        "Jackson",
        "1990-06-27",
        NinoPrefixGenerator.generateFirst5() + "011A",
        "2018-01-01",
        200
      ),
      (
        "Success : 4 Children DOB Before & On Provided Date with one child having DOD",
        "Tom",
        "Andrews",
        "1990-06-27",
        NinoPrefixGenerator.generateFirst5() +"014A",
        "2024-06-27",
        200
      ),
      (
        "Success : 3 Children DOB Before, After & On Provided Date with DOD",
        "Hannah",
        "White",
        "1990-06-27",
        NinoPrefixGenerator.generateFirst5() + "012A",
        "2024-06-27",
        200
      ),
      (
        "Success : 2 Children DOB After Provided Date & one child response is 404",
        "Tarana",
        "Basin",
        "1990-06-01",
        NinoPrefixGenerator.generateFirst5() + "033A",
        "2026-01-20",
        200
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

        Then("I get a successful response")
        expectedHttpStatusCode(responseStatusCode)

        And("Success response must contain correct json body")
        expectedJsonSuccessEligibleMessage(true)

        And("CorrelationId in response header is same as correlationId in request header")
        expectedCorrelationId(corrId)

      }
    }
  }
}
