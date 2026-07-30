package uk.gov.hmrc.test.apis.specs.esnz

import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps
import java.util.UUID

class N022HeaderAuthValidationSpec extends BaseSpec with CommonSteps {

  Feature("Auth validation Failure - Header Validation Scenario") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      ("Error :", "Laura", "Taylor", "1990-06-27", "AA000008A", "2025-12-01", 401)
    )

    Given("I have a valid accept header")
    withValidAcceptHeaderVersion2()

    And("I have a valid JSON content type header")
    withJsonContentTypeHeader()

    And("I have a valid correlation Id header")
    val corrId = UUID.randomUUID().toString
    withCorrIdHeader(corrId)

    forAll(happyPathData) { (scenario, firstName, secondName, dateOfBirth, nino, bornOnOrAfter, responseStatusCode) =>

      Scenario(scenario + "Authorisation is invalid in request header") {

        Given("I have a invalid bearer token for my privileged application")
        withInvalidAuthHeader()

        When("I make a request to the child verification endpoint with a valid payload")
        iMakeARequestToTheChildVerificationEndpointWithAValidPayload(
          firstName,
          secondName,
          dateOfBirth,
          nino,
          bornOnOrAfter
        )

        Then("I get a error response")
        expectedHttpStatusCode(responseStatusCode)

        And("No error body is return")
        expectedEmptyBody

        And("Response correlationId is same as Request correlationId")
        expectedCorrelationId(corrId)
      }

      Scenario(scenario + "Authorisation is missing in request header") {

        withNoAuthHeader()

        Given("I have a valid accept header")
        withValidAcceptHeaderVersion2()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        And("I have a valid correlation Id header")
        withCorrIdHeader(corrId)

        When("I make a request to the child verification endpoint with a valid payload")
        iMakeARequestToTheChildVerificationEndpointWithAValidPayload(
          firstName,
          secondName,
          dateOfBirth,
          nino,
          bornOnOrAfter
        )

        Then("I get a error response")
        expectedHttpStatusCode(responseStatusCode)

        And("No error body is return")
        expectedEmptyBody

        And("Response correlationId is same as Request correlationId")
        expectedCorrelationId(corrId)

      }

      Scenario(scenario + "Authorisation is expired in request header") {

        Given("I have a expired bearer token for my privileged application")
        withExpiredAuthHeader()

        When("I make a request to the child verification endpoint with a valid payload")
        iMakeARequestToTheChildVerificationEndpointWithAValidPayload(
          firstName,
          secondName,
          dateOfBirth,
          nino,
          bornOnOrAfter
        )

        Then("I get a error response")
        expectedHttpStatusCode(responseStatusCode)

        And("No error body is return")
        expectedEmptyBody

        And("Response correlationId is same as Request correlationId")
        expectedCorrelationId(corrId)

      }

      Scenario(scenario + "Authorisation value is missing in request header") {

        Given("I have a missing bearer token value  for my privileged application")
        withMissingAuthHeaderValue()

        When("I make a request to the child verification endpoint with a valid payload")
        iMakeARequestToTheChildVerificationEndpointWithAValidPayload(
          firstName,
          secondName,
          dateOfBirth,
          nino,
          bornOnOrAfter
        )

        Then("I get a error response")
        expectedHttpStatusCode(responseStatusCode)

        And("No error body is return")
        expectedEmptyBody

        And("Response correlationId is same as Request correlationId")
        expectedCorrelationId(corrId)

      }
    }
  }
}
