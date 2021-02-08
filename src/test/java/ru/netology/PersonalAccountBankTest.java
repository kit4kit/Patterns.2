package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.UserGenerator.*;


public class PersonalAccountBankTest {
    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitActiveUser() {
        Registration validValidActiveUser = getValidActiveUser();
        $("[name=login]").setValue(validValidActiveUser.getLogin());
        $("[name=password]").setValue(validValidActiveUser.getPassword());
        $(".button__text").click();
        $(byText("Личный кабинет")).shouldBe(exist);
    }


    @Test
    void shouldNotSubmitBlockedUser() {
        Registration validValidAcBlockedUser = getValidBlockedUser();
        $("[name=login]").setValue(validValidAcBlockedUser.getLogin());
        $("[name=password]").setValue(validValidAcBlockedUser.getPassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible, Duration.ofMillis(5000));
        $(".notification_visible[data-test-id=error-notification]").shouldHave(matchText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldNotSubmitWithIncorrectPassword() {
        Registration userWithIncorrectPassword = getUserWithIncorrectPassword();
        $("[name=login]").setValue(userWithIncorrectPassword.getLogin());
        $("[name=password]").setValue(userWithIncorrectPassword.getPassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible, Duration.ofMillis(5000));
        $(".notification_visible[data-test-id=error-notification]").shouldHave(matchText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotSubmitWithIncorrectLogin() {
        Registration userWithIncorrectLogin = getUserWithIncorrectLogin();
        $("[name=login]").setValue(userWithIncorrectLogin.getLogin());
        $("[name=password]").setValue(userWithIncorrectLogin.getPassword());
        $(".button__text").click();
        $(".notification_status_error").shouldBe(visible, Duration.ofMillis(5000));
        $(".notification_visible[data-test-id=error-notification]").shouldHave(matchText("Ошибка! Неверно указан логин или пароль"));
    }
}
