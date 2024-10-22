package ru.aleksey.NauJava.dtos;

import ru.aleksey.NauJava.enums.UserRole;

public class UserDto
{
    private Long id;
    private String name;
    private String login;
    private UserRole role;

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

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public UserRole getRole()
    {
        return role;
    }

    public void setRole(UserRole role)
    {
        this.role = role;
    }
}
