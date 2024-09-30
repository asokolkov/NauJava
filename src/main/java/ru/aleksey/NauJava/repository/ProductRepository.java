package ru.aleksey.NauJava.repository;

import java.util.List;

import ru.aleksey.NauJava.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductRepository implements CrudRepository<Product, Long>
{
    private final List<Product> products;

    @Autowired
    public ProductRepository(List<Product> products)
    {
        this.products = products;
    }

    @Override
    public void create(Product product)
    {
        products.add(product);
    }

    @Override
    public Product get(Long id)
    {
        return products.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Product> getAll()
    {
        return products;
    }

    @Override
    public void update(Product product)
    {
        var productId = product.getId();
        var productIndex = products.stream().filter(x -> x.getId().equals(productId)).mapToInt(products::indexOf).findFirst().orElse(-1);
        if (productIndex != -1)
        {
            products.set(productIndex, product);
        }
    }

    @Override
    public void delete(Long id)
    {
        products.removeIf(x -> x.getId().equals(id));
    }
}
