package ru.practicum.shareit.user.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.user.dto.UserDto;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDtoJsonTest {

  private final JacksonTester<UserDto> json;

  @Test
  void testUserDto() throws Exception {
    UserDto userDto = UserDto.builder().id(1L).name("Test").email("test@test.com").build();

    JsonContent<UserDto> result = json.write(userDto);

    assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
    assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Test");
    assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("test@test.com");
  }
}
