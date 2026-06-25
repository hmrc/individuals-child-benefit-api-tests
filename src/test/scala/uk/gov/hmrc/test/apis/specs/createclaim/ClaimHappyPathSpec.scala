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

package uk.gov.hmrc.test.apis.specs.createclaim

import uk.gov.hmrc.test.apis.steps.CreateClaimsSteps
import uk.gov.hmrc.test.apis.specs.BaseSpec

class CreateClaimHappyPathSpec extends BaseSpec with CreateClaimsSteps {

  Feature("Create Child Benefit Claim Endpoint - Happy Path Scenarios") {

    Scenario("Calling the Individual Benefits API claim endpoint with a valid payload works") {
      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      When("I make a request to the post child benefits claim endpoint with a valid payload")
      iMakeARequestToThePostChildBenefitsClaimEndpointWithAValidPayload()

      Then("I get a successfully create response")
      expectedHttpStatusCode(201)
    }

    Scenario("Calling the Individual Benefits API claim endpoint with an invalid payload returns 400 bad request") {
      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      When("I make a request to the post child benefits claim endpoint with an invalid payload")
      iMakeARequestToThePostChildBenefitsClaimEndpointWithAnInvalidPayload()

      Then("I get a bad request response due to submission has not passed validation")
      expectedHttpStatusCode(400)
      expectedArrayJsonErrorCode("INVALID_PAYLOAD")
    }

  }
}
