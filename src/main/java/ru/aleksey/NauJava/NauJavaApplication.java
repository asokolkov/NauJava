package ru.aleksey.NauJava;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Calories App", version = "1.0"))
public class NauJavaApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(NauJavaApplication.class, args);
    }
}
