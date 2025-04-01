package ru.practicum.shareit.repository;

public interface CrudRepository<T, K> {

  T findById(K id);

  T save(T entity);

  void delete(K id);

  Iterable<T> findAll();

}
