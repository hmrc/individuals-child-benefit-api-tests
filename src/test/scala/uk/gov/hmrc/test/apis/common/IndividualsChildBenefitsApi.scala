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

package uk.gov.hmrc.test.apis.common

import io.restassured.RestAssured.`given`;
import uk.gov.hmrc.test.apis.conf.TestConfiguration.*
import uk.gov.hmrc.test.apis.helpers.request.RequestHelper
import uk.gov.hmrc.test.apis.helpers.response.ResponseHelper

trait IndividualsChildBenefitsApi extends RequestHelper with ResponseHelper {

  def iMakeARequestToThePostChildBenefitsClaimEndpointWithPayload(payload: String): Unit =
    response(
      `given`()
        .spec(specification())
        .body(payload)
        .log()
        .all()
        .post(s"$baseApiUrl/child-benefit/claim")
        .`then`()
        .log()
        .all()
    )

  def iMakeARequestToTheIndividualChildBenefitsRegistrationEndpointWithPayload(payload: String): Unit =
    response(
      `given`()
        .spec(specification())
        .body(payload)
        .log()
        .all()
        .post(s"$baseApiUrl/birth-registration-matching/match")
        .`then`()
        .log()
        .all()
    )

  def callGetRelationshipDetails(idValue: String): Unit = {
    println(s"Calling $baseApiUrl/individuals/relationship/$idValue")
    response(
      `given`()
        .spec(specification())
        .get(s"$baseApiUrl/individuals/relationship/$idValue")
        .`then`()
        .log()
        .all()
    )
  }

  def callGetIndividualDetails(idValue: String, resolveMerge: String): Unit = {
    println(s"Calling $baseApiUrl/individuals/details/$idValue/$resolveMerge")
    response(
      `given`()
        .spec(specification())
        .get(s"$baseApiUrl/individuals/details/$idValue/$resolveMerge")
        .`then`()
        .log()
        .all()
    )
  }
}
