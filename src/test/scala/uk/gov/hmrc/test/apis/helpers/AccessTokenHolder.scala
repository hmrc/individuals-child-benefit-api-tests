package uk.gov.hmrc.test.api.oauth

import io.restassured.response.ValidatableResponse

object AccessTokenHolder {
    
    private var accessToken: Option[String] = None

    def bearerToken() = accessToken.get

    def extractAndStoreToken(response: ValidatableResponse): Unit = {
        accessToken = Some(response.extract().path("access_token").toString())
    }
}