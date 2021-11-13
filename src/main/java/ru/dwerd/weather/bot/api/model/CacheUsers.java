package ru.dwerd.weather.bot.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Data
@RequiredArgsConstructor
public class CacheUsers {
    private final Map<Long,Users> users;
    private final List<Users> historyUsers;

    public void addUser(Long userId, Users users) {
        this.users.put(userId,users);
    }
    public void addHistoryUser(Users users) {
        historyUsers.add(users);
    }
}
