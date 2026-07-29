package uk.gov.hmrc.test.apis.specs.esnz

import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps
import java.util.UUID

class N007_N009IndividualRelationshipFailureSpec extends BaseSpec with CommonSteps {

  Feature("Claimant's relationship type and source check failures - Negative Path Scenarios") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      (
        "Failure : N007_Claimant's relationship type and source are not classified as expected",
        "Michael",
        "Johnson",
        "1990-06-27",
        "AA000001A",
        "2023-05-01",
        200
      ),
      (
        "Failure : N008_Claimant's relationship source is not classified as expected",
        "Jess",
        "Bird",
        "1990-06-27",
        "AA000018A",
        "2023-05-01",
        200
      ),
      (
        "Failure : N008_Claimant's relationship data not found",
        "Frank",
        "Smith",
        "1990-06-27",
        "AA000016A",
        "2023-05-01",
        200
      )
    )
    Given("I have a valid bearer token for my privileged application")
    authenticate()

    And("I have a valid accept header")
    withValidAcceptHeaderVersion2()

    And("I have a valid JSON content type header")
    withJsonContentTypeHeader()

    And("I have a valid correlation Id header")
    val corrId = UUID.randomUUID().toString
    withCorrIdHeader(corrId)

    forAll(happyPathData) { (scenario, firstName, secondName, dateOfBirth, nino, bornOnOrAfter, responseStatusCode) =>
      Scenario(scenario) {

        When("I make a request to the child verification endpoint with a valid payload")
        iMakeARequestToTheChildVerificationEndpointWithAValidPayload(
          firstName,
          secondName,
          dateOfBirth,
          nino,
          bornOnOrAfter
        )

        Then("I get a success response")
        expectedHttpStatusCode(responseStatusCode)

        And("Success response must contain correct json body")
        expectedJsonSuccessEligibleMessage(false)

        And("Response correlationId is same as Request correlationId")
        expectedCorrelationId(corrId)

      }
    }
  }
}
