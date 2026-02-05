package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("")
public class ExampleResource {

    @GET
    @Path("/health")
    public String health() {
        return "UP";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/hello")
    public Response hello(@Context HttpHeaders headers) {
        // Print all headers to see what Kong did
        headers.getRequestHeaders().forEach((k, v) -> System.out.println(k + " = " + v));

        return Response.ok(Map.of(
                "message", "Hello from instance A",
                "headers_received", headers.getRequestHeaders()
        )).build();
    }
}
