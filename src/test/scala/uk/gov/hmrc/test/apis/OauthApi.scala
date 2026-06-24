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

package uk.gov.hmrc.test.apis

import io.restassured.specification.RequestSpecification
import uk.gov.hmrc.test.api.conf.TestConfiguration.*
import uk.gov.hmrc.test.api.oauth.AbstractOauthHelper
import uk.gov.hmrc.test.api.oauth.TotpHelper
import uk.gov.hmrc.test.api.oauth.AccessTokenHolder

class OauthAPI extends AbstractOauthHelper {

  private val totpHelper = new TotpHelper()

  private def oauthRequestSpec(
    clientId: String,
    totpCode: String,
    grantType: String = "client_credentials"
  ): RequestSpecification =
    oauthRequestSpecification(
      grantType,
      Map(
        "client_id"     -> clientId,
        "client_secret" -> totpCode
      )
    )

  def successfullyGenerateAccessToken(): Unit = {
    val spec = oauthRequestSpec(clientId, totpHelper.getTotpCode() + clientSecret)
    callOauthTokenEndpoint(spec)
    assertLastOauthCallSucceeded()
    AccessTokenHolder.extractAndStoreToken(lastOauthResponse.get)
  }
}
