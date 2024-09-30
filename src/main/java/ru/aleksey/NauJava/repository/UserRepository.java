package ru.aleksey.NauJava.repository;

import ru.aleksey.NauJava.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository implements CrudRepository<User, Long>
{
    private final List<User> users;

    @Autowired
    public UserRepository(List<User> users)
    {
        this.users = users;
    }

    @Override
    public void create(User user)
    {
        users.add(user);
    }

    @Override
    public User get(Long id)
    {
        return users.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<User> getAll()
    {
        return users;
    }

    @Override
    public void update(User user)
    {
        var userId = user.getId();
        var userIndex = users.stream().filter(x -> x.getId().equals(userId)).mapToInt(users::indexOf).findFirst().orElse(-1);
        if (userIndex != -1)
        {
            users.set(userIndex, user);
        }
    }

    @Override
    public void delete(Long id)
    {
        users.removeIf(x -> x.getId().equals(id));
    }
}
