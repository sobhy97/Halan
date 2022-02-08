package Locators;

import Locators.HelperMethodsSavedParameters;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import org.testng.Assert;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HelperMethods {

    public static String[] assertion (String response, String[] responseParameters) {
        int responseParametersNumber = responseParameters.length;
        String[] missingParameters = new String[0];
        int notFoundParametersIndex = 0;
        for (int i = 0; i < responseParametersNumber; i++)
        {
            boolean isFound = response.contains(responseParameters[i]);
            if (!isFound) {
                missingParameters = Arrays.copyOf(missingParameters,missingParameters.length+1);
                missingParameters[missingParameters.length-1] = responseParameters[i];
                notFoundParametersIndex = notFoundParametersIndex +1 ;
            }
        }
        return missingParameters;
    }
    public static String generateDate (int amount) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);
        date = calendar.getTime();
        SimpleDateFormat formatter= new SimpleDateFormat("MM/dd/yyyy");
        String newDate = formatter.format(date);

        return newDate;
    }

    public static String generateLaunchDate (int amount) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);
        date = calendar.getTime();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        String newDate = formatter.format(date);
        String newDateTime = newDate.concat("T15:00:00.000Z");

        return newDateTime;
    }

    public static int errorStatusAssertion (String response) {
        org.json.JSONObject obj = new org.json.JSONObject(response);
        int status = obj.getInt("status");
        int failFlag = 0;
        try {
            Assert.assertEquals(status,0);
        }
        catch (AssertionError e) {
            failFlag = 1;
        }
        return failFlag;
    }

    public static void getApiOperations(ExtentTest test , Response apiResponse) {
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

        //Parse actual response to json object
        String jsonResponse = apiResponse.asString();
        JsonObject actualResponse = JsonParser.parseString(jsonResponse).getAsJsonObject();

        //Get response time
        long responseTime = apiResponse.getTime();

        //Evaluate API performance
        if (responseTime<= APIs.highLevelApiResponse) {
            test.log(Status.PASS, APIs.performanceSubTitle + (String.valueOf(responseTime)) + (APIs.goodPerformance));
        }
        else {
            test.log(Status.WARNING, APIs.performanceSubTitle + (String.valueOf(responseTime)) + (APIs.poorPerformance));
        }

        //Assert Error Status
        int failFlag = HelperMethods.errorStatusAssertion(jsonResponse);
        if (failFlag == 0) {
            test.log(Status.PASS, APIs.errorAssertionSucceeded);
        }
        else {
            test.log(Status.FAIL, APIs.errorAssertionFailed);
        }

        //Beautify JSON Response
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String actualResponseJson = gson.toJson(actualResponse);

        //View API response
        test.createNode(APIs.actualResponseTitle).info("<pre>" + APIs.apiResponseSubTitle + "\n" + actualResponseJson+ "</pre>");
    }

    public static String postApiOperations(ExtentTest test , Response apiResponse) {
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


        //Parse actual response to json object
        String jsonResponse = apiResponse.asString();
        JsonObject actualResponse = JsonParser.parseString(jsonResponse).getAsJsonObject();

        //Get response time
        long responseTime = apiResponse.getTime();

        //Evaluate API performance
        if (responseTime<= APIs.highLevelApiResponse) {
            test.log(Status.PASS, APIs.performanceSubTitle + (String.valueOf(responseTime)) + (APIs.goodPerformance));
        }
        else {
            test.log(Status.WARNING, APIs.performanceSubTitle + (String.valueOf(responseTime)) + (APIs.poorPerformance));
        }

        //Assert Error Status
        int failFlag = HelperMethods.errorStatusAssertion(jsonResponse);
        if (failFlag == 0) {
            test.log(Status.PASS, APIs.errorAssertionSucceeded);
        }
        else {
            test.log(Status.FAIL, APIs.errorAssertionFailed);
        }

        //Beautify JSON Response
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String actualResponseJson = gson.toJson(actualResponse);

        //View API response
        test.createNode(APIs.actualResponseTitle).info("<pre>" + APIs.apiResponseSubTitle + "\n" + actualResponseJson+ "</pre>");
        return jsonResponse;
    }

    public static org.json.JSONObject responseInterfaceAssertion(org.json.JSONObject actualResponse , org.json.JSONObject expectedResponse , org.json.JSONObject missingKeys , org.json.JSONObject mismatchTypeKeys) {
        for (final Iterator<String> iterator = expectedResponse.keys(); iterator.hasNext();) {
            final String key = iterator.next();
            if (!actualResponse.has(key)) {
                    missingKeys.put(HelperMethodsSavedParameters.parameterFullPath.concat(key),"Not Found");
            } else {
                String expectedType;
                if (expectedResponse.get(key).equals("Null")) {
                    expectedType = "Null";
                } else {
                    expectedType = expectedResponse.get(key).getClass().getSimpleName();
                }
                String actualType = actualResponse.get(key).getClass().getSimpleName();
                if (!expectedType.equals(actualType)) {
                    mismatchTypeKeys.put(HelperMethodsSavedParameters.parameterFullPath.concat(key),"Expected [".concat(expectedType).concat("] but found [").concat(actualType).concat("]"));
                }
                Object obj = expectedResponse.get(key);
                if (obj instanceof org.json.JSONObject) {
                    HelperMethodsSavedParameters.parameterFullPath = HelperMethodsSavedParameters.parameterFullPath.concat(key).concat("_");
                    responseInterfaceAssertion(actualResponse.getJSONObject(key),expectedResponse.getJSONObject(key),missingKeys,mismatchTypeKeys);
                    HelperMethodsSavedParameters.parameterFullPath = HelperMethodsSavedParameters.parameterFullPath.replace(key.concat("_"),"");
                } else if (obj instanceof org.json.JSONArray && expectedResponse.getJSONArray(key).length()!=0) {
                    HelperMethodsSavedParameters.parameterFullPath = HelperMethodsSavedParameters.parameterFullPath.concat(key).concat("_");
                    responseInterfaceAssertion(actualResponse.getJSONArray(key).getJSONObject(0),expectedResponse.getJSONArray(key).getJSONObject(0),missingKeys,mismatchTypeKeys);
                    HelperMethodsSavedParameters.parameterFullPath = HelperMethodsSavedParameters.parameterFullPath.replace(key.concat("_"),"");
                }
            }
        }
        return missingKeys;
    }

    public static void printResponseAssertionResults(ExtentTest test , org.json.JSONObject interfaceAssertionResult , org.json.JSONObject mismatchTypeKeys) throws IOException {
        if (interfaceAssertionResult.length()==0 && mismatchTypeKeys.length()==0) {
            test.log(Status.PASS,APIs.interfaceAssertionSucceeded);
        } else {
            test.log(Status.FAIL,APIs.interfaceAssertionFailed);

            if (interfaceAssertionResult.length()!=0) {
                //Order Missing Parameters Alphabetically
                String missingKeysAsString = interfaceAssertionResult.toString();
                ObjectMapper om = new ObjectMapper();
                om.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
                Map<String, Object> map = om.readValue(missingKeysAsString, HashMap.class);
                String missingKeysOrdered = om.writeValueAsString(map);

                //Beautify Missing Keys JSON
                JsonObject missingKeysAsJson = JsonParser.parseString(missingKeysOrdered).getAsJsonObject();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String missingKeysBeautified = gson.toJson(missingKeysAsJson);

                //Print Missing Keys From Interface
                test.createNode(APIs.missingParametersTitle).info("<pre>" + missingKeysBeautified + "</pre>");
            }

            if (mismatchTypeKeys.length()!=0){
                //Order Mismatch Type Keys Alphabetically
                String mismatchTypeKeysAsString = mismatchTypeKeys.toString();
                ObjectMapper om = new ObjectMapper();
                om.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
                Map<String, Object> map = om.readValue(mismatchTypeKeysAsString, HashMap.class);
                String mismatchTypeKeysOrdered = om.writeValueAsString(map);

                //Beautify Mismatch Type Keys JSON
                JsonObject mismatchTypeKeysAsJson = JsonParser.parseString(mismatchTypeKeysOrdered).getAsJsonObject();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String mismatchTypeKeysBeautified = gson.toJson(mismatchTypeKeysAsJson);
                //Print Missing Keys From Interface
                test.createNode(APIs.mismatchTypeKeysTitle).info("<pre>" + mismatchTypeKeysBeautified + "</pre>");
            }
        }
    }
}
