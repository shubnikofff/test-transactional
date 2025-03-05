package com.shubnikofff.testtransactional;


import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class LikeSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080")
        .acceptHeader("application/json")
        .contentTypeHeader("application/json");

    FeederBuilder<String> feeder = ssv("authors.csv").random();

    ScenarioBuilder scenario = scenario("Like Author")
        .feed(feeder)
        .exec(http("Post like")
            .post("/like")
            .body(StringBody("""
                {"authorName": "#{author_name}", "userName": "#{randomAlphanumeric(10)}", "amount":  "#{randomInt(1,6)}"}
                """))
        );

    {
        setUp(
            scenario.injectOpen(rampUsersPerSec(10).to(1_000).during(10))
                .disablePauses()
                .protocols(httpProtocol)
        );
    }

}
