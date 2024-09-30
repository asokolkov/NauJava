package ru.aleksey.NauJava.entities;

public class Product
{
    private Long id;
    private String name;
    private Integer calories;

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
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getCalories()
    {
        return calories;
    }

    public void setCalories(Integer calories)
    {
        this.calories = calories;
    }

    @Override
    public String toString()
    {
        return String.format("{\"id\":\"%s\", \"name\":\"%s\", \"calories\":\"%s\"}", id, name, calories);
    }
}
