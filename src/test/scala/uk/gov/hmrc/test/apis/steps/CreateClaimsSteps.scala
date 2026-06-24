/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.test.apis.steps

import uk.gov.hmrc.test.apis.common.IndividualsChildBenefitsApi
import uk.gov.hmrc.test.apis.helpers.request.CommonRequestSteps
import uk.gov.hmrc.test.apis.helpers.response.CommonResponseSteps

trait CreateClaimsSteps extends IndividualsChildBenefitsApi with CommonRequestSteps with CommonResponseSteps {

  def iMakeARequestToThePostChildBenefitsClaimEndpointWithAValidPayload(): Unit =
    iMakeARequestToThePostChildBenefitsClaimEndpointWithPayload("""{
                |  "dateOfClaim": "19/01/2023",
                |  "claimant": {
                |    "nino": "AB123456A",
                |    "nationality": "UK_OR_CTA",
                |    "alwaysLivedInUK": true,
                |    "hicbcOptOut": false,
                |    "hmfAbroad": true
                |  },
                |  "partner": {
                |    "nino": "AB891870",
                |    "surname": "Brown"
                |  },
                |  "payment": {
                |    "paymentFrequency": "EVERY_4_WEEKS",
                |    "paymentDetails": {
                |      "bankAccount": {
                |        "sortCode": "245533",
                |        "accountNumber": "98015737"
                |      },
                |      "accountHolder": {
                |        "accountHolderType": "SOMEONE_ELSE",
                |        "forenames": "John",
                |        "surname": "Paul"
                |      }
                |    }
                |  },
                |  "children": [
                |    {
                |      "name": {
                |        "forenames": "Daniel",
                |        "surname": "Paul",
                |        "middleNames": "Alexander"
                |      },
                |      "crn": "AE808187",
                |      "gender": "UNSPECIFIED",
                |      "dateOfBirth": "29/11/2012",
                |      "countryOfRegistration": "ENGLAND_WALES",
                |      "birthRegistrationNumber": "4938863476",
                |      "livingWithClaimant": true,
                |      "claimantIsParent": false,
                |      "adoptionStatus": false
                |    }
                |  ],
                |  "otherEligibilityFailure": false
                |}""".stripMargin)

  def iMakeARequestToThePostChildBenefitsClaimEndpointWithAnInvalidPayload(): Unit =
    iMakeARequestToThePostChildBenefitsClaimEndpointWithPayload(
      """{"claimant":{"nino":"WK666666"}}"""
    )
}
