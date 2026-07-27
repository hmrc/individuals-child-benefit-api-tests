package uk.gov.hmrc.test.apis.steps

import uk.gov.hmrc.test.apis.common.IndividualsChildBenefitsApi
import uk.gov.hmrc.test.apis.helpers.request.CommonRequestSteps
import uk.gov.hmrc.test.apis.helpers.response.CommonResponseSteps

trait CommonSteps extends IndividualsChildBenefitsApi with CommonRequestSteps with CommonResponseSteps {

  def iMakeARequestToTheChildVerificationEndpointWithAValidPayload(
    scenario: String,
    firstName: String,
    secondName: String,
    dateOfBirth: String,
    nino: String,
    bornOnOrAfterDate: String,
    responseStatusCode: Int
  ): Unit = {
    val payloadWithValidData =
      s"""{
          |   "firstName": "$firstName",
          |   "secondName": "$secondName",
          |   "dateOfBirth": "$dateOfBirth",
          |   "nino": "$nino",
          |   "bornOnOrAfter": "$bornOnOrAfterDate"
          | }""".stripMargin
    iMakeARequestToTheIndividualChildBenefitsChildVerificationEndpointWithPayload(payloadWithValidData)
  }

}
