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
import uk.gov.hmrc.test.apis.helpers.request.CommonRequestSteps
import uk.gov.hmrc.test.apis.helpers.response.CommonResponseSteps
import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.HicbcIndicatorSteps

class HicbcIndicatorRequestHeadersSpec
    extends BaseSpec
    with HicbcIndicatorSteps
    with CommonResponseSteps
    with CommonRequestSteps
    with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    super.beforeEach()
    builder.reset()
  }

  Feature("HICBC Indicator Update Endpoint - Request Header Scenarios") {

    Scenario(
      "Calling the HICBC indicator endpoint with an invalid accept header returns a 406 accept header invalid response"
    ) {

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have an invalid accept header")
      withInvalidAcceptHeader()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I update the HICBC indicator endpoint with a valid opt-in payload")
      iUpdateTheHicbcIndicatorEndpointWithValidOptInPayload()

      Then("I get an unacceptable response due to an invalid accept header")
      iGetAnAcceptHeaderInvalidResponse()
    }

    Scenario(
      "Calling the HICBC indicator endpoint with no accept header returns a 406 accept header invalid response"
    ) {

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have no accept header")
      withNoAcceptHeader()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I update the HICBC indicator endpoint with a valid opt-in payload")
      iUpdateTheHicbcIndicatorEndpointWithValidOptInPayload()

      Then("I get an unacceptable response due to an invalid accept header")
      iGetAnAcceptHeaderInvalidResponse()
    }

    Scenario(
      "Calling the HICBC indicator endpoint with an invalid content type header returns a 415 unsupported media type response"
    ) {

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have an invalid JSON content type header")
      withInvalidJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I update the HICBC indicator endpoint with a valid opt-in payload")
      iUpdateTheHicbcIndicatorEndpointWithValidOptInPayload()

      Then("I get an unsupported media type response")
      iGetAnUnsupportedMediaTypeResponse()
    }

  }
}
