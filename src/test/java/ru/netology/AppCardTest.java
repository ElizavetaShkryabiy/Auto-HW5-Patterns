package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AppCardTest {


    @Test
    public void shouldTestHappyPathWith1Meeting() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(5));
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + DataClass.generateDate(5)));
    }

    @Test
    public void shouldTestHappyPathReplaning() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(5));
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));

        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(12));
        form.$(".button").find(byText("Запланировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));
        $("[data-test-id=replan-notification]").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$(".button").find(exactText("Перепланировать")).click();
        $(".notification_visible").shouldBe(appear, Duration.ofSeconds(16));
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + DataClass.generateDate(12)));
    }


    @Test
    public void shouldReturnErrorWhenCityNotInList() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Мытищи");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(5));
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldReturnErrorWhenCityNotInKirilitsa() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Moscow");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(5));
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldReturnErrorWhenCityIsEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(5));
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=city] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldReturnErrorWhenNameNotInKirilitsa() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(5));
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("eng"));
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=name] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void shouldReturnErrorWhenNameWithSymbols() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(5));
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru") + "@");
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=name] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

//    @Test
//    public void shouldReturnErrorWhenWrongFormatOfTelWith10Numbers() {
//        open("http://localhost:9999");
//        SelenideElement form = $(".form");
//        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
//        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
//        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(5));
//        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
//        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ukr"));
//        form.$("[data-test-id=agreement]").click();
//        form.$(".button").find(byText("Запланировать")).click();
//        $(".input_invalid[data-test-id=phone] .input__sub").shouldBe(appear)
//                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
//
//    }
//
//    @Test
//    public void shouldNotAddLastNumberWhenPhoneIsIncorrectWith12Symbols() {
//        open("http://localhost:9999");
//        SelenideElement form = $(".form");
//        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
//        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
//        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(5));
//        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
//        form.$("[data-test-id=phone] input").setValue("+927110012233");
//        form.$("[data-test-id=phone] input").shouldHave(Condition.exactText("+92711001223"));
//
//    }

    @Test
    public void shouldReturnErrorWhenTelIsEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(5));
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=phone] .input__sub").shouldBe(appear)
                .shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldReturnErrorWhenAgreementEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(5));
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]");
        form.$(".button").find(byText("Запланировать")).click();
        $(".input_invalid[data-test-id=agreement]").shouldBe(appear);
    }

    @Test
    public void shouldReturnErrorWhenWrongFormatOfDate() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue("80.56.8532");
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Неверно введена дата"));

    }

    @Test
    public void shouldReturnErrorWhenDateIsEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue("");
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Неверно введена дата"));

    }


    @Test
    public void shouldReturnErrorWhenDateOfMeetingIsToday() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(0));
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));

    }

    @Test
    public void shouldReturnErrorWhenDateOfMeetingIsYesterday() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(DataClass.generateCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataClass.generateDate(-1));
        form.$("[data-test-id=name] input").setValue(DataClass.generateName("ru"));
        form.$("[data-test-id=phone] input").setValue(DataClass.generatePhone("ru"));
        form.$("[data-test-id=agreement]").click();
        form.$(".button").find(byText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(appear)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));

    }


}
