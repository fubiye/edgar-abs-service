package com.biye.edgar.abs.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testDefaultSearch() throws Exception {
        this.mockMvc.perform(get("/s?wd=test").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                                .andExpect(content().string("[{\"record\":\"test\",\"type\":\"String\"}]"));
    }
}