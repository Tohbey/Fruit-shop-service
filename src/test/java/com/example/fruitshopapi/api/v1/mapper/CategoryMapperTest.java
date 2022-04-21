package com.example.fruitshopapi.api.v1.mapper;

import com.example.fruitshopapi.api.v1.model.CategoryDTO;
import com.example.fruitshopapi.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryMapperTest {

    public static final String NAME = "fruits";
    public static final long ID = 1L;

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {

        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}