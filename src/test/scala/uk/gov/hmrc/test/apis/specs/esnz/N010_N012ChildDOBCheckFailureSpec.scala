package uk.gov.hmrc.test.apis.specs.esnz

import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps

class N010_N012ChildDOBCheckFailureSpec extends BaseSpec with CommonSteps {

  Feature("Claimant's child DOB check failures - Negative Path Scenarios") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      (
        "Failure : N010_Claimant's child is born before the provided date",
        "James",
        "Brown",
        "1990-06-27",
        "AA000003A",
        "2024-06-28",
        200
      ),
      (
        "Failure : N012_Claimant's children data not found",
        "Jess",
        "Bird",
        "1990-06-27",
        "AA000017A",
        "2025-06-01",
        500
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
        if (responseStatusCode == 200) {
          expectedJsonSuccessEligibleMessage(false)
        } else {
          expectedEmptyBody
        }

      }
    }

  }
}
