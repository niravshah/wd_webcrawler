package com.nns.wd;

import com.jayway.restassured.RestAssured;
import com.nns.wd.api.HomeResource;
import com.nns.wd.api.RequestResource;
import com.nns.wd.api.ResultResource;
import com.nns.wd.services.RequestService;
import com.nns.wd.services.redis.RedisRequestService;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CrawlerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("MockService")
public class CrawlerApplicationTests {

	@Value("${local.server.port}")
	int port;

	@Autowired
	private RequestService service;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}


	@Test
	public void homeTest() {
		String testUid = UUID.randomUUID().toString();
		Mockito.when(service.startNewRequest("http://wiprodigital.com", 64)).thenReturn(testUid);
		HashMap linkMap =
				when().
						get("/").
						then().
						contentType(JSON).
						statusCode(HttpStatus.SC_OK).
						body("message", is(HomeResource.MESSAGE)).
						body("details", is(HomeResource.DETAIL_MESSAGE)).
						extract().
						path("_links");

		assertThat(linkMap.size(), equalTo(2));

	}

	@Test
	public void createNewRequestTest() {
		String testUid = UUID.randomUUID().toString();
		Mockito.when(service.startNewRequest("http://wiprodigital.com", 64)).thenReturn(testUid);
		HashMap linkMap =
				given().
						param("url", "http://wiprodigital.com").
						param("md", 64).
						when().
						get("/request").
						then().
						contentType(JSON).
						statusCode(HttpStatus.SC_OK).
						body("message", is(RequestResource.MESSAGE)).
						body("details", is(String.format(RequestResource.DETAIL_MESSAGE, testUid))).
						extract().
						path("_links");

		assertThat(linkMap.size(), equalTo(1));

	}

	@Test
	public void getRequestResultTest() {
		String testUid = UUID.randomUUID().toString();
		Mockito.when(service.getRequestStatus(testUid)).thenReturn(RedisRequestService.STATUS_PROCESSING);
		Mockito.when(service.getResult(testUid)).thenReturn("{}");
		given().
				param("id", testUid).
				when().
				get("/result").
				then().
				contentType(JSON).
				statusCode(HttpStatus.SC_OK).
				body("message", is(RedisRequestService.STATUS_PROCESSING)).
				body("details", is(ResultResource.SITEMAP_MESSAGE)).
				body("sitemap", is(not(empty())));

	}

}
