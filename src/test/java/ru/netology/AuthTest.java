package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static ru.netology.data.RegistrationData.*;
import static ru.netology.data.RegistrationData.Registration.*;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldGenerateNewActiveUserWithHardcodedData() {
        given()
                .spec(requestSpec)
                .body(new UserInfo("Vasya", "12345", "active")) // передаём в теле объект, который будет преобразован в JSON
                .when()
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then()
                .statusCode(200);
    }

    @Test
    void shouldGenerateNewBlockedUserWithHardcodedData() {
        given()
                .spec(requestSpec)
                .body(new UserInfo("Katya", "12345", "blocked")) // передаём в теле объект, который будет преобразован в JSON
                .when()
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then()
                .statusCode(200);
    }

    @Test
        void shouldLoginIfRegisteredActiveUser() {
        UserInfo registeredUser = getRegisteredUser("active");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(String.valueOf(registeredUser));
        form.$("[data-test-id=password] input").setValue(String.valueOf(registeredUser));
        form.$(".button[data-test-id=action-login]").click();
        $(".heading").find(withText("Личный кабинет"));
    }


    @Test
        void shouldGetErrorIfNotRegisteredUser() {
        UserInfo notRegisteredUser = getNotRegisteredUser();
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(String.valueOf(notRegisteredUser));
        form.$("[data-test-id=password] input").setValue(String.valueOf(notRegisteredUser));
        form.$(".button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        UserInfo registeredUser = getRegisteredUser("blocked");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(String.valueOf(registeredUser));
        form.$("[data-test-id=password] input").setValue(String.valueOf(registeredUser));
        form.$(".button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldGetErrorIfWrongLogin() {
        UserInfo registeredUser = getRegisteredUser("active");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(String.valueOf(getRandomLogin()));
        form.$("[data-test-id=password] input").setValue(String.valueOf(registeredUser));
        form.$(".button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
        void shouldGetErrorIfWrongPassword() {
        UserInfo registeredUser = getRegisteredUser("active");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(String.valueOf(registeredUser));
        form.$("[data-test-id=password] input").setValue(String.valueOf(getRandomPassword()));
        form.$(".button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }
}
