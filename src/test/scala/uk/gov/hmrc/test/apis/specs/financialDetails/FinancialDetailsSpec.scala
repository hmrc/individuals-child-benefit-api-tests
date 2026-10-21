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

import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.FinancialDetailsSteps

class FinancialDetailsSpec extends BaseSpec with FinancialDetailsSteps {

  Feature("Get Child Benefit Financial Details Endpoint - Happy Path Scenarios") {

    Scenario("Calling the financial details endpoint with a valid NINO returns financial data") {
      
      builder.reset()
      
      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      When("I make a request to the get financial details endpoint with a valid NINO")
      iMakeARequestToTheGetFinancialDetailsEndpointWithNino("AB765434C")

      Then("the response contains valid financial details data")
      expectedHttpStatusCode(200)
    }
  }
}
