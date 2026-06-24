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

package uk.gov.hmrc.test.apis.helpers.response

import io.restassured.http.ContentType
import org.hamcrest.Matchers.*
import io.restassured.http.ContentType.JSON;
import java.util.Collections.singletonList;

trait CommonResponseSteps {
  self: ResponseHelper =>

  def expectedHttpStatusCode(expectedStatus: Int): Unit =
    response().statusCode(equalTo(expectedStatus))

  def expectedContentType(expectedContentType: ContentType): Unit =
    response().contentType(equalTo(expectedContentType.getContentTypeStrings().apply(0)))

  def expectedJsonBodyField[T](field: String, expectation: T): Unit = {
    expectedContentType(JSON)
    response().body(field, is(expectation))
  }

  def expectedJsonErrorCode(expectedCode: String): Unit       = expectedJsonBodyField("code", expectedCode)
  def expectedJsonMessage(expectedMessage: String): Unit      = expectedJsonBodyField("message", expectedMessage)
  def expectedArrayJsonErrorCode(expectedCode: String): Unit  =
    expectedJsonBodyField("errors.code", singletonList(expectedCode))
  def expectedArrayJsonMessage(expectedMessage: String): Unit =
    expectedJsonBodyField("errors.message", singletonList(expectedMessage))

  def iGetNotFoundResponse(expectedErrorCode: String): Unit = {
    expectedJsonErrorCode(expectedErrorCode);
    expectedHttpStatusCode(404);
  }

  def iGetAnAcceptHeaderInvalidResponse(): Unit = {
    expectedHttpStatusCode(406);
    expectedArrayJsonErrorCode("ACCEPT_HEADER_INVALID");
    expectedArrayJsonMessage("The accept header is missing or invalid");
  }

  def iGetAnInvalidRequestPayloadResponse(): Unit = {
    expectedHttpStatusCode(400)
    expectedJsonErrorCode("INVALID_REQUEST_PAYLOAD")
  }

  def iGetAnInvalidPayloadResponse(): Unit = {
    expectedHttpStatusCode(400)
    expectedArrayJsonErrorCode("INVALID_PAYLOAD")
  }

  def iGetAnUnsupportedMediaTypeResponse(): Unit = {
    expectedHttpStatusCode(415)
    expectedArrayJsonMessage("Expecting text/json or application/json body")
  }

}
