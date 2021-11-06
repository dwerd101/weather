package ru.dwerd.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.dwerd.weather.feign.WeatherFeignClient;


@RestController
@RequiredArgsConstructor
public class HelloController {
    private final WeatherFeignClient weatherFeignClient;
    @PostMapping("/")
    @SneakyThrows
    public BotApiMethod<?> printHello(@RequestBody Update update) {
        return new SendMessage(String.valueOf(update.getMessage().getChatId()), weatherFeignClient.getWeather("").toString() );
    }
}
