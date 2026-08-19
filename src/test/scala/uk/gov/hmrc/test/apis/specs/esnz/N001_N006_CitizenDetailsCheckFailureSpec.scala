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
import uk.gov.hmrc.test.apis.helpers.NinoPrefixGenerator
import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps

import java.util.UUID

class N001_N006_CitizenDetailsCheckFailureSpec extends BaseSpec with CommonSteps with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    super.beforeEach()
    builder.reset()
  }

  Feature("N001-N006_Claimant's Identity Check Failure - Negative Path Scenarios") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      (
        "Failure : N001_Claimant identity check fails due to first name mismatch in record",
        "Hanna",
        "White",
        "1990-06-27",
        NinoPrefixGenerator.generateFirst5() + "012A",
        "2024-06-27",
        404
      ),
      (
        "Failure : N002_Claimant identity check fails due to second  name mismatch in record",
        "Tom",
        "And",
        "1990-06-27",
        NinoPrefixGenerator.generateFirst5() + "014A",
        "2000-01-01",
        404
      ),
      (
        "Failure : N003_Claimant identity check fails due to date of birth mismatch in record",
        "Hannah",
        "White",
        "1990-06-28",
        NinoPrefixGenerator.generateFirst5() + "012A",
        "2023-05-01",
        404
      ),
      (
        "Failure : N004_Claimant identity check fails due to nino mismatch in record",
        "Tom",
        "Andrews",
        "1990-06-28",
        NinoPrefixGenerator.generateFirst5() + "012B",
        "2023-05-01",
        404
      ),
      (
        "Failure : N006_Claimant identity check fails due to citizen details not found in record",
        "Michael",
        "Johnson",
        "1990-06-28",
        NinoPrefixGenerator.generateFirst5() + "027A",
        "2023-05-01",
        500
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

        Then("I get a error response")
        expectedHttpStatusCode(responseStatusCode)

        And("No error response body should be return")
        expectedEmptyBody

        And("CorrelationId in response header is same as correlationId in request header")
        expectedCorrelationId(corrId)

      }
    }
  }
}
