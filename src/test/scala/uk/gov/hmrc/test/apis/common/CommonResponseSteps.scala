package uk.gov.hmrc.test.apis.common

import io.restassured.response.ValidatableResponse;

class CommonResponseSteps {

  private var storedResponse: ValidatableResponse = ???

  def response(response: ValidatableResponse): Unit = {
    this.storedResponse = response;
  }

  def response(): ValidatableResponse = {
    storedResponse;
  }
}
