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

class N007_N009_IndividualRelationshipFailureSpec extends BaseSpec with CommonSteps with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    super.beforeEach()
    builder.reset()
  }

  Feature("N007-N009_Claimant's relationship type and source check failures - Negative Path Scenarios") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      (
        "Failure : N007_Claimant's relationship type and source are not classified as expected",
        "Michael",
        "Johnson",
        "1990-06-27",
        NinoPrefixGenerator.generateFirst5() + "001A",
        "2023-05-01",
        200
      ),
      (
        "Failure : N008_Claimant's relationship source is not classified as expected",
        "Jess",
        "Bird",
        "1990-06-27",
        NinoPrefixGenerator.generateFirst5() + "018A",
        "2023-05-01",
        200
      ),
      (
        "Failure : N008_Claimant's relationship data not found",
        "Frank",
        "Smith",
        "1990-06-27",
        NinoPrefixGenerator.generateFirst5() + "016A",
        "2023-05-01",
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

        Then("I get a success response")
        expectedHttpStatusCode(responseStatusCode)

        And("Success response must contain correct json body")
        expectedJsonSuccessEligibleMessage(false)

        And("CorrelationId in response header is same as correlationId in request header")
        expectedCorrelationId(corrId)
      }
    }
  }
}
