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

package uk.gov.hmrc.test.apis.helpers

import scala.util.Random

object NinoPrefixGenerator {

  private val ValidChar1: List[Char] = ('A' to 'Z').filterNot(c => "DFIQUV".contains(c)).toList
  private val ValidChar2: List[Char] = ('A' to 'Z').filterNot(c => "DFIOQUV".contains(c)).toList
  private val InvalidPrefixes: Set[String] = Set("BG", "GB", "KN", "NK", "NT", "TN", "ZZ")


  def generateFirst5(): String = {
    val prefix = getValidPrefix()
    val digits = (1 to 3).map(_ => Random.nextInt(10)).mkString
    s"$prefix$digits"
  }

  // Tail-recursive helper method to keep memory footprint safe
  @scala.annotation.tailrec
  private def getValidPrefix(): String = {
    val c1 = ValidChar1(Random.nextInt(ValidChar1.length))
    val c2 = ValidChar2(Random.nextInt(ValidChar2.length))
    val prefix = s"$c1$c2"

    if (InvalidPrefixes.contains(prefix)) getValidPrefix() else prefix
  }
}
