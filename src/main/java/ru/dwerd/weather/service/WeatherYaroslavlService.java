package ru.dwerd.weather.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface WeatherYaroslavlService extends WeatherService{
    SendMessage handle(final long chatId, Message message);
}
