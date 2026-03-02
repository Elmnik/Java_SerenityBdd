package com.bdg.api.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.interactions.Put;
import io.restassured.http.ContentType;

public class GenericRest implements Interaction {

    private final String method;
    private final String resource;
    private final Object body; // Cambiado de String a Object

    public GenericRest(String method, String resource, Object body) {
        this.method = method;
        this.resource = resource;
        this.body = body;
    }

    public static GenericRest execute(String method, String resource, Object body) {
        return instrumented(GenericRest.class, method, resource, body);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        switch (method.toUpperCase()) {
            case "GET":
                actor.attemptsTo(Get.resource(resource));
                break;
            case "POST":
                actor.attemptsTo(
                        Post.to(resource)
                                .with(request -> request
                                        .contentType(ContentType.JSON) // Más limpio que el header manual
                                        .body(body))
                );
                break;
            case "PUT":
                actor.attemptsTo(
                        Put.to(resource)
                                .with(request -> request
                                        .contentType(ContentType.JSON)
                                        .body(body))
                );
                break;
            case "DELETE":
                actor.attemptsTo(Delete.from(resource));
                break;
            default:
                throw new IllegalArgumentException("HTTP Method not supported: " + method);
        }
    }
}