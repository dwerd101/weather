package ru.dwerd.weather.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface WeatherYaroslavlService extends WeatherService{
    SendMessage handle(long chatId);
}
