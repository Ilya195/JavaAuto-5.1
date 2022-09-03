package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataClass {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void requestSet(RegistrationInfo user) {
                 given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

   public static String getLogin(String locale) {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        return  login;
   }

   public static String getPassword(String locale) {
       Faker faker = new Faker(new Locale(locale));
       String password = faker.code().imei();
       return password;
   }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationInfo getRegisterUser(String status) {
            RegistrationInfo registerUser = getUser(status);
            requestSet(registerUser);
            return registerUser;
        }

        public static RegistrationInfo getUser(String status) {
            RegistrationInfo user = new RegistrationInfo(getLogin(status),getPassword(status),status);
            return user;
        }
    }

    @Value
    public static class RegistrationInfo {
        String login;
        String password;
        String status;
    }
}