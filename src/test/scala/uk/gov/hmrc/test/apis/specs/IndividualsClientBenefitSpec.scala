package uk.gov.hmrc.test.api.specs

import uk.gov.hmrc.test.api.service.IndividualsChildBenefitService
import uk.gov.hmrc.test.api.data.CreateClaimTestData

class IndividualsClientBenefitSpec extends BaseSpec {
  Feature("Example of using the Individuals Child Benefit API") {
    val api = new IndividualsChildBenefitService()

    Scenario("Calling the Create Child Benefit Claim endpoint with an incorrect accept header version returns a 404 matching resource not found response") {
      Given("I have an incorrect accept header version")

      And("I have a valid JSON content type header")
      
      When("I make a request to the post child benefits claim endpoint with a valid payload")
      val result = api.someEndpoint(CreateClaimTestData.validCreateClaim)

      Then("I get an unacceptable response due to an invalid accept header")
      result.status shouldBe 406
    }
  }
}

// @feature
// Feature: Create Child Benefit Claim Endpoint - Request Header Scenarios

//   ### Accept Header Scenarios

//   @individuals-child-benefits-api @api-platform @regression-tests @claim
//   Scenario: Calling the Create Child Benefit Claim endpoint with an incorrect accept header version returns a 404 matching resource not found response
//     And I have an incorrect accept header version
//     And I have a valid JSON content type header
//     When I make a request to the post child benefits claim endpoint with a valid payload
//     Then I get a matching resource not found response

//   @individuals-child-benefits-api @api-platform @regression-tests @claim
//   Scenario: Calling the Create Child Benefit Claim endpoint with an invalid accept header returns a 406 accept header invalid response
//     Given I have a valid bearer token for my privileged application
//     And I have an invalid accept header
//     And I have a valid JSON content type header
//     When I make a request to the post child benefits claim endpoint with a valid payload
//     Then I get an unacceptable response due to an invalid accept header

//   @individuals-child-benefits-api @api-platform @regression-tests @claim
//   Scenario: Calling the Create Child Benefit Claim endpoint with no accept header returns a 406 accept header invalid response
//     Given I have a valid bearer token for my privileged application
//     And I have no accept header
//     And I have a valid JSON content type header
//     When I make a request to the post child benefits claim endpoint with a valid payload
//     Then I get an unacceptable response due to an invalid accept header


//   ### Content Header Scenarios

//   @individuals-child-benefits-api @api-platform @regression-tests @claim
//   Scenario: Calling the Create Child Benefit Claim endpoint with an invalid content type header returns a 415 unsupported media type response
//     Given I have a valid bearer token for my privileged application
//     And I have an invalid JSON content type header
//     When I make a request to the post child benefits claim endpoint with a valid payload
//     Then I get an unsupported media type response

//   @individuals-child-benefits-api @api-platform @regression-tests @claim
//   Scenario: Calling the Create Child Benefit Claim endpoint with no content type header returns a 415 unsupported media type response
//     Given I have a valid bearer token for my privileged application
//     And I have no JSON content type header
//     When I make a request to the post child benefits claim endpoint with a valid payload
//     Then I get an unsupported media type response

