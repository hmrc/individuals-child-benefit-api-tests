package uk.gov.hmrc.test.api.steps

class CommonApiSteps {

  private val requestHelper: RequestHelper;

    @Steps(shared = true)
    protected CommonResponseSteps commonResponseSteps;

    //
    // Helper methods to save indirection to old.steps
    //
    public void response(ValidatableResponse response) {
        commonResponseSteps.response(response);
    }

    public ValidatableResponse response() {
        return Validate.notNull(commonResponseSteps.response(), "The response is not assigned");
    }

    public RequestSpecification specification() {
        return requestHelper.specification();
    }

    public HmrcRequestSpecBuilder builder() {
        return requestHelper.builder();
    }
}
