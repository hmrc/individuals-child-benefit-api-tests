package uk.gov.hmrc.test.apis.specs.esnz

import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps

class N022HeaderAcceptValidationSpec extends BaseSpec with CommonSteps {

  Feature("Accept validation Failure - Header Validation Scenario") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      ("Error : ", "Laura", "Taylor", "1990-06-27", "AA000008A", "2025-12-01", 406)
    )
    forAll(happyPathData) { (scenario, firstName, secondName, dateOfBirth, nino, bornOnOrAfter, responseStatusCode) =>
      Scenario(scenario + "Accept is invalid in request header") {

        Given("I have a valid bearer token for my privileged application")
        authenticate()

        And("I have a invalid accept header")
        withInvalidAcceptHeader()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        When("I make a request to the child verification endpoint with a valid payload")
        iMakeARequestToTheChildVerificationEndpointWithAValidPayload(
          scenario,
          firstName,
          secondName,
          dateOfBirth,
          nino,
          bornOnOrAfter,
          responseStatusCode
        )

        Then("I get a error response")
        expectedHttpStatusCode(responseStatusCode)

        And("Error response body must contain correct error details")
        expectedArrayJsonErrorCode("ACCEPT_HEADER_INVALID")
        expectedArrayJsonMessage("The accept header is missing or invalid")
      }

      Scenario(scenario + "Accept is missing in request header") {

        Given("I have a valid bearer token for my privileged application")
        authenticate()

        And("I have a invalid accept header")
        withNoAcceptHeader()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        When("I make a request to the child verification endpoint with a valid payload")
        iMakeARequestToTheChildVerificationEndpointWithAValidPayload(
          scenario,
          firstName,
          secondName,
          dateOfBirth,
          nino,
          bornOnOrAfter,
          responseStatusCode
        )

        Then("I get a error response")
        expectedHttpStatusCode(responseStatusCode)

        And("Error response body must contain correct error details")
        expectedArrayJsonErrorCode("ACCEPT_HEADER_INVALID")
        expectedArrayJsonMessage("The accept header is missing or invalid")

      }
      Scenario(scenario + "Accept is incorrect in request header") {

        Given("I have a valid bearer token for my privileged application")
        authenticate()

        And("I have a invalid accept header")
        withIncorrectAcceptHeaderVersion()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        When("I make a request to the child verification endpoint with a valid payload")
        iMakeARequestToTheChildVerificationEndpointWithAValidPayload(
          scenario,
          firstName,
          secondName,
          dateOfBirth,
          nino,
          bornOnOrAfter,
          responseStatusCode
        )

        Then("I get a error response")
        expectedHttpStatusCode(responseStatusCode)

        And("Error response body must contain correct error details")
        expectedArrayJsonErrorCode("ACCEPT_HEADER_INVALID")
        expectedArrayJsonMessage("The accept header is missing or invalid")

      }
      Scenario(scenario + "Accept value is missing in request header") {

        Given("I have a valid bearer token for my privileged application")
        authenticate()

        And("I have a invalid accept header")
        withMissingAcceptHeaderValue()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        When("I make a request to the child verification endpoint with a valid payload")
        iMakeARequestToTheChildVerificationEndpointWithAValidPayload(
          scenario,
          firstName,
          secondName,
          dateOfBirth,
          nino,
          bornOnOrAfter,
          responseStatusCode
        )

        Then("I get a error response")
        expectedHttpStatusCode(responseStatusCode)

        And("Error response body must contain correct error details")
        expectedArrayJsonErrorCode("ACCEPT_HEADER_INVALID")
        expectedArrayJsonMessage("The accept header is missing or invalid")

      }

    }

  }

}
