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
        throw e;
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
