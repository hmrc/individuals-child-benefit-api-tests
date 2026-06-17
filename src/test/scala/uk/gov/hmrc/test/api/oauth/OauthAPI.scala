package uk.gov.hmrc.test.api.oauth

import io.restassured.specification.RequestSpecification
import uk.gov.hmrc.test.api.conf.TestConfiguration.*

class OauthAPI extends AbstractOauthHelper {
  
  private val totpHelper = new TotpHelper()

  private def oauthRequestSpec(clientId: String, totpCode: String, grantType: String = "client_credentials"): RequestSpecification = {
    oauthRequestSpecification(
      grantType,
      Map(
        "client_id" -> clientId,
        "client_secret" -> totpCode
      )
    )
  }

  def successfullyGenerateAccessToken(): Unit = {
    val spec = oauthRequestSpec(clientId, totpHelper.getTotpCode() + clientSecret)
        callOauthTokenEndpoint(spec)
        assertLastOauthCallSucceeded()
        AccessTokenHolder.extractAndStoreToken(lastOauthResponse.get)
    }
}
  