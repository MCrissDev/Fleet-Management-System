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
import com.surodev.fms.domain.Owner;
import com.surodev.fms.domain.Ship;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class ShipControllerTest {

	@LocalServerPort
	private int mPort;

	@Autowired
	private TestRestTemplate mRestTemplate;

	@Test
	@Order(1)
	public void integrationTestAddShips() {

		Ship ship = new Ship();
		ship.setShipName("Jupiter");

		ResponseEntity<Ship> responseEntity = mRestTemplate
				.postForEntity("http://localhost:" + mPort + "/api/ships/add", ship, Ship.class);
		assertEquals(201, responseEntity.getStatusCodeValue());

		ship.setShipName("Venus");

		responseEntity = mRestTemplate.postForEntity("http://localhost:" + mPort + "/api/ships/add", ship, Ship.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
		assertEquals("Venus", responseEntity.getBody().getShipName());
		assertTrue(responseEntity.getBody().getId() > 0);
	}

	@Test
	@Order(2)
	public void integrationTestGetShips() {
		ResponseEntity<Ship[]> responseList = mRestTemplate.getForEntity("http://localhost:" + mPort + "/api/ships",
				Ship[].class);
		Ship[] ships = responseList.getBody();
		assertTrue(ships != null);
		assertTrue(ships.length > 0);
	}

	@Test
	@Order(3)
	public void integrationTestAddGetInfoShip() {

		Ship ship = new Ship();
		ship.setShipName("Neptun");

		ResponseEntity<Ship> responseEntity = mRestTemplate
				.postForEntity("http://localhost:" + mPort + "/api/ships/add", ship, Ship.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
		ship = responseEntity.getBody();

		responseEntity = mRestTemplate.getForEntity(
				"http://localhost:" + mPort + "/api/ships/info/" + responseEntity.getBody().getId(), Ship.class);

		assertEquals(ship.getId(), responseEntity.getBody().getId());
	}

	@Test
	@Order(4)
	public void integrationTestSetShipCategory() {

		Ship ship = new Ship();
		ship.setShipName("Pluto");

		ResponseEntity<Ship> responseEntity = mRestTemplate
				.postForEntity("http://localhost:" + mPort + "/api/ships/add", ship, Ship.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
		ship = responseEntity.getBody();

		Category category = new Category();
		category.setShipType("Cruise");
		category.setShipTonnage(1991);

		ResponseEntity<Category> responseCategoryEntity = mRestTemplate
				.postForEntity("http://localhost:" + mPort + "/api/categories/add", category, Category.class);

		assertEquals(201, responseCategoryEntity.getStatusCodeValue());
		category = responseCategoryEntity.getBody();

		ResponseEntity<Void> responseUpdateCategory = mRestTemplate.postForEntity(
				"http://localhost:" + mPort + "/api/ships/" + ship.getId() + "/category/" + category.getId(), null,
				Void.class);

		assertEquals(200, responseUpdateCategory.getStatusCodeValue());

		responseEntity = mRestTemplate.getForEntity(
				"http://localhost:" + mPort + "/api/ships/info/" + responseEntity.getBody().getId(), Ship.class);
		assertEquals(ship.getId(), responseEntity.getBody().getId());
		ship = responseEntity.getBody();

		assertEquals(ship.getCategory().getShipType(), category.getShipType());
	}

	@Test
	@Order(5)
	public void integrationTestSetShipOwners() {

		Ship ship = new Ship();
		ship.setShipName("Alpha");

		ResponseEntity<Ship> responseEntity = mRestTemplate
				.postForEntity("http://localhost:" + mPort + "/api/ships/add", ship, Ship.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
		ship = responseEntity.getBody();

		Owner owner = new Owner();
		owner.setName("Johnny Bravo");

		ResponseEntity<Owner> responseOwnerEntity = mRestTemplate
				.postForEntity("http://localhost:" + mPort + "/api/owners/add", owner, Owner.class);
		assertEquals(201, responseOwnerEntity.getStatusCodeValue());
		owner = responseOwnerEntity.getBody();

		ResponseEntity<Void> responseUpdateCategory = mRestTemplate.postForEntity(
				"http://localhost:" + mPort + "/api/ships/" + ship.getId() + "/owner/" + owner.getId(), null,
				Void.class);

		assertEquals(200, responseUpdateCategory.getStatusCodeValue());

		responseEntity = mRestTemplate.getForEntity(
				"http://localhost:" + mPort + "/api/ships/info/" + responseEntity.getBody().getId(), Ship.class);

		assertEquals(ship.getId(), responseEntity.getBody().getId());
	}

	@Test
	@Order(6)
	public void integrationTestDeleteShips() {
		ResponseEntity<Ship[]> responseList = mRestTemplate.getForEntity("http://localhost:" + mPort + "/api/ships",
				Ship[].class);
		Ship[] ships = responseList.getBody();
		assertTrue(ships != null);
		assertTrue(ships.length > 0);

		for (Ship ship : ships) {
			mRestTemplate.delete("http://localhost:" + mPort + "/api/ships/remove/" + ship.getId());
		}
		responseList = mRestTemplate.getForEntity("http://localhost:" + mPort + "/api/ships", Ship[].class);
		ships = responseList.getBody();
		assertTrue(ships != null);
		assertTrue(ships.length == 0);
	}
}
