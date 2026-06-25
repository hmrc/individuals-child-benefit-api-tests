**This is the template README. Please update this with project specific content.**

# individuals-child-benefit-api-tests

individuals-child-benefit API tests.

## Pre-requisites

### Services

Start `INDIVIDUALS_CHILD_BENEFIT_API_ALL` services as follows:

```bash
sm2 --start INDIVIDUALS_CHILD_BENEFIT_API_ALL
```

## Tests

Run tests as follows:

* Argument `<environment>` must be `local` at the present time.

```bash
./run_tests.sh <environment>
```

## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
