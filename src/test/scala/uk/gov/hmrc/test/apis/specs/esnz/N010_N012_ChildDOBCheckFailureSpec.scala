package uk.gov.hmrc.test.apis.specs.esnz

import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.test.apis.specs.BaseSpec
import uk.gov.hmrc.test.apis.steps.CommonSteps

import java.util.UUID

class N010_N012_ChildDOBCheckFailureSpec extends BaseSpec with CommonSteps with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    super.beforeEach()
    builder.reset()
  }

  Feature("N010-N012_Claimant's child DOB check failures - Negative Path Scenarios") {

    val happyPathData = Table(
      ("scenario", "firstName", "secondName", "dateOfBirth", "nino", "bornOnOrAfter", "responseCode"),
      (
        "Failure : N010_Claimant's child DOB before provided date",
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

        Then("I get a success response")
        expectedHttpStatusCode(responseStatusCode)

        And("Success response must contain correct json body")
        if (responseStatusCode == 200) {
          expectedJsonSuccessEligibleMessage(false)
        } else {
          expectedEmptyBody
        }

        And("CorrelationId in response header is same as correlationId in request header")
        expectedCorrelationId(corrId)
      }
    }
  }
}
