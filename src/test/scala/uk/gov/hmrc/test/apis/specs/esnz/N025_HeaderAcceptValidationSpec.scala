package uk.gov.hmrc.test.apis.specs.esnz

import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps

import java.util.UUID

class N025_HeaderAcceptValidationSpec extends BaseSpec with CommonSteps with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    super.beforeEach()
    builder.reset()
  }

  Feature("N025_Accept validation Failure - Header Validation Scenario") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      ("Error : ", "Laura", "Taylor", "1990-06-27", "AA000008A", "2025-12-01", 406)
    )

    forAll(happyPathData) { (scenario, firstName, secondName, dateOfBirth, nino, bornOnOrAfter, responseStatusCode) =>
      Scenario(scenario + "Accept is invalid in request header") {

        Given("I have a valid bearer token for my privileged application")
        authenticate()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        And("I have a valid correlation Id header")
        val corrId = UUID.randomUUID().toString
        withCorrIdHeader(corrId)

        And("I have a invalid accept header")
        withInvalidAcceptHeader()

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

        And("Error response must contain correct json body")
        expectedArrayJsonErrorCode("ACCEPT_HEADER_INVALID")
        expectedArrayJsonMessage("The accept header is missing or invalid")
      }

      Scenario(scenario + "Accept is missing in request header") {
        withNoAcceptHeader()

        Given("I have a valid bearer token for my privileged application")
        authenticate()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        And("I have a valid correlation Id header")
        val corrId = UUID.randomUUID().toString
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

        And("Error response must contain correct json body")
        expectedArrayJsonErrorCode("ACCEPT_HEADER_INVALID")
        expectedArrayJsonMessage("The accept header is missing or invalid")

      }

      Scenario(scenario + "Accept is incorrect in request header") {

        Given("I have a valid bearer token for my privileged application")
        authenticate()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        And("I have a valid correlation Id header")
        val corrId = UUID.randomUUID().toString
        withCorrIdHeader(corrId)

        And("I have a invalid accept header")
        withIncorrectAcceptHeaderVersion()

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

        And("Error response must contain correct json body")
        expectedArrayJsonErrorCode("ACCEPT_HEADER_INVALID")
        expectedArrayJsonMessage("The accept header is missing or invalid")

      }

      Scenario(scenario + "Accept value is missing in request header") {

        Given("I have a valid bearer token for my privileged application")
        authenticate()

        And("I have a valid JSON content type header")
        withJsonContentTypeHeader()

        And("I have a valid correlation Id header")
        val corrId = UUID.randomUUID().toString
        withCorrIdHeader(corrId)

        And("I have a invalid accept header")
        withMissingAcceptHeaderValue()

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

        And("Error response must contain correct json body")
        expectedArrayJsonErrorCode("ACCEPT_HEADER_INVALID")
        expectedArrayJsonMessage("The accept header is missing or invalid")
      }
    }
  }
}