package uk.gov.hmrc.test.apis.helpers

import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import io.restassured.authentication.AuthenticationScheme
import io.restassured.http.ContentType

class HmrcRequestSpecBuilder {
  
  private var inner: RequestSpecBuilder = new RequestSpecBuilder()
  private var needsDefaultHeader: Boolean = true

  def build(): RequestSpecification = {
      applyDefaults();
      inner.build();
  }

  def applyDefaults(): Unit = {
    if (needsDefaultHeader) {
        setAccept("application/vnd.hmrc.1.0+json");
    }
  }

  def setAccept(mediaType: String): HmrcRequestSpecBuilder = {
      inner = inner.setAccept(mediaType)
      needsDefaultHeader = false
      this
  }

  def setNoAccept(): HmrcRequestSpecBuilder = {
      needsDefaultHeader = false
      this
  }

  def withNoContentTypeHeader(s: String): HmrcRequestSpecBuilder = {
      inner = inner.setBody(s)
      this
  }

  def withJsonContentTypeHeader(): HmrcRequestSpecBuilder = {
      inner = inner.setContentType(ContentType.JSON)
      this
  }

  def withInvalidJsonContentTypeHeader(): HmrcRequestSpecBuilder = {
      inner = inner.setContentType(ContentType.TEXT)
      this
  }

  def withJsonBody(s: String): HmrcRequestSpecBuilder = {
      inner = inner.setBody(s).setContentType(ContentType.JSON)
      this
  }

  def withXmlBody(s: String): HmrcRequestSpecBuilder = {
      inner = inner.setBody(s).setContentType(ContentType.XML)
      this
  }


  def setAuth(authenticationScheme: AuthenticationScheme): HmrcRequestSpecBuilder = {
      inner = inner.setAuth(authenticationScheme);
      this;
  }
}
