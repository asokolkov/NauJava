package ru.aleksey.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.aleksey.NauJava.entities.UserProduct;

@Repository
public interface UserProductRepository extends CrudRepository<UserProduct, Long>
{
}
