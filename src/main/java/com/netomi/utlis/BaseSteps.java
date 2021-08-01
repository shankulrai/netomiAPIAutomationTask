package com.netomi.utlis;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;

import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

public class BaseSteps
{
    public static Logger logger;
    public static Properties config;
    public static FileInputStream fis;


    public BaseSteps()
    {
        logger=Logger.getLogger(BaseSteps.class.getName());
        try {
            fis=new FileInputStream(Constants.configFilePath);
            config=new Properties();
            config.load(fis);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();

        }
    }

    public String  createJspnFromPojo(Object obj)
    {
        logger.info("Creating JSON FROM Pojo");
        String jsonString=null;

        ObjectMapper mapper= new ObjectMapper();
        try {
            jsonString= mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return jsonString;

    }
    public String getBaseURL()
    {
        logger.info("Fetching Base URL");
        String baseURL=config.getProperty("baseUrl");

        return baseURL;
    }

    public Response callGetAPIWithQueryParams(Map<String,String> queryParams)
    {
        logger.info("Making Get Request For the URL");
        RestAssured.baseURI =getBaseURL();
        RequestSpecification request = RestAssured.given();
        Response response=request.queryParams(queryParams).get();
        return response;
    }

}
