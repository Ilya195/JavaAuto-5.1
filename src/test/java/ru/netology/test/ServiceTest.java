package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataClass.Registration.getRegisterUser;
import static ru.netology.data.DataClass.Registration.getUser;
import static ru.netology.data.DataClass.getLogin;
import static ru.netology.data.DataClass.getPassword;

public class ServiceTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    @DisplayName("Should successfully register user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registerUser = getRegisterUser("active");
        $("[data-test-id=login] input").setValue(registerUser.getLogin());
        $("[data-test-id=password] input").setValue(registerUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("h2").shouldHave(Condition.text("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should error if login blocked")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisterUser("blocked");
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(Condition.text("Пользователь заблокирован"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should error if login not register")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should error if login wrong")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisterUser("active");
        var wrongLogin = getLogin("en");
        $("[data-test-id=login] input").setValue(wrongLogin);
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should error if wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registerUser = getRegisterUser("active");
        var wrongPassword = getPassword("en");
        $("[data-test-id=login] input").setValue(registerUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongPassword);
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }
}
