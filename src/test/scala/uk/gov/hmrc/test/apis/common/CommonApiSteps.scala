package uk.gov.hmrc.test.apis.common

import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification

class CommonApiSteps {
  private val requestHelper: RequestHelper;

  protected val commonResponseSteps: CommonResponseSteps = new CommonResponseSteps();

    //
    // Helper methods to save indirection to old.steps
    //
    def response(response: ValidatableResponse): Unit = {
        commonResponseSteps.response(response);
    }

    def response(): ValidatableResponse = {
      commonResponseSteps.response()
    }

    def specification(): RequestSpecification = {
        return requestHelper.specification();
    }

    def builder(): HmrcRequestSpecBuilder = {
        return requestHelper.builder();
    }
}
