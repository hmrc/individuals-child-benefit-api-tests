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

import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.HicbcIndicatorSteps

class HicbcIndicatorSpec extends BaseSpec with HicbcIndicatorSteps {

  Feature("Update HICBC Indicator Endpoint - Happy Path Scenarios") {

    Scenario("Calling the Individual Benefits API HICBC endpoint with a valid opt-in payload works") {

      builder.reset()

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I make a request to the PUT child benefits HICBC endpoint with a valid opt-in payload")
      iUpdateTheHicbcIndicatorEndpointWithValidOptInPayload()

      Then("I get a successful response")
      expectedHttpStatusCode(204)
    }

    Scenario("Calling the Individual Benefits API HICBC endpoint with a valid opt-out payload works") {

      builder.reset()

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I make a request to the PUT child benefits HICBC endpoint with a valid opt-out payload")
      iUpdateTheHicbcIndicatorEndpointWithValidOptOutPayload()

      Then("I get a successful response")
      expectedHttpStatusCode(204)
    }

    Scenario("Calling the Individual Benefits API HICBC endpoint with no NINO returns 400") {

      builder.reset()

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I make a request to the PUT child benefits HICBC endpoint with no NINO")
      iUpdateTheHicbcIndicatorEndpointWithNoNinoPayload()

      Then("I get a bad request response due to invalid payload")
      expectedHttpStatusCode(400)
      expectedArrayJsonErrorCode("INVALID_PAYLOAD")
    }

    Scenario("Calling the Individual Benefits API HICBC endpoint with no opt-in/opt-out fields returns 400") {

      builder.reset()

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I make a request to the PUT child benefits HICBC endpoint with no opt-in/opt-out fields")
      iUpdateTheHicbcIndicatorEndpointWithNoOptFieldsPayload()

      Then("I get a bad request response due to invalid payload")
      expectedHttpStatusCode(400)
      expectedArrayJsonErrorCode("INVALID_PAYLOAD")
    }

    Scenario("Calling the Individual Benefits API HICBC endpoint with both opt-in and opt-out fields returns 400") {

      builder.reset()

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I make a request to the PUT child benefits HICBC endpoint with both opt-in and opt-out fields")
      iUpdateTheHicbcIndicatorEndpointWithBothOptFieldsPayload()

      Then("I get a bad request response due to invalid payload")
      expectedHttpStatusCode(400)
      expectedArrayJsonErrorCode("INVALID_PAYLOAD")
    }

    Scenario("Calling the Individual Benefits API HICBC endpoint with empty payload returns 400") {

      builder.reset()

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I make a request to the PUT child benefits HICBC endpoint with empty payload")
      iUpdateTheHicbcIndicatorEndpointWithEmptyPayload()

      Then("I get a bad request response due to invalid payload")
      expectedHttpStatusCode(400)
      expectedArrayJsonErrorCode("INVALID_PAYLOAD")
    }

    Scenario("Calling the Individual Benefits API HICBC endpoint returns 422") {

      builder.reset()

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      And("I have a valid correlation ID")
      withValidCorrID()

      When("I make a request to the PUT child benefits HICBC endpoint with empty payload")
      iUpdateTheHicbcIndicatorEndpointWithUnprocessableEntity()

      Then("I get a unprocessable entity response due to NINO")
      expectedHttpStatusCode(422)
      expectedArrayJsonErrorCode("ACCOUNT_LOCKED")
    }

  }
}
