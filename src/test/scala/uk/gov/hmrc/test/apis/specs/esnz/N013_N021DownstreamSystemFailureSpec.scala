package uk.gov.hmrc.test.apis.specs.esnz

import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps

class N013_N021DownstreamSystemFailureSpec extends BaseSpec with CommonSteps {

  Feature("Handling downstream service failures such as HTTP 500, 503 and 404 responses") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      (
        "Failure : N013_Citizen details api service is unavailable",
        "Michael",
        "Johnson",
        "1990-06-27",
        "BB000001A",
        "2023-05-01",
        503
      ),
      (
        "Failure : N014_Citizen details api is down due to internal server error",
        "Michael",
        "Johnson",
        "1990-06-27",
        "AA000026A",
        "2023-05-01",
        500
      ),
      (
        "Failure : N015_Citizen details api service returns 404 Not Found response",
        "Michael",
        "Johnson",
        "1990-06-27",
        "AA000025A",
        "2023-05-01",
        404
      ),
      (
        "Failure : N016_Individual details api service is unavailable",
        "Jess",
        "Bird",
        "1990-06-27",
        "AA000019A",
        "2023-05-01",
        503
      ),
      (
        "Failure : N017_Individual details api service is down due to internal server error",
        "Jess",
        "Bird",
        "1990-06-27",
        "AA000020A",
        "2023-05-01",
        500
      ),
      (
        "Failure : N018_Individual Relationship Details api returns 404 Not found response",
        "Kyle",
        "Parker",
        "1990-06-27",
        "AA000028A",
        "2023-05-01",
        502
      ),
      (
        "Failure : N019_Child DOB API service is unavailable",
        "Jess",
        "Bird",
        "1990-06-27",
        "AA000021A",
        "2023-05-01",
        503
      ),
      (
        "Failure : N020_Child DOB API Details service is down due to internal server error",
        "Jess",
        "Bird",
        "1990-06-27",
        "AA000022A",
        "2023-05-01",
        500
      ),
      (
        "Failure : N021_Child DOB API returns 404 Not found response",
        "Raymond",
        "Reddington",
        "1990-06-27",
        "AA000029A",
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
        if (responseStatusCode == 200) {
          expectedJsonSuccessEligibleMessage(false)
        } else if (responseStatusCode == 502) {
          expectedJsonErrorCode("DOWNSTREAM_ERROR")
          expectedJsonMessage("A downstream service is currently unavailable")
        } else {
          expectedEmptyBody
        }

      }
    }

  }
}
