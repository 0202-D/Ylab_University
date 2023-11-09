package io.ylab.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
  /*  private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("Должен успешно аутентифицировать существующего пользователя")
    void testAuthenticateUser() throws Exception {
        UserRqDto userRqDto = Utils.getUserRqDto();
        User authenticatedUser = Utils.getUser();
        Mockito.when(authService.authenticateUser(userRqDto)).thenReturn(authenticatedUser);
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRqDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("username"));
    }

    @Test
    @DisplayName(" Должен успешно зарегестрировать пользователя")
    void testAddUser() throws Exception {
        UserRqDto userRqDto = Utils.getUserRqDto();
        User user = Utils.getUser();
        Mockito.when(authService.addUser(userRqDto)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRqDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("username"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("1"));
    }
*/
}