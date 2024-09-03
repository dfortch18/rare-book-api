package com.dfortch.rarebook.controller;

import com.dfortch.rarebook.dto.request.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.dfortch.rarebook.RareBookApiApplicationConstants.API_VERSION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RareBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetBooks() throws Exception {
        mockMvc.perform(get("/api/" + API_VERSION + "/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testRareBookCrud() throws Exception {
        CreateRareBookRequest createRequest = new CreateRareBookRequest(
                "The Great Gatsby",
                "F. Scott Fitzgerald",
                "science-fiction", 1925,
                "9780743273789",
                "NEW",
                "RARE",
                "A classic novel",
                10.99,
                List.of(new CreateEditionRequest(1, 1925, "First Edition"))
        );

        String createdResponseContent = mockMvc.perform(post("/api/" + API_VERSION + "/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.recordId").isNumber())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long createdBookId = objectMapper.readTree(createdResponseContent).path("recordId").asLong();

        // Test Get book by id
        String retrievedResponseContent = mockMvc.perform(get("/api/" + API_VERSION + "/books/" + createdBookId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdBookId))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode book = objectMapper.readTree(retrievedResponseContent);

        // Test update book
        UpdateRareBookRequest updateRequest = new UpdateRareBookRequest(
                "The Great Gatsby - Updated",
                "F. Scott Fitzgerald",
                "science-fiction",
                1925,
                "9780743273789",
                "USED",
                "VERY_RARE",
                "Updated description",
                12.99,
                List.of(new UpdateEditionRequest(
                        book.path("editions").get(0).path("id").asLong(),
                        1,
                        1925,
                        "Updated Edition")
                )
        );

        mockMvc.perform(put("/api/" + API_VERSION + "/books/" + createdBookId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));

        // Test delete book
        mockMvc.perform(delete("/api/" + API_VERSION + "/books/" + createdBookId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }
}
