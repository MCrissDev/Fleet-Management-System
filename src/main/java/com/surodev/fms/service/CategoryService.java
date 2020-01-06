package com.surodev.fms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.surodev.fms.domain.Category;
import com.surodev.fms.repository.CategoryRepository;

@Service
public class CategoryService extends AbstractService<Category> {

	@Autowired
	private CategoryRepository mCategoryRepository;

	/**
	 * Retrieve long value of the id from a Category instance
	 * 
	 * @param object valid Category instance
	 * @return long value of the Category id
	 */
	@Override
	public long getObjectId(Category object) {
		return object.getId();
	}

	/**
	 * Retrieve service repository.
	 * 
	 * @return valid repository
	 */
	@Override
	public JpaRepository<Category, Long> getRepository() {
		return mCategoryRepository;
	}
}