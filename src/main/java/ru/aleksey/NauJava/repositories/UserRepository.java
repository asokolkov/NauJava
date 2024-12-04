package ru.aleksey.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.aleksey.NauJava.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password);
}