package uk.gov.hmrc.test.api.oauth

import uk.gov.hmrc.test.api.conf.TestConfiguration

class TotpHelper {

  private var totpCode: Option[String] = None

  private def generateNextTotpCode(): String = {
    totpCode = Some(NextTotpGenerator.getNextTotpCode(TestConfiguration.totpSecret));
    totpCode.get
  }

  def getTotpCode(): String = {
    if(totpCode.isEmpty)
      totpCode = Some(generateNextTotpCode())

    totpCode.get
  }
}
