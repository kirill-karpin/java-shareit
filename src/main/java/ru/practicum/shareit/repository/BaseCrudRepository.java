package ru.practicum.shareit.repository;

import ru.practicum.shareit.storage.Storage;

public abstract class BaseCrudRepository<T> implements CrudRepository<T, Long> {

  private final Storage<T> store;

  public BaseCrudRepository(Storage<T> store) {
    this.store = store;
  }

  public T save(T entity) {
    return store.save(entity);
  }

  @Override
  public void delete(Long userId) {
    store.deleteById(userId);
  }

  @Override
  public T findById(Long userId) {
    return store.getById(userId);
  }

  @Override
  public Iterable<T> findAll() {
    return store.getAll();
  }
}
