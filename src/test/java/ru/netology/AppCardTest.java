package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.CardOrderInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AppCardTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    public void shouldTestHappyPathWith1Meeting() {
                SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(CardOrderInfo.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(5));
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + CardOrderInfo.generateDate(5)));
    }

    @Test
    public void shouldTestHappyPathReplaning() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(CardOrderInfo.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(5));
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));

        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(12));
        form.$(".button").find(byText("Запланировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));
        $("[data-test-id=replan-notification]").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$(".button").find(exactText("Перепланировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + CardOrderInfo.generateDate(12)));
    }


    @Test
    public void shouldReturnErrorWhenCityNotInList() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Мытищи");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(5));
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldReturnErrorWhenCityNotInKirilitsa() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Moscow");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(5));
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldReturnErrorWhenCityIsEmpty() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(5));
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldReturnErrorWhenNameNotInKirilitsa() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(CardOrderInfo.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(5));
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("eng"));
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=name] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void shouldReturnErrorWhenNameWithSymbols() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(CardOrderInfo.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(5));
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru") + "@");
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=name] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void shouldReturnErrorWhenTelIsEmpty() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(CardOrderInfo.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(5));
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=phone] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldReturnErrorWhenAgreementEmpty() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(CardOrderInfo.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(5));
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]");
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=agreement]").shouldBe(appear);
    }

    @Test
    public void shouldReturnErrorWhenWrongFormatOfDate() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(CardOrderInfo.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue("80.56.8532");
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Неверно введена дата"));

    }

    @Test
    public void shouldReturnErrorWhenDateIsEmpty() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(CardOrderInfo.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue("");
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Неверно введена дата"));

    }


    @Test
    public void shouldReturnErrorWhenDateOfMeetingIsToday() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(CardOrderInfo.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(0));
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));

    }

    @Test
    public void shouldReturnErrorWhenDateOfMeetingIsYesterday() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(CardOrderInfo.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(CardOrderInfo.generateDate(-1));
        form.$("[data-test-id=name] input").setValue(CardOrderInfo.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(CardOrderInfo.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));

    }


}
