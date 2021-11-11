package ru.dwerd.weather.bot.config.context;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dwerd.weather.bot.config.BotState;
import ru.dwerd.weather.service.WeatherService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotStateContext {
    private Map<BotState, WeatherService> messageHandlers = new HashMap<>();

    public BotStateContext(List<WeatherService> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message) {
        WeatherService weatherservice = findService(currentState);
        return weatherservice.handle(message);
    }
    public SendMessage processButton(BotState currentState, long chatId) {
        WeatherService currentService = findService(currentState);
        if(currentState.equals(BotState.TODAY)) {
            TodayHandler todayHandler = (TodayHandler) currentService;
            return todayHandler.handle(chatId);
        }
        else if(currentState.equals(BotState.TOMMOROW)) {
            TomorrowHandler tomorrowHandler = (TomorrowHandler) currentService;
            return tomorrowHandler.handle(chatId);
        }
        else if(currentState.equals(BotState.WEEK)) {
            WeekHandler weekHandler = (WeekHandler)  currentService;
            return weekHandler.handle(chatId);
        }
        else if(currentState.equals(BotState.WEATHER_TODAY)) {
            WeatherTodayHandler weatherTodayHandler = (WeatherTodayHandler)  currentService;
            return weatherTodayHandler.handle(chatId);
        }
        else if(currentState.equals(BotState.WEATHER_TODAY_AND_TOMORROW)) {
            WeatherTodayAndTomorrowHandler weatherTodayHandler = (WeatherTodayAndTomorrowHandler)  currentService;
            return weatherTodayHandler.handle(chatId);
        }

        else return null;
    }

    private WeatherService findService(BotState currentState) {
        if (isUsertTelegramState(currentState)) {
            return messageHandlers.get(BotState.NEW_USER);
        }

        return messageHandlers.get(currentState);
    }

    private boolean isUsertTelegramState(BotState currentState) {
        switch (currentState) {
            case NEW_USER:
                return true;
            default:
                return false;
        }
    }
}
