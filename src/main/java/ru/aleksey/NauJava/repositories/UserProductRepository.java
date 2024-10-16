package ru.aleksey.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.aleksey.NauJava.entities.UserProduct;

public interface UserProductRepository extends CrudRepository<UserProduct, Long>
{
}
