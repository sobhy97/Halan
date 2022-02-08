package TestCases;

import Locators.APIs;
import Locators.HelperMethods;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class RestApi {

    public void getProfileData(ExtentTest test)
    {

        //API Call
        String getProfile = APIs.getProfileData;
        io.restassured.response.Response apiResponse = RestAssured.get(getProfile);
        given().get(getProfile).then().statusCode(200);

        //Parse actual response to json object
        String jsonResponse = apiResponse.asString();
        JsonObject actualResponse = JsonParser.parseString(jsonResponse).getAsJsonObject();

        //Get API response
        int statusCode = apiResponse.getStatusCode();

        //Assert API response
        try {
            Assert.assertEquals(statusCode,200);
            test.log(Status.PASS, APIs.statusCodeSuccess);
        }
        catch (AssertionError e) {
            test.log(Status.FAIL, APIs.statusCodeFailed.concat(String.valueOf(statusCode)));
        }

        //Get response time
        long responseTime = apiResponse.getTime();

        //Evaluate API performance
        if (responseTime<= APIs.highLevelApiResponse) {
            test.log(Status.PASS, APIs.performanceSubTitle + (String.valueOf(responseTime)) + (APIs.goodPerformance));
        }
        else {
            test.log(Status.WARNING, APIs.performanceSubTitle + (String.valueOf(responseTime)) + (APIs.poorPerformance));
        }
        //Assert API Response
        int failFlag = 0;
        String[] missingParameters = HelperMethods.assertion( jsonResponse , profileResponse);
        if (missingParameters.length==0) {
            test.log(Status.PASS, APIs.responseAssertionSucceeded);
        }
        else {
            failFlag = 1;
            test.log(Status.FAIL, APIs.responseAssertionFailed);
        }

        //Beautify JSON Response
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String actualResponseJson = gson.toJson(actualResponse);
        String expectedParameters = gson.toJson(missingParameters);

        //View API response
        test.createNode(APIs.actualResponseTitle).info("<pre>" + APIs.apiResponseSubTitle + "\n" + actualResponseJson+ "</pre>");
        if (failFlag == 1) {
            test.createNode(APIs.errorDescriptionTitle).info("<pre>" + expectedParameters + "</pre>");
        }
    }

    public static String[] profileResponse =
            {
                    "name",
                    "age",
                    "count"
            };


}
