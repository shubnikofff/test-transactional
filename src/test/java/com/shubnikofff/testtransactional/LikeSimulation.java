package com.shubnikofff.testtransactional;


import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class LikeSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    ScenarioBuilder scenario = scenario("Like Author")
            .exec(http("Post like").post("/like")
                    .body(StringBody("""
                            {"authorName": "Bruce Wayne"}
                            """))
                    .check(bodyString().find().in("1"))
            );

    {
        setUp(
                scenario.injectOpen(rampUsersPerSec(10).to(1_000).during(10))
                        .disablePauses()
                        .protocols(httpProtocol)
        );
    }

}
