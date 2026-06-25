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

package uk.gov.hmrc.test.apis.specs.relationships

import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.RelationshipsSteps

class RealtionshipsHappyPathSpec extends BaseSpec with RelationshipsSteps {

  Feature("Individuals Child Benefits API - Get Relationship Details") {

    Scenario("Calling the Individual Benefits API relationship endpoint with a valid id") {
      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      When("I make a request to the get relationship details endpoint with an id of AB654321")
      iMakeARequestToTheRelationshipsEndpointWithAnIdOf("AB654321")

      Then("I get a successful response")
      expectedHttpStatusCode(200)
    }

    Scenario("Calling the Individual Benefits API relationship endpoint with an invalid id") {
      Given("I have a valid bearer token for my privileged application")
      authenticate()

      And("I have a valid accept header")
      withValidAcceptHeaderVersion()

      And("I have a valid JSON content type header")
      withJsonContentTypeHeader()

      When("I make a request to the get relationship details endpoint with an id of BA000002D")
      iMakeARequestToTheRelationshipsEndpointWithAnIdOf("BA000002D")

      Then("I get a not found response due to an invalid Id number")
      expectedHttpStatusCode(404)
      expectedArrayJsonErrorCode("NOT_FOUND_IDENTIFIER")
      expectedArrayJsonMessage("The remote endpoint has indicated that the identifier provided cannot be found.")
    }

  }
}
