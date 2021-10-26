package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class CardOrderInfo {

    private CardOrderInfo() {
    }

    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        return date;
    }

    public static String generateCity() {
        Random city = new Random();
        String[] allowedCity = {"Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас",
                "Нальчик", "Элиста", "Черкесск", "Петрозаводск", "Сыктывкар", "Симферополь", "Йошкар-Ола",
                "Саранск", "Якутск", "Владикавказ", "Казань", "Кызыл", "Ижевск", "Абакан", "Грозный", "Чебоксары",
                "Барнаул", "Чита", "Петропавловск-Камчатский", "Краснодар", "Красноярск", "Красноярск", "Пермь",
                "Ставрополь", "Хабаровск", "Благовещенск", "Архангельск", "Астрахань", "Белгород", "Брянск", "Владимир",
                "Волгоград", "Вологда", "Воронеж", "Иваново", "Иркутск", "Калининград", "Калуга", "Кемерово", "Киров",
                "Кострома", "Курган", "Курск", "Липецк", "Магадан", "Мурманск", "Нижний Новгород", "Великий Новгород",
                "Новосибирск", "Омск", "Оренбург", "Орёл", "Пенза", "Псков", "Ростов-на-Дону", "Рязань", "Самара",
                "Саратов", "Южно-Сахалинск", "Екатеринбург", "Смоленск", "Тамбов", "Тверь", "Томск", "Тула",
                "Тюмень", "Ульяновск", "Челябинск", "Ярославль", "Москва", "Санкт-Петербург", "Севастополь",
                "Биробиджан", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь", "Салехард"};

        return allowedCity[city.nextInt(allowedCity.length)];
    }

    public static String generateName(String locale) {
        final Faker faker = new Faker(Locale.forLanguageTag(locale));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        final Faker faker = new Faker(Locale.forLanguageTag(locale));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

}
