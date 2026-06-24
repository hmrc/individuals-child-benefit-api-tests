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

import uk.gov.hmrc.test.apis.helpers.RequestHelper

// TODO: this may be a duplicate of the same file in the steps package
class CommonApiSteps extends RequestHelper with CommonResponseSteps {

  def iGetASuccessfulResponse(): Unit =
    expectedHttpStatusCode(200)

  def iGetASuccessfulCreatedResponse(): Unit =
    expectedHttpStatusCode(201)

  def iGetAServiceTemporarilyUnavailableResponse(): Unit = {
    expectedHttpStatusCode(503)
    expectedJsonErrorCode("SERVER_ERROR")
    expectedJsonMessage("Service unavailable")
  }

  def iGetAMatchingResourceNotFoundResponse(): Unit = {
    expectedHttpStatusCode(404)
    expectedArrayJsonErrorCode("NOT_FOUND_IDENTIFIER")
    expectedArrayJsonMessage("The remote endpoint has indicated that the identifier provided cannot be found.")
  }

  def iGetANotAcceptableResponseDueToAnInvalidAcceptHeader(): Unit =
    iGetAnAcceptHeaderInvalidResponse()

  def iGetABadRequestResponseDueToAnInvalidRequestPayload(): Unit = {
    iGetAnInvalidRequestPayloadResponse()
    expectedJsonMessage("JSON body is invalid against expected format")
  }

  def iGetAnUnacceptableResponseDueToAMissingAcceptHeader(): Unit =
    iGetAnAcceptHeaderInvalidResponse()

  def iGetAnUnsupportedMediaTypeResponse(): Unit = {
    expectedHttpStatusCode(415)
    expectedArrayJsonMessage("Expecting text/json or application/json body")
  }
}
