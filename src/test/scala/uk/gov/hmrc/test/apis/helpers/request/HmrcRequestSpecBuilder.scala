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

package uk.gov.hmrc.test.apis.helpers.request

import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import io.restassured.http.ContentType

class HmrcRequestSpecBuilder {

  private var inner: RequestSpecBuilder   = new RequestSpecBuilder()
  private var needsDefaultHeader: Boolean = true

  def build(): RequestSpecification = {
    applyDefaults();
    inner.build();
  }

  def applyDefaults(): Unit =
    if (needsDefaultHeader) {
      setAccept("application/vnd.hmrc.1.0+json");
    }

  def setAccept(mediaType: String): HmrcRequestSpecBuilder = {
    inner = inner.setAccept(mediaType)
    needsDefaultHeader = false
    this
  }

  def setNoAccept(): HmrcRequestSpecBuilder = {
    needsDefaultHeader = false
    this
  }

  def withNoContentTypeHeader(s: String): HmrcRequestSpecBuilder = {
    inner = inner.setBody(s)
    this
  }

  def withJsonContentTypeHeader(): HmrcRequestSpecBuilder = {
    inner = inner.setContentType(ContentType.JSON)
    this
  }

  def withInvalidJsonContentTypeHeader(): HmrcRequestSpecBuilder = {
    inner = inner.setContentType(ContentType.TEXT)
    this
  }

  def withJsonBody(s: String): HmrcRequestSpecBuilder = {
    inner = inner.setBody(s).setContentType(ContentType.JSON)
    this
  }

  def withXmlBody(s: String): HmrcRequestSpecBuilder = {
    inner = inner.setBody(s).setContentType(ContentType.XML)
    this
  }

  def setAuth(token: String): HmrcRequestSpecBuilder = {
    inner = inner.addHeader("Authorization", token) // inner.setAuth(oauth2(token));
    this;
  }
}
