package ru.dwerd.weather.bot.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Users {
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private Long chatId;
    private LocalDateTime localDateTime;
    private Location location;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Location {
        private Double lat;
        private Double lon;
    }
}
