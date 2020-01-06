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
import com.surodev.fms.domain.Owner;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class OwnerControllerTest {

	@LocalServerPort
	private int mPort;

	@Autowired
	private TestRestTemplate mRestTemplate;

	@Test
	@Order(1)
	public void integrationTestAddOwners() {

		Owner owner = new Owner();
		owner.setName("Johnny Bravo");

		ResponseEntity<Owner> responseEntity = mRestTemplate
				.postForEntity("http://localhost:" + mPort + "/api/owners/add", owner, Owner.class);
		assertEquals(201, responseEntity.getStatusCodeValue());

		owner.setName("Doctor Doom");

		responseEntity = mRestTemplate.postForEntity("http://localhost:" + mPort + "/api/owners/add", owner,
				Owner.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
		assertEquals("Doctor Doom", responseEntity.getBody().getName());
		assertTrue(responseEntity.getBody().getId() > 0);
	}

	@Test
	@Order(2)
	public void integrationTestGetOwners() {
		ResponseEntity<Owner[]> responseList = mRestTemplate.getForEntity("http://localhost:" + mPort + "/api/owners",
				Owner[].class);
		Owner[] owners = responseList.getBody();
		assertTrue(owners != null);
		assertTrue(owners.length > 0);
	}

	@Test
	@Order(3)
	public void integrationTestDeleteOwners() {
		ResponseEntity<Owner[]> responseList = mRestTemplate.getForEntity("http://localhost:" + mPort + "/api/owners",
				Owner[].class);
		Owner[] owners = responseList.getBody();
		assertTrue(owners != null);
		assertTrue(owners.length > 0);

		for (Owner owner : owners) {
			mRestTemplate.delete("http://localhost:" + mPort + "/api/owners/remove/{id}", owner.getId());
		}
		responseList = mRestTemplate.getForEntity("http://localhost:" + mPort + "/api/owners", Owner[].class);
		owners = responseList.getBody();
		assertTrue(owners != null);
		assertTrue(owners.length == 0);
	}
}
