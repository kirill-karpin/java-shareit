package ru.practicum.shareit.storage;

import java.util.HashMap;

public class StorageManager {

  private static final HashMap<String, Object> stores = new HashMap<>();

  public static <T> Storage<T> getStore(Class<T> clazz) {
    if (!stores.containsKey(clazz.getSimpleName())) {
      stores.put(clazz.getSimpleName(), new Storage<T>());
    }
    return (Storage<T>) stores.get(clazz.getSimpleName());
  }

}
