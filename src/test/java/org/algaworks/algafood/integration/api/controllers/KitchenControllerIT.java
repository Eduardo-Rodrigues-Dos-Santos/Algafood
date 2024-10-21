package org.algaworks.algafood.integration.api.controllers;

import io.restassured.http.ContentType;
import org.algaworks.algafood.api.mapper.KitchenMapper;
import org.algaworks.algafood.api.models.input.KitchenInput;
import org.algaworks.algafood.core.DatabaseCleaner;
import org.algaworks.algafood.core.JsonReader;
import org.algaworks.algafood.domain.models.Kitchen;
import org.algaworks.algafood.domain.repositories.KitchenRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.yaml")
@ActiveProfiles("test")
class KitchenControllerIT {

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private KitchenMapper kitchenMapper;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Kitchen firstKitchen;
    private Integer numberOfRegisteredKitchens;
    private static final long KITCHEN_ID_NOT_REGISTERED = 100L;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    public void setup() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        port = serverPort;
        basePath = "/kitchens";
        databaseCleaner.clearTables();

        List<Kitchen> testKitchens = addDataForTesting();
        this.firstKitchen = testKitchens.get(0);
        this.numberOfRegisteredKitchens = testKitchens.size();
    }

    @Test
    void shouldReturnHttpStatusCode200AndCorrectlyBodyWhenConsultKitchenRegistered() {
        given().accept(ContentType.JSON).pathParam("id", firstKitchen.getId())
                .when().get("/{id}")
                .then().statusCode(HttpStatus.OK.value())
                .body("name", Matchers.equalTo(firstKitchen.getName()));
    }

    @Test
    void shouldReturnHttpStatusCode404WhenConsultKitchenNonRegistered() {
        given().accept(ContentType.JSON)
                .pathParam("id", KITCHEN_ID_NOT_REGISTERED)
                .when().get("/{id}")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldReturnHttpStatusCode200AndNumberKitchensEqualVariableNumberOfRegisteredKitchens() {
        given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(HttpStatus.OK.value())
                .body("numberOfElements", Matchers.equalTo(numberOfRegisteredKitchens));
    }

    @Test
    void shouldReturnHttpStatusCode201WhenPassBodyValid() {
        String kitchenJson = JsonReader.read(Paths.get("src/test/resources/json/validKitchen.json"));
        given().body(kitchenJson).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post()
                .then().statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void shouldReturnHttpStatusCode400WhenPassInvalidBody() {
        String kitchenJson = JsonReader.read(Paths.get("src/test/resources/json/invalidKitchen.json"));
        given().body(kitchenJson).contentType(ContentType.JSON)
                .when().post()
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private List<Kitchen> addDataForTesting() {
        return Stream.of(KitchenInput.builder().name("Americana").build(),
                KitchenInput.builder().name("Indiana").build()).map(kitchenInput
                -> kitchenRepository.save(kitchenMapper.toKitchen(kitchenInput))).toList();
    }
}