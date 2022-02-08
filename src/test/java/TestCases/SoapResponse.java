package TestCases;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SoapResponse {

    @Test
    public void test_Soap_Response1() {
        String baseUrl = "";

        given().when().
                get(baseUrl).
                then().
                assertThat().body("GetStockPriceResponse.Price",equalTo("34.5"));
    }


    @Test
    public void test_Soap_Response2() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File(".\\Soap\\SoapFile.xml"));
        RestAssured.baseURI="";

        Response response=given()
                .header("Content-Type", "text/xml")
                .and()
                .body(IOUtils.toString(fileInputStream,"UTF-8"))
                .when()
                .get(baseURI)
                .then()
                .statusCode(200)
                .and()
                .log().all()
                .extract().response();

        XmlPath jsXpath= new XmlPath(response.asString());//Converting string into xml path to assert
        String price=jsXpath.getString("GetStockPriceResponse.Price");
        System.out.println("price is: " +  price);
        Assert.assertEquals(price,"34.5");

    }
}
