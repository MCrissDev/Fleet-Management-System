package com.surodev.fms.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surodev.fms.common.Log;
import com.surodev.fms.common.Utils;
import com.surodev.fms.domain.Category;
import com.surodev.fms.exceptions.BadResourceException;
import com.surodev.fms.exceptions.ResAlreadyExistsException;
import com.surodev.fms.exceptions.ResourceNotFoundException;
import com.surodev.fms.service.CategoryService;

import static com.surodev.fms.common.Constants.API;

/**
 * Category REST controller Handles operations like add category, get all
 * categories, delete category
 *
 */
@RestController
@RequestMapping(API)
public class CategoryController {
	private static final String TAG = Utils.makeTag(CategoryController.class);

	@Autowired
	private CategoryService mCategoryService;

	/**
	 * Retrieve all categories saved in the database
	 * 
	 * @return JSON array containing all the saved categories
	 */
	@GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Category>> getAllCategories() {
		return ResponseEntity.ok(mCategoryService.getAllObjects());
	}

	/**
	 * Add category in the database
	 * 
	 * @param category valid Category instance
	 * @return serialized Category object if all ok
	 * @throws URISyntaxException
	 */
	@PostMapping(value = "/categories/add")
	public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category) throws URISyntaxException {
		try {
			Category newCategory = mCategoryService.createObject(category);
			return ResponseEntity.created(new URI("/api/categories/" + newCategory.getId())).body(newCategory);
		} catch (ResAlreadyExistsException ex) {
			Log.e(TAG, "addCategory: failed to save category. Ex = " + ex.toString());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			Log.e(TAG, "addCategory: failed to save category. Ex = " + ex.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Remove category from the database
	 * 
	 * @param categoryId Category id
	 * @return ResponseEntity status
	 */
	@DeleteMapping(path = "/categories/remove/{categoryId}")
	public ResponseEntity<Void> deleteCategoryById(@PathVariable long categoryId) {
		try {
			Category category = mCategoryService.getObjectById(categoryId);
			mCategoryService.deleteObject(category);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			Log.e(TAG, "deleteCategoryById: failed to delete category id = " + categoryId + ". Ex = " + ex.toString());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException e) {
			Log.e(TAG, "deleteCategoryById: failed to delete category. Ex = " + e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}