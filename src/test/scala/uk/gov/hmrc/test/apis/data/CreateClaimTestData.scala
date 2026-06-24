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

package uk.gov.hmrc.test.apis.data

import uk.gov.hmrc.test.apis.common.*

object CreateClaimTestData {
  val validClaimant = Claimant(
    nino = Some("AB123456A"),
    nationality = Some("UK_OR_CTA"),
    alwaysLivedInUK = Some(true),
    hicbcOptOut = Some(false),
    hmfAbroad = Some(true)
  )

  val partner = Partner(
    nino = Some("AB891870"),
    surname = Some("Brown")
  )

  val child = Children(
    name = Some(
      ClaimName(
        forenames = Some("Daniel"),
        surname = Some("Paul"),
        middleNames = Some("Alexander")
      )
    ),
    crn = Some("AE808187"),
    gender = Some("UNSPECIFIED"),
    dateOfBirth = Some("29/11/2012"),
    countryOfRegistration = Some("ENGLAND_WALES"),
    birthRegistrationNumber = Some("4938863476"),
    livingWithClaimant = Some(true),
    claimantIsParent = Some(false),
    adoptionStatus = Some(false)
  )

  val accountHolder = AccountHolder(
    accountHolderType = Some("SOMEONE_ELSE"),
    forenames = Some("John"),
    surname = Some("Paul")
  )

  val payment = Payment(
    paymentFrequency = Some("EVERY_4_WEEKS"),
    paymentDetails = Some(
      PaymentDetails(
        accountHolder = Some(accountHolder),
        bankAccount = Some(
          BankAccount(
            sortCode = Some("245533"),
            accountNumber = Some("98015737")
          )
        ),
        buildingSocietyDetails = None
      )
    )
  )

  val validCreateClaim = CreateClaim(
    dateOfClaim = Some("19/01/2023"),
    claimant = Some(validClaimant),
    partner = Some(partner),
    payment = Some(payment),
    children = Some(List(child)),
    otherEligibilityFailure = Some(false)
  )
}
