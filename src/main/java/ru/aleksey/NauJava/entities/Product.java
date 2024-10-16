package ru.aleksey.NauJava.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private Integer calories;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private final List<UserProduct> users = new ArrayList<>();

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

    public List<UserProduct> getUsers()
    {
        return users;
    }
}
