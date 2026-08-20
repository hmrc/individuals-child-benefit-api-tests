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

class N025_HeaderAuthValidationSpec extends BaseSpec with CommonSteps with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    super.beforeEach()
    builder.reset()
  }

  Feature("N025_Auth validation Failure - Header Validation Scenario") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      ("Error :", "Laura", "Taylor", "1990-06-27", NinoPrefixGenerator.generateFirst5() + "008A", "2025-12-01", 401)
    )

    forAll(happyPathData) { (scenario, firstName, secondName, dateOfBirth, nino, bornOnOrAfter, responseStatusCode) =>

      Scenario(scenario + "Authorisation is invalid in request header") {

        Given("I have a valid accept header")
        withValidAcceptHeaderVersion2()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        And("I have a valid correlation Id header")
        val corrId = UUID.randomUUID().toString
        withCorrIdHeader(corrId)

        And("I have a invalid bearer token for my privileged application")
        withInvalidAuthHeader()

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

        And("No error body is return")
        expectedEmptyBody

        And("CorrelationId in response header is same as correlationId in request header")
        expectedCorrelationId(corrId)
      }

      Scenario(scenario + "Authorisation is missing in request header") {
        withNoAuthHeader()

        Given("I have a valid accept header")
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

        And("No error body is return")
        expectedEmptyBody

        And("CorrelationId in response header is same as correlationId in request header")
        expectedCorrelationId(corrId)

      }

      Scenario(scenario + "Authorisation is expired in request header") {

        Given("I have a valid accept header")
        withValidAcceptHeaderVersion2()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        And("I have a valid correlation Id header")
        val corrId = UUID.randomUUID().toString
        withCorrIdHeader(corrId)

        Given("I have a expired bearer token for my privileged application")
        withExpiredAuthHeader()

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

        And("No error body is return")
        expectedEmptyBody

        And("CorrelationId in response header is same as correlationId in request header")
        expectedCorrelationId(corrId)
      }

      Scenario(scenario + "Authorisation value is missing in request header") {

        Given("I have a valid accept header")
        withValidAcceptHeaderVersion2()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        And("I have a valid correlation Id header")
        val corrId = UUID.randomUUID().toString
        withCorrIdHeader(corrId)

        Given("I have a missing bearer token value  for my privileged application")
        withMissingAuthHeaderValue()

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

        And("No error body is return")
        expectedEmptyBody

        And("CorrelationId in response header is same as correlationId in request header")
        expectedCorrelationId(corrId)
      }
    }
  }
}
