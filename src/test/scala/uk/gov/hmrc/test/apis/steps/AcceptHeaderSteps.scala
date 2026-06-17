package uk.gov.hmrc.test.api.steps

class AcceptHeaderSteps {

  private val acceptHeaderHelper: AcceptHeaderHelper

    @Given("^I have an incorrect accept header version$")
    public void iHaveAnIncorrectAcceptHeaderVersion() {
        acceptHeaderHelper.withIncorrectAcceptHeaderVersion();
    }

    @Given("^I have no accept header$")
    public void iHaveNoAcceptHeader() {
        acceptHeaderHelper.withNoAcceptHeader();
    }

    @Given("^I have an invalid accept header$")
    public void iHaveAnInvalidAcceptHeader() {
        acceptHeaderHelper.withInvalidAcceptHeader();
    }
}
