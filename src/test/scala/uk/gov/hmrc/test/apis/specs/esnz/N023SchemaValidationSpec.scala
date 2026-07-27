package uk.gov.hmrc.test.apis.specs.esnz

import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps

class N023SchemaValidationSpec extends BaseSpec with CommonSteps {

  Feature("N023_Schema validation failures") {
    val schemaValidationData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "statusCode"),
      ("Nino value is empty in request body", "Laura", "Taylor", "1990-06-27", "", "2025-12-01", 400),
      ("Nino invalid regex value in request body", "Laura", "Taylor", "1990-06-27", "AA00A008A", "2025-12-01", 400),
      ("FirstName value is missing in request body", "", "Taylor", "1990-06-27", "AA000008A", "2025-12-01", 400),
      (
        "FirstName value is more than 35 char in request body",
        "LauraLauraLauraLauraLauraLauraLaura1",
        "Taylor",
        "1990-06-27",
        "AA000008A",
        "2025-12-01",
        400
      ),
      ("SecondName value is missing in request body", "Laura", "", "1990-06-27", "AA000008A", "2025-12-01", 400),
      (
        "SecondName value is more than 35 char in request body",
        "Laura",
        "TaylorTaylorTaylorTaylorTaylorTaylor",
        "1990-06-27",
        "AA000008A",
        "2025-12-01",
        400
      ),
      ("DOB value is missing in request body", "Laura", "Taylor", "", "AA000008A", "2025-12-01", 400),
      ("DOB's invalid regex in request body", "Laura", "Taylor", "1990/06/27", "AA000008A", "2025-12-01", 400),
      ("DOB's value is incorrect in request body", "Laura", "Taylor", "1990-09-31", "AA000008A", "2025-12-01", 400),
      ("bornOnOrAfter value is missing  in request body", "Laura", "Taylor", "1990-06-27", "AA000008A", "", 400),
      ("bornOnOrAfter is invalid regex in request body", "Laura", "Taylor", "127-06-2020", "AA000008A", "", 400)
    )
    forAll(schemaValidationData) {
      (scenario, firstName, secondName, dateOfBirth, nino, bornOnOrAfter, responseStatusCode) =>
        Scenario(scenario) {

          Given("I have a valid bearer token for my privileged application")
          authenticate()

          And("I have a valid accept header")
          withValidAcceptHeaderVersion2()

          And("I have a valid JSON content type header")
          withJsonContentTypeHeader()

          When("I make a request to the child verification endpoint with nino missing in request body")
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
          expectedJsonErrorCode("400")
          expectedJsonMessage("The JSON payload is invalid")

        }
    }

  }
}
