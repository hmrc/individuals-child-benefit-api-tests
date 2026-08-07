#!/usr/bin/env bash

ENVIRONMENT=$1

sbt clean -Denvironment="${ENVIRONMENT:=local}" "testOnly uk.gov.hmrc.test.apis.specs.*"

sbt clean -Denvironment="${ENVIRONMENT:=staging}" "testOnly uk.gov.hmrc.test.apis.specs.*"
