package com.feedback_service.feedback_service;

import com.feedback_service.feedback_service.configuration.jwt.request.AuthRequest;
import com.feedback_service.feedback_service.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserControllerIntegrationTest {
    @Autowired
    private UserRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:empty/reset-user-table.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-data-table.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void shouldCreateOneUser() throws Exception {
        final File jsonFile = new ClassPathResource("init/user.json").getFile();
        final String userToCreate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(post("/api/v1/users/user")
                        .contentType(APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .content(userToCreate))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(this.repository.findAll()).hasSize(4);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:empty/reset-user-table.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-data-table.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void shouldFetchAllUsers() throws Exception {
        this.mockMvc.perform(get("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].id").value(2))
                .andExpect(jsonPath("$.[1].id").value(3))
                .andExpect(jsonPath("$.[2].id").value(4));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:empty/reset-user-table.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-data-table.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void shouldFetchOneUser() throws Exception {
        this.mockMvc.perform(get("/api/v1/users/user/{id}", 2)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.username").value("test2"));
    }

    String getAccessToken() throws Exception {
        final AuthRequest request = new AuthRequest();
        request.setLogin("test2");
        request.setPassword("adminAdmin");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(request);
        ResultActions resultActions = this.mockMvc.perform(post("/auth/access")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        String result = resultActions.andReturn().getResponse().getContentAsString();
        Integer indexStart = result.indexOf(':') + 2;
        String accessToken = result.substring(indexStart);
        Integer indexEnd = accessToken.indexOf('\"');
        accessToken = accessToken.substring(0, indexEnd);

        System.out.println("Result: " + result);
        System.out.println("AccessToken: " + accessToken);

        return accessToken;
    }

}
