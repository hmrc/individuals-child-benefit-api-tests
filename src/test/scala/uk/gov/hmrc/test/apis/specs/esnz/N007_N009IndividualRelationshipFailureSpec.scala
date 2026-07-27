package uk.gov.hmrc.test.apis.specs.esnz

import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps

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

    forAll(happyPathData) { (scenario, firstName, secondName, dateOfBirth, nino, bornOnOrAfter, responseStatusCode) =>
      Scenario(scenario) {

        Given("I have a valid bearer token for my privileged application")
        authenticate()

        And("I have a valid accept header")
        withValidAcceptHeaderVersion2()

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

        Then("I get a success response")
        expectedHttpStatusCode(responseStatusCode)

        And("Success response body must contain correct error details")
        expectedJsonSuccessEligibleMessage(false)

      }
    }

  }
}
