package uk.gov.hmrc.test.api.oauth

import io.restassured.specification.RequestSpecification
import uk.gov.hmrc.test.api.conf.TestConfiguration.*
import io.restassured.response.ValidatableResponse;
import io.restassured.RestAssured.`given`
import io.restassured.http.ContentType


abstract class AbstractOauthHelper {

  protected var lastOauthResponse: Option[ValidatableResponse] = None

  protected final def callOauthTokenEndpoint(spec: RequestSpecification): ValidatableResponse = {
      val oauthTokenUrl = s"${baseApiUrl}/oauth/token"

      lastOauthResponse = Some(spec
              .when()
              .post(oauthTokenUrl)
              .`then`())

      lastOauthResponse.get
  }

  def oauthRequestSpecification(grantType: String, params: Map[String, String]) = {
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

  def assertLastOauthCallSucceeded(): Unit = {
    import io.restassured.http.ContentType.JSON;
    import org.hamcrest.CoreMatchers.{is, not};

      lastOauthResponse.get
        .statusCode(200)
        .contentType(JSON)
        .body("access_token", not(is("")))
  }
}

