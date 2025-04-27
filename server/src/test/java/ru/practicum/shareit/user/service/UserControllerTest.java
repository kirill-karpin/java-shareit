package ru.practicum.shareit.user.service;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private UserDto userDto;

  @Autowired
  ObjectMapper mapper;

  @BeforeEach
  public void setUp() {
    userDto = new UserDto();
    userDto.setId(1L);
    userDto.setName("test");
    userDto.setEmail("test@example.com");
  }

  @Test
  public void testGetUserById() throws Exception {
    Long userId = 1L;

    when(userService.getById(1L)).thenReturn(userDto);

    mockMvc.perform(get("/users/{id}", userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
        .andExpect(jsonPath("$.name", is(userDto.getName())))
        .andExpect(jsonPath("$.email", is(userDto.getEmail())));

    verify(userService, times(1)).getById(1L);
  }

  @Test
  public void testCreateUser() throws Exception {
    CreateUserDto createUserDto = CreateUserDto.builder()
        .name("test")
        .email("test@mail.com")
        .build();

    when(userService.create(createUserDto)).thenReturn(userDto);

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(createUserDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value(userDto.getName()))
        .andExpect(jsonPath("$.email").value(userDto.getEmail()));

    verify(userService, times(1)).create(createUserDto);
  }

  @Test
  public void testUpdateUser() throws Exception {
    Long userId = 1L;
    UpdateUserDto updatedUserDto = new UpdateUserDto();
    updatedUserDto.setName("test2");
    updatedUserDto.setEmail("test2@mail.com");

    UserDto updatedUser = new UserDto();
    updatedUser.setId(userId);
    updatedUser.setName("test2");
    updatedUser.setEmail("test2@mail.com");

    when(userService.update(1L, updatedUserDto)).thenReturn(updatedUser);

    mockMvc.perform(patch("/users/{id}", userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(updatedUserDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("test2"))
        .andExpect(jsonPath("$.email").value("test2@mail.com"));

    verify(userService, times(1)).update(1L, updatedUserDto);
  }

  @Test
  public void testDeleteUser() throws Exception {
    Long userId = 1L;

    mockMvc.perform(delete("/users/{id}", userId))
        .andExpect(status().isOk());

    verify(userService, times(1)).delete(userId);
  }

  private String asJsonString(final Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
