package ru.dwerd.weather.bot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dwerd.weather.bot.api.model.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
public class ListUsersConfig {
    @Bean
    public Map<Long,Users> users() {
        return new HashMap<>();
    }

    @Bean
    public List<Users> listUsers() {
        return new ArrayList<>();
    }
}
