package dev.breno5g.laterbox.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.breno5g.laterbox.TestcontainersConfiguration;
import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.user.application.dto.CreateUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@TestPropertySource(properties = {
        "laterBox.security.secret=test-secret-key-for-jwt"
})
class LinkIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        String uniqueUsername = "user_" + UUID.randomUUID();
        jwtToken = registerAndLogin(uniqueUsername);
    }

    @Test
    @DisplayName("Should create a new link successfully")
    void createLink_success() throws Exception {
        String url = "https://example.com/" + UUID.randomUUID();
        CreateLinkDTO dto = new CreateLinkDTO(url, "My title", "My description", new ArrayList<>(), null);

        mockMvc.perform(post("/links")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.url", is(url)))
                .andExpect(jsonPath("$.title", is("My title")))
                .andExpect(jsonPath("$.description", is("My description")));
    }

    @Test
    @DisplayName("Should fail with Internal Server Error when creating duplicate link for the same URL")
    void createLink_duplicate_fails() throws Exception {
        String url = "https://example.com/" + UUID.randomUUID();
        CreateLinkDTO dto = new CreateLinkDTO(url, "Title", "Desc", List.of(), null);

        mockMvc.perform(post("/links")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/links")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Link already exists")));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when validation fails (empty url, title, description)")
    void createLink_validation_fails() throws Exception {
        CreateLinkDTO invalid = new CreateLinkDTO("", "", "", List.of(), null);

        mockMvc.perform(post("/links")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.url", anyOf(
                        is("Link url must not be empty"),
                        is("Link url must not exceed 2048 characters"),
                        is("Only http/https URLs are allowed")
                )))
                .andExpect(jsonPath("$.title", is("Link title must not be empty")))
                .andExpect(jsonPath("$.description", is("Link description must not be empty")));
    }

    @Test
    @DisplayName("Should reject unauthorized request without JWT token")
    void createLink_unauthorized() throws Exception {
        CreateLinkDTO dto = new CreateLinkDTO("https://unauth.com", "Title", "Desc", List.of(), null);

        mockMvc.perform(post("/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should list links including the created one")
    void listLinks_includesCreated() throws Exception {
        String url = "https://list-test.com/" + UUID.randomUUID();
        CreateLinkDTO dto = new CreateLinkDTO(url, "List Title", "List Desc", List.of(), null);

        mockMvc.perform(post("/links")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/links")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].url", hasItem(url)));
    }

    private String registerAndLogin(String username) throws Exception {
        CreateUserDTO userDto = new CreateUserDTO(username, "Password@123");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(json);
        return node.get("token").asText();
    }
}
