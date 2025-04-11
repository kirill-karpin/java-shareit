package ru.practicum.shareit.item.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

  List<Item> findAllByNameContainingIgnoreCaseAndIsAvailable(
      @Size(max = 512) @NotNull String name, Boolean isAvailable);

  List<Item> findAllByOwner_Id(Long ownerId);
}
