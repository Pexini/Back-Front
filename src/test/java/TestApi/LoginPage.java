package TestApi;

import Data.DataHelper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static Data.SQLHelper.cleanAuthCodes;
import static Data.SQLHelper.cleanDataBase;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class LoginPage {


    @BeforeAll
    public static void setup() {

        given()
                .contentType(ContentType.JSON)
                .body("{\"login\":\"vasya\",\"password\":\"qwerty123\"}")
                .when()
                .post("http://localhost:9999/api/auth")
                .then()
                .statusCode(200);

    }

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @AfterAll
    static void tearDownAll() {
        cleanDataBase();
    }

    @Test
    void shouldGetVerificationPage() {

        String generatedCode = DataHelper.generateRandomVerificationCode().getCode();

        System.out.println("Generated Code: " + generatedCode);

        Response response = given()
                .baseUri("http://localhost:9999")
                .basePath("/api/verification")
                .body("{\"login\":\"vasya\",\"code\":\"" + generatedCode + "\"}")
                // Выполняемые действия
                .when()
                .post()
                // Проверки
                .then()
                .statusCode(200)
                .extract().response();

    }
}

