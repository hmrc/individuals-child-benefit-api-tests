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

package uk.gov.hmrc.test.api.oauth

import io.restassured.specification.RequestSpecification
import uk.gov.hmrc.test.api.conf.TestConfiguration.*
import io.restassured.response.ValidatableResponse;
import io.restassured.RestAssured.`given`
import io.restassured.http.ContentType


abstract class AbstractOauthHelper {

  // TODO - static somewhere else
  protected var lastOauthResponse: Option[ValidatableResponse] = None

  protected final def callOauthTokenEndpoint(spec: RequestSpecification): ValidatableResponse = {
      val oauthTokenUrl = s"${baseApiUrl}/oauth/token"

      lastOauthResponse = Some(spec
              .when()
              .post(oauthTokenUrl)
              .`then`())

      lastOauthResponse.get
  }

  def oauthRequestSpecification(grantType: String, params: Map[String, String]): RequestSpecification = {
    val base: RequestSpecification = 
      `given`()
        .contentType(ContentType.URLENC)
        .accept("application/vnd.hmrc.1.0+json")
        .formParam("grant_type", grantType)

    // Add all parameters to the request specification
    params.foldRight(base){
      case (paramKV, requestSpec) => requestSpec.formParam(paramKV._1, paramKV._2)
    }
  }

  // TODO - this should be elsewhere
  def assertLastOauthCallSucceeded(): Unit = {
    import io.restassured.http.ContentType.JSON;
    import org.hamcrest.CoreMatchers.{is, not};

      lastOauthResponse.get
        .statusCode(200)
        .contentType(JSON)
        .body("access_token", not(is("")))
  }
}

