package ru.aleksey.NauJava.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.aleksey.NauJava.entities.User;

import java.util.UUID;

@SpringBootTest
public class UserRepositoryTests
{
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindUserByLoginAndPasswordValid()
    {
        var name = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var foundUser = userRepository.findByLoginAndPassword(login, password);

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
        Assertions.assertEquals(name, foundUser.getName());
        Assertions.assertEquals(login, foundUser.getLogin());
        Assertions.assertEquals(password, foundUser.getPassword());
    }

    @Test
    public void testFindUserByLoginAndPasswordInvalidLogin()
    {
        var name = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var foundUser = userRepository.findByLoginAndPassword(UUID.randomUUID().toString(), password);

        Assertions.assertNull(foundUser);
    }

    @Test
    public void testFindUserByLoginAndPasswordInvalidPassword()
    {
        var name = UUID.randomUUID().toString();
        var login = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();

        var user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        var foundUser = userRepository.findByLoginAndPassword(login, UUID.randomUUID().toString());

        Assertions.assertNull(foundUser);
    }
}
