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

package uk.gov.hmrc.test.apis.specs.hicbc

import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.HicbcIndicatorSteps

class HicbcIndicatorNegativeScenarioSpec extends BaseSpec with HicbcIndicatorSteps with BeforeAndAfterEach {
  override def beforeEach(): Unit = {
    super.beforeEach()
    builder.reset()
  }

  Feature("Update HICBC Indicator Endpoint - Negative Scenarios") {

    Scenario("Calling the Individual Benefits API HICBC endpoint returns ServiceUnavailableFailure") {

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I make a request to the PUT child benefits HICBC endpoint with valid payload")
      iUpdateTheHicbcIndicatorEndpointWithServiceUnavailableFailure()

      Then("I get a unprocessable entity response due to NINO")
      expectedHttpStatusCode(503)
      expectedArrayJsonErrorCode("SERVICE_UNAVAILABLE")
    }

    Scenario("Calling the Individual Benefits API HICBC endpoint returns MissingPaymentDetailsFailure") {

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I make a request to the PUT child benefits HICBC endpoint with valid payload")
      iUpdateTheHicbcIndicatorEndpointWithMissingPaymentDetailsFailure()

      Then("I get a unprocessable entity response due to NINO")
      expectedHttpStatusCode(404)
      expectedArrayJsonErrorCode("MISSING_PAYMENT_DETAILS")
    }

    Scenario("Calling the Individual Benefits API HICBC endpoint returns BadHeadersFailure") {

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I make a request to the PUT child benefits HICBC endpoint with valid payload")
      iUpdateTheHicbcIndicatorEndpointWithBadHeadersFailure()

      Then("I get a unprocessable entity response due to NINO")
      expectedHttpStatusCode(400)
      expectedArrayJsonErrorCode("INVALID_CORRELATIONID")
    }

  }
}
