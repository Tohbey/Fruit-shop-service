package com.example.fruitshopapi.controllers.v1;

import com.example.fruitshopapi.api.v1.model.CategoryDTO;
import com.example.fruitshopapi.controllers.RestResponseEntityExceptionHandler;
import com.example.fruitshopapi.domain.Category;
import com.example.fruitshopapi.services.CategoryService;
import com.example.fruitshopapi.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    public static final String NAME_1 = "Fafo";
    public static final String NAME_2 = "tobey";
    public static final Long ID_1 = 1L;
    public static final Long ID_2 = 2L;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;


    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllCategories() throws Exception {
        CategoryDTO categoryDTO1 = getCategory1();
        CategoryDTO categoryDTO2 = getCategory2();

        List<CategoryDTO> categoryDTOList = Arrays.asList(categoryDTO1, categoryDTO2);

        when(categoryService.getAllCategories()).thenReturn(categoryDTOList);

        mockMvc.perform(get(CategoryController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories",hasSize(2)));
    }

    @Test
    public void getCategoryByName() throws Exception {
        CategoryDTO categoryDTO1 = getCategory1();

        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO1);

        mockMvc.perform(get(CategoryController.BASE_URL +"/"+NAME_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(NAME_1)));
    }

    @Test
    public void testGetByNameNotFound() throws Exception {

        when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CategoryController.BASE_URL + "/Foo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private CategoryDTO getCategory1(){
        CategoryDTO category1 = new CategoryDTO();

        category1.setId(ID_1);
        category1.setName(NAME_1);

        return category1;
    }

    private CategoryDTO getCategory2(){
        CategoryDTO category1 = new CategoryDTO();

        category1.setId(ID_2);
        category1.setName(NAME_2);

        return category1;
    }
}