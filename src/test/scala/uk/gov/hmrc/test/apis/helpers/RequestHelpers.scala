package uk.gov.hmrc.test.apis.helpers

import io.restassured.specification.RequestSpecification

class RequestHelpers {
  
  private var bldr: HmrcRequestSpecBuilder = new HmrcRequestSpecBuilder();

  def builder(): HmrcRequestSpecBuilder = {
      return bldr;
  }

  def specification(): RequestSpecification = {
      return bldr.build();
  }

  // @Before
  def cleanup(): Unit = {
      bldr = new HmrcRequestSpecBuilder();
  }
}