package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class Registration {
    public Registration() {
    }

    private Registration sendRequest(Registration user) {
        final RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        given()
                .spec(requestSpec)
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when()
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then()
                .statusCode(200);

        return user;
    }

    private static final Faker faker = new Faker(new Locale("en"));

    public static String getRandomLogin() {
        String login = String.valueOf(faker.funnyName());
        return login;
    }

    public static String getRandomPassword() {
        String password = String.valueOf(faker.crypto());
        return password;
    }

    public static Registration getUser(String status) {
        Object user = Registration.getRandomLogin() + Registration.getRandomPassword() + status;
        return (Registration) user;
    }

    public static Registration getRegisteredUser(String status) {
        Registration registeredUser = Registration.getUser(status);
        new Registration().sendRequest(registeredUser);
        return registeredUser;
    }


}
