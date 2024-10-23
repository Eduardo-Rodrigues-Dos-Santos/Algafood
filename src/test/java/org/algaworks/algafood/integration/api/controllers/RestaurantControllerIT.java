package org.algaworks.algafood.integration.api.controllers;

import io.restassured.http.ContentType;
import org.algaworks.algafood.api.mapper.CityMapper;
import org.algaworks.algafood.api.mapper.KitchenMapper;
import org.algaworks.algafood.api.mapper.RestaurantMapper;
import org.algaworks.algafood.api.mapper.StateMapper;
import org.algaworks.algafood.api.models.input.*;
import org.algaworks.algafood.core.DatabaseCleaner;
import org.algaworks.algafood.core.JsonReader;
import org.algaworks.algafood.domain.models.City;
import org.algaworks.algafood.domain.models.Kitchen;
import org.algaworks.algafood.domain.models.Restaurant;
import org.algaworks.algafood.domain.models.State;
import org.algaworks.algafood.domain.repositories.CityRepository;
import org.algaworks.algafood.domain.repositories.KitchenRepository;
import org.algaworks.algafood.domain.repositories.RestaurantRepository;
import org.algaworks.algafood.domain.repositories.StateRepository;
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
@TestPropertySource("/application-test.yaml")
@ActiveProfiles("test")
class RestaurantControllerIT {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private StateMapper stateMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private KitchenMapper kitchenMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    private List<Restaurant> restaurants;
    private static final String NAME = "Ma";

    @BeforeEach
    public void setup() {
        port = this.serverPort;
        basePath = "/restaurants";
        enableLoggingOfRequestAndResponseIfValidationFails();
        databaseCleaner.clearTables();
        addDataForTesting();
    }

    @Test
    void shouldReturnHttpStatusCode200AndOnlyRestaurantsWithFreeDelivery() {
        given().accept(ContentType.JSON)
                .when().get("/freeDelivery")
                .then().statusCode(HttpStatus.OK.value())
                .body("totalElements", Matchers.equalTo(restaurants.size()));
    }

    @Test
    void shouldReturnHttpStatusCode201WhenPassValidBody() {
        String restaurantJson = JsonReader.read(Paths.get("src/test/resources/json/validRestaurant.json"));
        given().body(restaurantJson).contentType(ContentType.JSON)
                .when().post()
                .then().statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void shouldReturnHttpStatusCode400WhenPassInvalidBody() {
        String restaurantJson = JsonReader.read(Paths.get("src/test/resources/json/invalidRestaurant.json"));
        given().body(restaurantJson).contentType(ContentType.JSON)
                .when().post()
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldReturnHttpStatusCode200AndOnlyRestaurantsWithLakeNamePassInUrlWhenConsultRestaurantLikeByName() {
        given().param("name", NAME).accept(ContentType.JSON)
                .when().get("/by-name")
                .then().statusCode(HttpStatus.OK.value())
                .body("content[0].name", Matchers.containsString(NAME));

    }

    private void addDataForTesting() {

        State state = stateRepository.save(stateMapper.toState(new StateInput("Goiás")));

        List<City> cities = Stream.of(new CityInput("Aparecida", state.getId()),
                        new CityInput("Trindade", state.getId()))
                .map(cityInput -> cityRepository.save(cityMapper.toCity(cityInput))).toList();

        List<Kitchen> kitchens = Stream.of(new KitchenInput("Brasileira"), new KitchenInput("Americana"))
                .map(kitchenInput -> kitchenRepository.save(kitchenMapper.toKitchen(kitchenInput))).toList();

        AddressInput addressInput1 = AddressInput.builder()
                .cityId(cities.get(0).getId())
                .postalCode("57071554")
                .number("11")
                .complement("próximo a lugar nenhum")
                .district("Setor Central").build();

        AddressInput addressInput2 = AddressInput.builder()
                .cityId(cities.get(1).getId())
                .postalCode("76829200")
                .number("22")
                .complement("próximo a algum lugar")
                .district("Setor Central").build();

        this.restaurants = Stream.of(new RestaurantInput("Madeiro", kitchens.get(0).getId(), addressInput1),
                        new RestaurantInput("Quentinha Americana", kitchens.get(1).getId(), addressInput2))
                .map(restaurantInput -> restaurantRepository.save(restaurantMapper.toRestaurant(restaurantInput))).toList();
    }
}