package dev.breno5g.laterbox.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.breno5g.laterbox.TestcontainersConfiguration;
import dev.breno5g.laterbox.tag.application.dto.CreateTagDTO;
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

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@TestPropertySource(properties = {
        "laterBox.security.secret=test-secret-key-for-jwt"
})
class TagIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String uniqueUsername;
    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        uniqueUsername = "user_" + UUID.randomUUID();
        jwtToken = registerAndLogin(uniqueUsername);
    }

    @Test
    @DisplayName("Should create a new tag successfully")
    void createTag_success() throws Exception {
        String name = "tag_" + UUID.randomUUID();
        CreateTagDTO dto = new CreateTagDTO(name, "#FFFFFF", null);

        mockMvc.perform(post("/tags")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.color", is("#FFFFFF")));
    }

    @Test
    @DisplayName("Should fail with Internal Server Error when creating duplicate tag for the same user")
    void createTag_duplicate_fails() throws Exception {
        String name = "tag_" + UUID.randomUUID();
        CreateTagDTO dto = new CreateTagDTO(name, "#000000", null);

        mockMvc.perform(post("/tags")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/tags")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Tag already exists")));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when validation fails (empty name and color)")
    void createTag_validation_fails() throws Exception {
        CreateTagDTO invalid = new CreateTagDTO("", "", null);

        mockMvc.perform(post("/tags")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Tag name must not be empty")))
                .andExpect(jsonPath("$.color", is("Tag color must not be empty")));
    }

    @Test
    @DisplayName("Should reject unauthorized request without JWT token")
    void createTag_unauthorized() throws Exception {
        CreateTagDTO dto = new CreateTagDTO("tag_" + UUID.randomUUID(), "#ABCDEF", null);

        mockMvc.perform(post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
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
