package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardOrderInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.RegistrationData.*;
import static ru.netology.data.RegistrationData.Registration.getRegisteredUser;
import static ru.netology.data.RegistrationData.Registration.getUser;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldGenerateNewUser() {
        UserInfo newUser = Registration.getRegisteredUser("active");
        UserInfo newUser2 = Registration.getRegisteredUser("blocked");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldLoginIfRegisteredActiveUser() {
        UserInfo registeredUser = Registration.getRegisteredUser("active");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(String.valueOf(registeredUser));
        form.$("[data-test-id=password] input").setValue(String.valueOf(registeredUser));
        form.$("[data-test-id=action-login] input").click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + CardOrderInfo.generateDate(5)));
    }


//    @Test
//    @DisplayName("Should get error message if login with not registered user")
//    void shouldGetErrorIfNotRegisteredUser() {
//        UserInfo notRegisteredUser = getUser("active");
//        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
//        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
//    }
//
//    @Test
//    @DisplayName("Should get error message if login with blocked registered user")
//    void shouldGetErrorIfBlockedUser() {
//        UserInfo blockedUser = getRegisteredUser("blocked");
//        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
//        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
//    }
//
//    @Test
//    @DisplayName("Should get error message if login with wrong login")
//    void shouldGetErrorIfWrongLogin() {
//        UserInfo registeredUser = getRegisteredUser("active");
//        String wrongLogin = getRandomLogin();
//        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
//        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
//        //  "Пароль" - пользователя registeredUser
//    }
//
//    @Test
//    @DisplayName("Should get error message if login with wrong password")
//    void shouldGetErrorIfWrongPassword() {
//        UserInfo registeredUser = getRegisteredUser("active");
//        String wrongPassword = getRandomPassword();
//        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
//        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
//        //  "Пароль" - переменную wrongPassword
//    }
//

}
