package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	private Faker faker;
	private User userPayload;
	private Logger logger;
	
	@BeforeClass
	public void setup() {
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	public void testPostUser() {
		logger.info("****** Creating user **********");
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();	
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("****** User is created **********");
	}
	
	@Test(priority = 2)
	public void testGetUserByName() {
		logger.info("****** Reading user **********");
		Response response = UserEndPoints.readUser(userPayload.getUsername());
		response.then().log().all();	
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("****** User Retrieved **********");
	}
	
	@Test(priority = 3)
	public void testUpdateUserByName() {
		
		logger.info("****** Updating user **********");
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints.updateUser(userPayload.getUsername(), userPayload);
		response.then().log().all();	
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("****** User updated **********");
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName() {
		
		logger.info("****** Deleting user **********");
		
		Response response = UserEndPoints.deleteUser(userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("****** User deleted **********");
	}
}
