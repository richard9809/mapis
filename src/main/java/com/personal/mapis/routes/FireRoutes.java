package com.personal.mapis.routes;

import com.personal.mapis.models.dto.FireResponseDTO;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class FireRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:fire-create-clearance")
                .routeId("fire-create-clearance")
                .log("Calling FIRE API to create clearance for permitId=${body.permitId}")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .marshal().json(JsonLibrary.Jackson)
                .toD("{{agencies.fire.base-url}}/clearances")
                .unmarshal().json(JsonLibrary.Jackson, FireResponseDTO.class)
                .log("FIRE API responded with clearanceId=${body.clearanceId}, status=${body.status}");
    }
}
