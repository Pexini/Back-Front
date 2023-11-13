package TestApi;

import Data.DataHelper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;


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

    @Test
    void shouldGetVerificationPage() {
        // Given - When - Then
        // Предусловия
        String generatedCode = DataHelper.generateRandomVerificationCode().getCode();

        System.out.println("Generated Code: " + generatedCode);

        Response response = given()
                .baseUri("http://localhost:9999")
                .basePath("/api/verification")
                .body("{\"login\":\"vasya\",\"code\":\""+generatedCode+"\"}")
                // Выполняемые действия
                .when()
                .post()
                // Проверки
                .then()
                .statusCode(200)
                .extract().response();

    }
}

