package uk.gov.hmrc.test.apis.specs.esnz

import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps

import java.util.UUID

class N001_N006_CitizenDetailsCheckFailureSpec extends BaseSpec with CommonSteps with BeforeAndAfterEach {

  val corrId = UUID.randomUUID().toString

  override def beforeEach(): Unit = {
    super.beforeEach()
    builder.reset()
    Given("I have a valid bearer token for my privileged application")
    authenticate()

    And("I have a valid accept header")
    withValidAcceptHeaderVersion2()

    And("I have a valid JSON content type header")
    withJsonContentTypeHeader()

    And("I have a valid correlation Id header")
    withCorrIdHeader(corrId)
  }

  Feature("Claimant's Identity Check Failure - Negative Path Scenarios") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      (
        "Failure : N001_Claimant identity check fails due to first name mismatch in record",
        "Hanna",
        "White",
        "1990-06-27",
        "AA000012A",
        "2024-06-27",
        404
      ),
      (
        "Failure : N002_Claimant identity check fails due to second  name mismatch in record",
        "Tom",
        "And",
        "1990-06-27",
        "AA000014A",
        "2000-01-01",
        404
      ),
      (
        "Failure : N003_Claimant identity check fails due to date of birth mismatch in record",
        "Hannah",
        "White",
        "1990-06-28",
        "AA000012A",
        "2023-05-01",
        404
      ),
      (
        "Failure : N004_Claimant identity check fails due to nino mismatch in record",
        "Tom",
        "Andrews",
        "1990-06-28",
        "AA000012B",
        "2023-05-01",
        404
      ),
      (
        "Failure : N006_Claimant identity check fails due to citizen details not found in record",
        "Michael",
        "Johnson",
        "1990-06-28",
        "AA000027A",
        "2023-05-01",
        500
      )
    )

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

        Then("I get a error response")
        expectedHttpStatusCode(responseStatusCode)

        And("No error response body should be return")
        expectedEmptyBody

        And("Response correlationId is same as Request correlationId")
        expectedCorrelationId(corrId)

      }
    }

  }
}