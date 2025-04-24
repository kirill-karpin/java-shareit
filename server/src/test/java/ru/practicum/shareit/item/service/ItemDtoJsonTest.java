package ru.practicum.shareit.item.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemDto;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemDtoJsonTest {

  private final JacksonTester<ItemDto> json;

  @Test
  void testItemDto() throws Exception {
    ItemDto itemDto = new ItemDto();
    itemDto.setId(1L);
    itemDto.setName("name");
    itemDto.setDescription("description");
    itemDto.setAvailable(true);

    JsonContent<ItemDto> result = json.write(itemDto);

    assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
    assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("name");
    assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
  }


}
