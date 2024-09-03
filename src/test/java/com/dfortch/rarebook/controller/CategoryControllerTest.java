package com.dfortch.rarebook.controller;

import com.dfortch.rarebook.dto.request.CreateCategoryRequest;
import com.dfortch.rarebook.dto.request.UpdateCategoryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.dfortch.rarebook.RareBookApiApplicationConstants.API_VERSION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetCategories() throws Exception {
        mockMvc.perform(get("/api/" + API_VERSION + "/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCategoryCrud() throws Exception {
        CreateCategoryRequest createRequest = new CreateCategoryRequest("New Category", "Description for new category", "new-category");

        // Test create category
        ResultActions resultActions = mockMvc.perform(post("/api/" + API_VERSION + "/categories")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.recordId").isNumber());

        String createdRequestResponseContent = resultActions.andReturn().getResponse().getContentAsString();

        Long createdCategoryId = objectMapper.readTree(createdRequestResponseContent).path("recordId").asLong();

        // Test get category by id
        mockMvc.perform(get("/api/" + API_VERSION + "/categories/" + createdCategoryId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdCategoryId));

        // Test update category
        UpdateCategoryRequest updateRequest = new UpdateCategoryRequest("Updated Category", "Updated Description", "updated-category");

        mockMvc.perform(put("/api/" + API_VERSION + "/categories/" + createdCategoryId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));

        // Test delete category
        mockMvc.perform(delete("/api/" + API_VERSION + "/categories/" + createdCategoryId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }
}
