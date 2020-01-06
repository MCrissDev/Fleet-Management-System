package com.surodev.fms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.surodev.Application;
import com.surodev.fms.domain.Category;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class CategoryControllerTest {

	@LocalServerPort
	private int mPort;

	@Autowired
	private TestRestTemplate mRestTemplate;

	@Test
	@Order(1)
	public void integrationTestAddCategories() {

		Category category = new Category();
		category.setShipType("Cruise");

		ResponseEntity<Category> responseEntity = mRestTemplate
				.postForEntity("http://localhost:" + mPort + "/api/categories/add", category, Category.class);
		assertEquals(201, responseEntity.getStatusCodeValue());

		category.setShipTonnage(123456);
		category.setShipType("Tanker");

		responseEntity = mRestTemplate.postForEntity("http://localhost:" + mPort + "/api/categories/add", category,
				Category.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
		assertEquals(123456, responseEntity.getBody().getShipTonnage());
		assertEquals("Tanker", responseEntity.getBody().getShipType());
	}

	@Test
	@Order(2)
	public void integrationTestGetCategories() {
		ResponseEntity<Category[]> responseList = mRestTemplate
				.getForEntity("http://localhost:" + mPort + "/api/categories", Category[].class);
		Category[] categories = responseList.getBody();
		assertTrue(categories != null);
		assertTrue(categories.length > 0);
	}

	@Test
	@Order(3)
	public void integrationTestDeleteCategories() {
		ResponseEntity<Category[]> responseList = mRestTemplate
				.getForEntity("http://localhost:" + mPort + "/api/categories", Category[].class);
		Category[] categories = responseList.getBody();
		assertTrue(categories != null);
		assertTrue(categories.length > 0);

		for (Category category : categories) {
			mRestTemplate.delete("http://localhost:" + mPort + "/api/categories/remove/{id}", category.getId());
		}

		responseList = mRestTemplate.getForEntity("http://localhost:" + mPort + "/api/categories", Category[].class);
		categories = responseList.getBody();
		assertTrue(categories != null);
		assertTrue(categories.length == 0);
	}
}
