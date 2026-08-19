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
