package com.personal.mapis.routes;

import com.personal.mapis.models.dto.EnvResponseDTO;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class EnvRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:env-create-inspection")
                .routeId("env-create-inspection")
                .log("Calling ENV API to create inspection for permitId=${body.permitId}")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .marshal().json(JsonLibrary.Jackson)
                .toD("{{agencies.env.base-url}}/inspection/requests")
                .unmarshal().json(JsonLibrary.Jackson, EnvResponseDTO.class)
                .log("ENV API responded with inspectionId=${body.inspectionId}, status=${body.status}");
    }
}
