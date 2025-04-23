package ru.practicum.shareit.request.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

  @Query("select i from ItemRequest i where i.requestor.id = ?1")
  List<ItemRequest> findAllByRequestor_Id(Long requestorId);
}
