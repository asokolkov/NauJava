package ru.aleksey.NauJava.repository;

import java.util.List;

public interface CrudRepository<T, Id>
{
    void create(T entity);

    T get(Id id);

    List<T> getAll();

    void update(T entity);

    void delete(Id id);
}
