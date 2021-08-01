package com.netomi.tests;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.netomi.utlis.BaseSteps;
import com.netomi.utlis.Constants;
import com.netomi.utlis.Log;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import static com.netomi.utlis.ExtentTestManager.startTest;
import java.lang.reflect.Method;
import org.testng.annotations.Test;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class FiveDaysWeatherAPITest extends BaseSteps
{
    @BeforeSuite
    public void setupForFramework()
    {
        logger.info("Use Framework Setup Here");

        //Use Framework Setup Here

    }
    @DataProvider (name = "data-provider-valid")
    public Object[][] dataProvidersForAPI(){
        return new Object[][] {{"London,uk"}, {"221003"},{"Delhi"}};
    }

    @DataProvider (name = "data-provider-invalid")
    public Object[][] dataProvidersForAPIInvalid(){
        return new Object[][] {{"ABC,XYZ"}, {"89609871"},{"XYZ"}};
    }



    @Test(dataProvider = "data-provider-valid")
    public void fiveDayWeatherForecastApiTestWithCorrectData(String citesValue,Method method)
    {
        startTest(method.getName(),"Testing Weather API with the Valid data");
        Log.info("Creating Get Request for FiveDay wether API");
        logger.info("Creating Get Request for FiveDay wether API");
        Map<String,String> requestQueryParams=new HashMap<String,String>();
        requestQueryParams.put("q",citesValue);
        requestQueryParams.put("APPID", Constants.appId);
        Response response=callGetAPIWithQueryParams(requestQueryParams);
        Log.info(response.asPrettyString());
        logger.info(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(),200);
        SoftAssert softAssert=new SoftAssert();
        // Need Proper Json Schema to validate below Line online genearated schema throwing error
        //response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchema//weatherAPISchema.json"));
        Assert.assertNotNull(response.getBody().jsonPath().get("city.id"));
        Assert.assertNotNull(response.getBody().jsonPath().get("city.name"));
        Assert.assertNotNull(response.getBody().jsonPath().get("city.coord.lat"));
        Assert.assertNotNull(response.getBody().jsonPath().get("city.coord.lon"));
        Assert.assertNotNull(response.getBody().jsonPath().get("city.country"));
        Assert.assertNotNull(response.getBody().jsonPath().get("city.population"));
        Assert.assertNotNull(response.getBody().jsonPath().get("city.timezone"));
        Assert.assertNotNull(response.getBody().jsonPath().get("city.sunrise"));
        Assert.assertNotNull(response.getBody().jsonPath().get("city.sunset"));


    }
    @Test(dataProvider = "data-provider-invalid")
    public void fiveDayWeatherForecastApiTestWithInvalidData(String cityValue, Method method)
    {
        startTest(method.getName(),"Testing Weather API with the InValid data");
        Log.info("Creating Get Request for FiveDay wether API");
        logger.info("Creating Get Request for FiveDay wether API");
        Map<String,String> requestQueryParams=new HashMap<String,String>();
        requestQueryParams.put("q",cityValue);
        requestQueryParams.put("APPID", Constants.appId);
        Response response=callGetAPIWithQueryParams(requestQueryParams);
        Log.info(response.asPrettyString());
        logger.info(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(),404);
        Assert.assertEquals(response.getBody().jsonPath().get("cod"), String.valueOf(404));
        Assert.assertTrue(response.getBody().jsonPath().get("message").toString().equalsIgnoreCase("city not found"));




    }

    @AfterSuite
    public void closeSetup()
    {
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }

    }
}
