package org.ruthvik.assertions;

import java.io.File;
import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.SoftAssertions;
import org.ruthvik.enums.ErrorType;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class ResponseAssert extends AbstractAssert<ResponseAssert, Response> {
	
	private final SoftAssertions softAssertions;	
    private ResponseAssert(Response response, Class<?> selfType) {
        super(response, selfType);
        this.softAssertions = new SoftAssertions();
    }

    public static ResponseAssert assertThat(Response response){
        return new ResponseAssert(response,ResponseAssert.class);
    }

    public ResponseAssert isGetRequestSuccessful(){
        softAssertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not 200")
                .isEqualTo(200);
        return this;
    }
    public ResponseAssert isPostRequestSuccessful(){
        softAssertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not 201")
                .isEqualTo(201);
        return this;
    }
    public ResponseAssert isPutRequestSuccessful(){
        softAssertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not 200")
                .isEqualTo(200);
       return this;
    }
    public ResponseAssert isDeleteRequestSuccessful(){
        softAssertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not 200")
                .isEqualTo(200);
        return this;
    }

    public ResponseAssert isBadRequestAndHasMessage(String message){
        String actualValue = actual.body().jsonPath().getString("message");
        softAssertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not "+ 400)
                .isEqualTo(400);
        softAssertions.assertThat(actualValue).contains(message);
        return this;
    }
    
    public ResponseAssert isBadRequestAndHasMessageInList(String message){
        List<String> actualValue = actual.body().jsonPath().getList("message");
        softAssertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not "+ 400)
                .isEqualTo(400);
        softAssertions.assertThat(actualValue).contains(message);
        return this;
    }
    
    public ResponseAssert hasErrorType(ErrorType error){
        String actualValue = actual.body().jsonPath().getString("name");
        softAssertions.assertThat(actualValue).contains(error.getName());
        return this;
    }
    
    public ResponseAssert isUnauthorized() {
    	String actualValue = actual.body().jsonPath().getString("message");
        softAssertions.assertThat(actual.getStatusCode())
                .withFailMessage(()->"Status code is not "+ 401)
                .isEqualTo(401);
        softAssertions.assertThat(actualValue).isEqualTo("Unauthorized");
        return this;
    }
    
    
    public ResponseAssert validateResponseSchema(String schemaFileName){
        File file = new File(System.getProperty("user.dir")+"/src/test/resources/schemas/"+schemaFileName+".json");
        softAssertions.assertThat(actual.then().body(JsonSchemaValidator.matchesJsonSchema(file)))
                .withFailMessage("Schema is not matching please check ["+schemaFileName+"] file")
                .getWritableAssertionInfo();
        return this;
    }
    
    public void assertAll() {
    	softAssertions.assertAll();
    }
}
