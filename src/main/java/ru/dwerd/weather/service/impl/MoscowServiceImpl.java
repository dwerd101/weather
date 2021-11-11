package ru.dwerd.weather.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.dwerd.weather.bot.config.BotState;
import ru.dwerd.weather.feign.WeatherFeignClient;
import ru.dwerd.weather.model.Weather;
import ru.dwerd.weather.service.WeatherMoscowService;
@Service
@RequiredArgsConstructor
public class MoscowServiceImpl implements WeatherMoscowService {

    private final WeatherFeignClient weatherFeignClient;
    private final InlineKeyboardMarkup inlineMessageButtons;
    @Override
    public SendMessage handle(final long chatId) {
        Weather weather = weatherFeignClient.getWeather("3ed5dbe8-670b-497b-99d0-350402bebb79");

        SendMessage sendMessage =new SendMessage(String.valueOf(chatId),weather.toString());
        sendMessage.setReplyMarkup(inlineMessageButtons);
        return sendMessage;
    }

    @Override
    public SendMessage handle(Message message) {
        Weather weather = weatherFeignClient.getWeather("3ed5dbe8-670b-497b-99d0-350402bebb79");

        final long chatId = message.getChatId();
        SendMessage replyToUser = new SendMessage(String.valueOf(chatId),weather.toString());
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MOSCOW;
    }
}
