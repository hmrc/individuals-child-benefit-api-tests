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

package uk.gov.hmrc.test.apis.specs.financialDetails

import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.FinancialDetailsSteps

class FinancialDetailsRequestHeadersSpec extends BaseSpec with FinancialDetailsSteps with BeforeAndAfterEach {
  override def beforeEach(): Unit = {
    super.beforeEach()
    builder.reset()
  }

  Feature("Get Child Benefit Financial Details Endpoint - Request Headers Scenarios") {

    Scenario("Calling with an invalid auth header(invalid token) returns 500") {

      builder.reset()

      Given("I have an invalid bearer token for my privileged application")
      withInvalidAuthHeader()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      When("I make a request to the get financial details endpoint")
      iMakeARequestToTheGetFinancialDetailsEndpointWithNino("AB123456C")

      Then("I get an unknown error response")
      expectedHttpStatusCode(500)
    }

    Scenario("Calling with no auth header (no bearer token) returns 500") {

      builder.reset()

      Given("I have no bearer token")
      withNoAuthHeader()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      When("I make a request to the get financial details endpoint")
      iMakeARequestToTheGetFinancialDetailsEndpointWithNino("AB123456C")

      Then("I get an unknown error response")
      expectedHttpStatusCode(500)
    }

    Scenario("Calling with an invalid accept header returns 406") {

      builder.reset()

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have an invalid accept header")
      withInvalidAcceptHeader()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      When("I make a request to the get financial details endpoint")
      iMakeARequestToTheGetFinancialDetailsEndpointWithNino("AB123456C")

      Then("I get an accept header invalid response")
      iGetAnAcceptHeaderInvalidResponse()
    }

    Scenario("Calling with a missing accept header returns 406") {

      builder.reset()

      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have no accept header")
      withNoAcceptHeader()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      When("I make a request to the get financial details endpoint")
      iMakeARequestToTheGetFinancialDetailsEndpointWithNino("AB123456C")

      Then("I get an accept header invalid response")
      iGetAnAcceptHeaderInvalidResponse()
    }
  }
}
