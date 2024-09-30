package ru.aleksey.NauJava.entities;

import java.util.List;

public class User
{
    private Long id;
    private String username;
    private List<Product> products;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }

    @Override
    public String toString()
    {
        return String.format("{\"id\":\"%s\", \"name\":\"%s\"}", id, username);
    }
}
