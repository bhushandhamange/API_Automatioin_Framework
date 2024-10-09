package api.endpoints;

import api.payload.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserEndPoints {

	public static Response createUser(User payload) {
		RestAssured.baseURI = Routes.base_url;

		RequestSpecification request = RestAssured.given();
		request.accept(ContentType.JSON);
		request.contentType(ContentType.JSON);
		request.body(payload);
		request.log().all();

		Response response = request.post(Routes.user_post_endpoint);
		return response;
	}

	public static Response readUser(String username) {
		RestAssured.baseURI = Routes.base_url;
		
		RequestSpecification request = RestAssured.given();
		request.pathParam("username", username);

		Response response = request.get(Routes.user_get_endpoint);
		return response;
	}
	
	public static Response updateUser(String username, User payload) {
		RestAssured.baseURI = Routes.base_url;

		RequestSpecification request = RestAssured.given();
		request.accept(ContentType.JSON);
		request.contentType(ContentType.JSON);
		request.pathParam("username", username);
		request.body(payload);

		Response response = request.put(Routes.user_update_endpoint);
		return response;
	}
	
	public static Response deleteUser(String username) {
		RestAssured.baseURI = Routes.base_url;

		RequestSpecification request = RestAssured.given();
		request.pathParam("username", username);

		Response response = request.delete(Routes.user_delete_endpoint);
		return response;
	}
}
