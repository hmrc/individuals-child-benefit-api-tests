package uk.gov.hmrc.test.api.model

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
case class AccountHolder(accountHolderType: Option[String] = None, forenames: Option[String] = None, surname: Option[String] = None)

object AccountHolder {
  implicit val format: OFormat[AccountHolder] = Json.format[AccountHolder]
}

case class PaymentDetails(accountHolder: Option[AccountHolder], bankAccount: Option[BankAccount], buildingSocietyDetails: Option[BuildingSocietyDetails])

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
