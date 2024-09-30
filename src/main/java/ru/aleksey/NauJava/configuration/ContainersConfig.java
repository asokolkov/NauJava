package ru.aleksey.NauJava.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.aleksey.NauJava.entities.Product;
import ru.aleksey.NauJava.entities.User;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ContainersConfig
{
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Product> products()
    {
        return new ArrayList<>();
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<User> users()
    {
        return new ArrayList<>();
    }
}
