package spring_boot.it211projectfinal.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import spring_boot.it211projectfinal.model.dto.response.UserResponseDTO;
import spring_boot.it211projectfinal.model.enums.Role;
import spring_boot.it211projectfinal.repository.BlacklistedTokenRepository;
import spring_boot.it211projectfinal.security.JwtAuthenticationFilter;
import spring_boot.it211projectfinal.security.JwtUtil;
import spring_boot.it211projectfinal.service.AuthService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AuthService authService;

    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    JwtUtil jwtUtil;

    @MockitoBean
    BlacklistedTokenRepository blacklistedTokenRepository;

    @Test
    void register_success() throws Exception {

        UserResponseDTO response =
                UserResponseDTO.builder()
                        .id(1L)
                        .fullName("Bang")
                        .email("test@gmail.com")
                        .username("bang")
                        .role(Role.STUDENT)
                        .build();

        when(authService.register(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                  "fullName":"Bang",
                                  "email":"test@gmail.com",
                                  "username":"bang",
                                  "password":"123456"
                                }
                                """)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void register_invalid_request() throws Exception {

        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                }
                                """)
                )
                .andExpect(status().isBadRequest());
    }
}