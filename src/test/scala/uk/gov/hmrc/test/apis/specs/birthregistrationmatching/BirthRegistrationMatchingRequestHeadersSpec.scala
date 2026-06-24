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

package uk.gov.hmrc.test.apis.specs.birthregistrationmatching

import uk.gov.hmrc.test.apis.steps.BirthRegistrationMatchingSteps
import uk.gov.hmrc.test.apis.specs.BaseSpec

class BirthRegistrationMatchingRequestHeadersSpec extends BaseSpec with BirthRegistrationMatchingSteps {

  Feature("Create Child Benefit Birth Registration Endpoint - Accept Header Scenarios") {
    Scenario(
      "Calling the Individual Benefits API birth registration endpoint with an invalid accept header returns a 406 accept header invalid response"
    ) {
      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have an incorrect accept header version")
      withInvalidAcceptHeader()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      When("I make a request to the post child benefits claim endpoint with a valid payload")
      iMakeARequestToTheIndividualChildBenefitsRegistrationEndpointWithAValidPayload()

      Then("I get an unacceptable response due to an invalid accept header")
      iGetAnAcceptHeaderInvalidResponse()
    }

    Scenario(
      "Calling the Individual Benefits API birth registration endpoint with no accept header returns a 406 accept header invalid response"
    ) {
      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have no accept header")
      withNoAcceptHeader()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      When("I make a request to the post child benefits claim endpoint with a valid payload")
      iMakeARequestToTheIndividualChildBenefitsRegistrationEndpointWithAValidPayload()

      Then("I get an unacceptable response due to an invalid accept header")
      iGetAnAcceptHeaderInvalidResponse()
    }
  }

  Feature("Create Child Benefit Birth Registration Endpoint - Content Type Header Scenarios") {
    Scenario(
      "Calling the Individual Benefits API birth registration endpoint with an invalid content type header returns a 415 unsupported media type response"
    ) {
      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have an invalid JSON content type header")
      withInvalidJsonContentTypeHeader()

      When("I make a request to the post child benefits claim endpoint with a valid payload")
      iMakeARequestToTheIndividualChildBenefitsRegistrationEndpointWithAValidPayload()

      Then("I get an unsupported media type response")
      iGetAnUnsupportedMediaTypeResponse()
    }

    Scenario(
      "Calling the Individual Benefits API birth registration endpoint with no content type header returns a 415 unsupported media type response"
    ) {
      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have no JSON content type header")
      withNoJsonContentTypeHeader()

      When("I make a request to the post child benefits claim endpoint with a valid payload")
      iMakeARequestToTheIndividualChildBenefitsRegistrationEndpointWithAValidPayload()

      Then("I get an unsupported media type response")
      iGetAnUnsupportedMediaTypeResponse()
    }
  }
}
