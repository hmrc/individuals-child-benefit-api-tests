package uk.gov.hmrc.test.api.specs

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.GivenWhenThen

trait BaseSpec extends AnyFeatureSpec with GivenWhenThen with Matchers {}
