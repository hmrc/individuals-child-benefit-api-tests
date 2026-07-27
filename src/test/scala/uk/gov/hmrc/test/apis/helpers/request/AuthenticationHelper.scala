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

trait AuthenticationHelper extends AuthHelper {
  self: RequestHelper =>

  def authenticate(): HmrcRequestSpecBuilder =
    builder.setAuth(authenticateAndExtractBearer)

  def withInvalidAuthHeader(): HmrcRequestSpecBuilder =
    builder.setAuth("Bearer 123")

  def withMissingAuthHeaderValue(): HmrcRequestSpecBuilder =
    builder.setAuth("")

  def withNoAuthHeader(): HmrcRequestSpecBuilder =
    builder.setNoAuth()

  def withExpiredAuthHeader(): HmrcRequestSpecBuilder =
    builder.setAuth(
      "Bearer BXQ3/Treo4kQCZvVcCqKPrCyt041gc3fUvEnKccCB/7AxuI4aQDaoz3pQsgEBNYN3AqfWySOltI1mb198EX71ip+Jpzc7HORcnAb7yrOSRkmOdP9L8moOVDPVbTn89UG4BGVQBZ5qiLONk7oyqCs2SLdaWtwpen0nS2e9XTqlS+vazTTlox+CDBpvIoTXOJy/SsCJHiDyv5jJQREo7nuFQ==,GNAP dummy-60d76ef6cd71453fbef19b1d77797ddc"
    )
}
