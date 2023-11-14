package com.rodrigues.restapi.integrationtests.swagger;

import com.rodrigues.restapi.configs.TestConfig;
import com.rodrigues.restapi.integrationtests.testcontainers.AbstractIntegrationTest;
import static io.restassured.RestAssured.given;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void shouldDisplaySwaggerUiPage() {
        var result = given()
                .when()
                .get("/swagger-ui.html")
                .then()
                .statusCode(200)
                .extract()
                .asString();


        Assertions.assertTrue(result.contains("Swagger UI"));
    }
}
