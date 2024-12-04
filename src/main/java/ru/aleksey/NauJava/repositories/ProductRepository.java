package ru.aleksey.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.aleksey.NauJava.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>
{
    Product findByName(String name);
}
