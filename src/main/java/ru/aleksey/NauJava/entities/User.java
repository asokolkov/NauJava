package ru.aleksey.NauJava.entities;

import jakarta.persistence.*;
import ru.aleksey.NauJava.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private final List<UserProduct> products = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private final List<Report> reports = new ArrayList<>();

    public long getId()
    {
        return id;
    }

    public void setId(long id)
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

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public UserRole getRole()
    {
        return role;
    }

    public void setRole(UserRole role)
    {
        this.role = role;
    }

    public List<UserProduct> getProducts()
    {
        return products;
    }

    public List<Report> getReports()
    {
        return reports;
    }
}
