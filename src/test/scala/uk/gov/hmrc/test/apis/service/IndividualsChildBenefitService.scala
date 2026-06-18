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

package uk.gov.hmrc.test.api.service

import play.api.libs.ws.StandaloneWSResponse

import uk.gov.hmrc.apitestrunner.http.HttpClient

import uk.gov.hmrc.test.api.models.CreateClaim

import scala.concurrent.Await
import scala.concurrent.duration._
import play.api.libs.json.Json
import play.api.libs.ws.JsonBodyWritables.writeableOf_JsValue
import uk.gov.hmrc.test.api.conf.TestConfiguration

// class IndividualsChildBenefitService extends HttpClient {
//   val host: String                        = TestConfiguration.url("icb")
//   val individualsChildBenefitURL: String  = s"$host/"

//   def someEndpoint(claim: CreateClaim, headerOverrides: (String, String)*): StandaloneWSResponse = {
//     val payload = Json.toJsObject(claim)

//     val defaultHeaders = Map(
//       "Authorization" -> "",//??? /*auth token*/,
//       "CorrelationId" -> "12345678",
//       "Accept"        -> "application/vnd.hmrc.P1.0+json"
//     )

//     val headers = defaultHeaders ++ headerOverrides

//     Await.result(
//       mkRequest(individualsChildBenefitURL + "/individuals/child-benefit/child-benefit/claim")
//       .withHttpHeaders((headers.toList)*)
//         .post(payload),
//       10.seconds
//     )
//   }
// }
