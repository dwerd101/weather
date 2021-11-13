package ru.dwerd.weather.bot.mapper;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dwerd.weather.bot.api.model.Users;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public Users toUsers(Message message) {
        try {

            return Users.builder()
                .chatId(message.getChatId())
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .localDateTime(LocalDateTime.now())
                .userId(message.getFrom().getId())
                .username(message.getFrom().getUserName())
                .location(Users.Location.builder()
                    .lat(message.getLocation().getLatitude())
                    .lon(message.getLocation().getLongitude())
                    .build())
                .build();
        } catch (NullPointerException e) {
            return Users.builder()
                .chatId(message.getChatId())
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .localDateTime(LocalDateTime.now())
                .userId(message.getFrom().getId())
                .username(message.getFrom().getUserName())
                .location(Users.Location.builder()
                    .lat(0D)
                    .lon(0D)
                    .build())
                .build();
        }
    }
}
