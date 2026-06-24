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

package uk.gov.hmrc.test.apis.common

import play.api.libs.json.{Json, OFormat}

case class Claimant(
  nino: Option[String] = None,
  nationality: Option[String] = None,
  alwaysLivedInUK: Option[Boolean] = None,
  rightToReside: Option[Boolean] = None,
  last3MonthsInUK: Option[Boolean] = None,
  hmfAbroad: Option[Boolean] = None,
  hicbcOptOut: Option[Boolean] = None
)

object Claimant {
  given format: OFormat[Claimant] = Json.format[Claimant]
}

case class Partner(nino: Option[String], surname: Option[String])

object Partner {
  given format: OFormat[Partner] = Json.format[Partner]
}

case class BankAccount(sortCode: Option[String], accountNumber: Option[String])

object BankAccount {
  given format: OFormat[BankAccount] = Json.format[BankAccount]
}

case class BuildingSocietyDetails(buildingSociety: Option[String], rollNumber: Option[String])

object BuildingSocietyDetails {
  given format: OFormat[BuildingSocietyDetails] = Json.format[BuildingSocietyDetails]
}
case class AccountHolder(
  accountHolderType: Option[String] = None,
  forenames: Option[String] = None,
  surname: Option[String] = None
)

object AccountHolder {
  implicit val format: OFormat[AccountHolder] = Json.format[AccountHolder]
}

case class PaymentDetails(
  accountHolder: Option[AccountHolder],
  bankAccount: Option[BankAccount],
  buildingSocietyDetails: Option[BuildingSocietyDetails]
)

object PaymentDetails {
  given format: OFormat[PaymentDetails] = Json.format[PaymentDetails]
}
case class Payment(paymentFrequency: Option[String], paymentDetails: Option[PaymentDetails])

object Payment {
  given format: OFormat[Payment] = Json.format[Payment]
}

case class ClaimName(forenames: Option[String], surname: Option[String], middleNames: Option[String])

object ClaimName {
  implicit val format: OFormat[ClaimName] = Json.format[ClaimName]
}

case class Children(
  name: Option[ClaimName] = None,
  crn: Option[String] = None,
  gender: Option[String] = None,
  dateOfBirth: Option[String] = None,
  countryOfRegistration: Option[String] = None,
  birthRegistrationNumber: Option[String] = None,
  dateOfBirthVerified: Option[Boolean] = None,
  livingWithClaimant: Option[Boolean] = None,
  claimantIsParent: Option[Boolean] = None,
  adoptionStatus: Option[Boolean] = None
)

object Children {
  implicit val format: OFormat[Children] = Json.format[Children]
}

case class CreateClaim(
  dateOfClaim: Option[String],
  claimant: Option[Claimant],
  partner: Option[Partner],
  payment: Option[Payment],
  children: Option[List[Children]],
  otherEligibilityFailure: Option[Boolean]
)

object CreateClaim {
  given format: OFormat[CreateClaim] = Json.format[CreateClaim]
}
