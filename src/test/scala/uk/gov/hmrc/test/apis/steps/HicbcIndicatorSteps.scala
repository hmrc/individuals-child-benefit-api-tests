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

trait HicbcIndicatorSteps
    extends IndividualsChildBenefitsApi
    with CommonRequestSteps
    with CommonResponseSteps {

  def iUpdateTheHicbcIndicatorEndpointWithValidOptInPayload(): Unit =
    iUpdateTheHicbcIndicatorEndpointWithPayload("""{
      "nino": "AB123456A",
      "optInFrom": "2024-04-02"
    }""")

  def iUpdateTheHicbcIndicatorEndpointWithValidOptOutPayload(): Unit =
    iUpdateTheHicbcIndicatorEndpointWithPayload("""{
      "nino": "AB123456A",
      "optOutFrom": "2024-04-02"
    }""")

  def iUpdateTheHicbcIndicatorEndpointWithNoNinoPayload(): Unit =
    iUpdateTheHicbcIndicatorEndpointWithPayload("""{
      "optInFrom": "2024-04-02"
    }""")

  def iUpdateTheHicbcIndicatorEndpointWithNoOptFieldsPayload(): Unit =
    iUpdateTheHicbcIndicatorEndpointWithPayload("""{
      "nino": "AB123456A"
    }""")

  def iUpdateTheHicbcIndicatorEndpointWithBothOptFieldsPayload(): Unit =
    iUpdateTheHicbcIndicatorEndpointWithPayload("""{
      "nino": "AB123456A",
      "optInFrom": "2024-04-02",
      "optOutFrom": "2025-05-02"
    }""")


  def iUpdateTheHicbcIndicatorEndpointWithUnprocessableEntity(): Unit =
    iUpdateTheHicbcIndicatorEndpointWithPayload(
      """{
        "nino": "CB190600D",
        "optInFrom": "2024-04-02"
      }""")

  def iUpdateTheHicbcIndicatorEndpointWithEmptyPayload(): Unit =
    iUpdateTheHicbcIndicatorEndpointWithPayload("{}")
}
