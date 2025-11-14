package com.personal.mapis.routes;

import com.personal.mapis.models.dto.HealthResponseDTO;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class HealthRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:health-create-approval")
                .routeId("health-create-approval")
                .log("Calling HEALTH API to create inspection for permitId=${body.permitId}")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .marshal().json(JsonLibrary.Jackson)
                .toD("{{agencies.health.base-url}}/sanitation/approvals")
                .unmarshal().json(JsonLibrary.Jackson, HealthResponseDTO.class)
                .log("HEALTH API responded with approvalId=${body.approvalId}, status=${body.status}");
    }
}
