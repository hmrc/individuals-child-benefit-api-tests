package uk.gov.hmrc.test.api.oauth

import uk.gov.hmrc.totp.HmacShaTotp
import uk.gov.hmrc.totp.JavaTotpSha512Generator
import scala.annotation.tailrec

object NextTotpGenerator {
  private var currentTotpCode: Option[String] = None

  private val totpGenerator: HmacShaTotp = JavaTotpSha512Generator()

  private def isSameAsPreviousTotpCode(newTotpCode: String): Boolean = currentTotpCode.fold(false)(_ == newTotpCode)

  private def safeSleep(millis: Long): Unit = {
    try {
        Thread.sleep(millis);
    } catch {
      case e: InterruptedException => {
        e.printStackTrace();
      }
    }
  }

  @tailrec
  def getNextTotpCode(totpSecret: String): String = {
    val newTotpCode = totpGenerator.getTotpCode((totpSecret.toUpperCase()))

    if(! isSameAsPreviousTotpCode(newTotpCode)) {
      currentTotpCode = Some(newTotpCode)
      newTotpCode
    }
    else {
      safeSleep(500)
      getNextTotpCode(totpSecret)
    }
  }
}
