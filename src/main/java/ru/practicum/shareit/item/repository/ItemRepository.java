package ru.practicum.shareit.item.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

  List<Item> findByOwner_Id(Long userId);

  List<Item> findAllByNameContainingIgnoreCase(String searchString);

}
