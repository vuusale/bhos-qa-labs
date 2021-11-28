package test

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class RegisterSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:94.0) Gecko/20100101 Firefox/94.0")

  val scn = scenario("Scenario Name")
    .feed(csv("mock_data_100000.csv"))
    .exec(
      http("random_request")
        .post("/register")
        .formParam("email", "#{email}")
        .formParam("password", "#{password}")
    )

  setUp(scn.inject(
    constantUsersPerSec(500).during(200.seconds),
  ).protocols(httpProtocol))
}