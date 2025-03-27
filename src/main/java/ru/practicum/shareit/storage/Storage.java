package ru.practicum.shareit.storage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class Storage<T> {

  private final Map<Long, T> store = new HashMap<>();


  private long counter = 1;

  public long getNextId() {
    return counter++;
  }

  public T save(T entity) {
    try {
      Method getId = entity.getClass().getDeclaredMethod("getId");
      Method setId = entity.getClass().getDeclaredMethod("setId", Long.class);
      Long entityId = (Long) getId.invoke(entity);

      if (entityId == null) {
        entityId = getNextId();
      }
      store.put(entityId, entity);

      setId.invoke(entity, entityId);

    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    return entity;
  }

  public void deleteById(Long id) {
    store.remove(id);
  }

  public T getById(Long id) {
    return store.get(id);
  }

  public Iterable<T> getAll() {
    return store.values();
  }
}
