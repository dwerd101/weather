package ru.dwerd.weather.bot.api.facade;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.dwerd.weather.bot.api.model.CacheUsers;
import ru.dwerd.weather.bot.api.model.Users;
import ru.dwerd.weather.bot.config.BotState;
import ru.dwerd.weather.bot.config.context.BotStateContext;
import ru.dwerd.weather.bot.mapper.UserMapper;


import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramFacade {
    private final BotStateContext botStateContext;
    //private final UserDataCache userDataCache;
    private final CacheUsers cacheUsers;
    private final UserMapper userMapper;



    public Optional<BotApiMethod<?>> handleUpdate(Update update) {
        Message message = update.getMessage();
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data1: {}", update.getCallbackQuery().getFrom().getUserName(),
                callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }


       else  if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                message.getFrom().getUserName(), message.getChatId(), message.getText());
            Optional<SendMessage> optionalSendMessage = handleInputMessage(message);
            if (optionalSendMessage.isPresent()) {
                return Optional.of(optionalSendMessage.get());
            }
        }
         else if(update.getMessage().getLocation()!=null) {
            log.info("Отправка коррдинатов");
            if(!cacheUsers.getUsers().containsKey(userMapper.toUsers(message).getUserId())) {
                cacheUsers.addUser(userMapper.toUsers(message).getUserId(),userMapper.toUsers(message));
                log.info("Сохранение пользователя");
                cacheUsers.addHistoryUser(userMapper.toUsers(message));
                return Optional.ofNullable(botStateContext.processInputMessage(BotState.YOUR_CITY, update.getMessage()));
            } else {
               Users users = cacheUsers.getUsers().get(userMapper.toUsers(message).getUserId());
               users.setLocation(userMapper.toUsers(message).getLocation());
               users.setUsername(userMapper.toUsers(message).getUsername());
               users.setFirstName(userMapper.toUsers(message).getFirstName());
               users.setLastName(userMapper.toUsers(message).getLastName());
               users.setChatId(users.getChatId());
               users.setUserId(userMapper.toUsers(message).getUserId());
               cacheUsers.addUser(userMapper.toUsers(message).getUserId(), users);
                cacheUsers.addHistoryUser(userMapper.toUsers(message));
               log.info("Сохранение полтзователя");
               log.info("Сохранение новых коррдинат");
               return Optional.ofNullable(botStateContext.processInputMessage(BotState.YOUR_CITY, update.getMessage()));
            }
        }

        return Optional.empty();
    }

    private Optional<SendMessage> handleInputMessage(Message message) {
        String inputMsg = message.getText();
       // final Long userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.MOSCOW;
                break;
            case "/Moscow":
            case "/moscow":
           // case "/today@hse_ebot":
                // case "СЕГОДНЯ":
                botState = BotState.MOSCOW;
                break;
            case "/Saint_Petersburg":
            case "/saint_petersburg":
                botState = BotState.SAINT_PETERSBURG;
                break;
            case "/Yaroslavl":
            case "/yaroslavl":
                botState = BotState.YAROSLAVL;
                break;
            case "/your_city":
            case "/Your_city":
                botState = BotState.YOUR_CITY;
                break;
            default:
                return Optional.empty();

        }

        replyMessage = botStateContext.processInputMessage(botState, message);

        return Optional.of(replyMessage);
    }

    private Optional<BotApiMethod<?>> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        SendMessage replyMessage;
        switch (buttonQuery.getData()) {
            case "buttonMoscow":
              //  userDataCache.getUsersCurrentBotState(userId);
                replyMessage = botStateContext.processButton(BotState.MOSCOW, chatId, buttonQuery.getMessage());
                return Optional.of(replyMessage);
            case "buttonPetersburg":
                replyMessage = botStateContext.processButton(BotState.SAINT_PETERSBURG, chatId, buttonQuery.getMessage());
                return Optional.of(replyMessage);
            case "buttonYaroslavl":
                replyMessage = botStateContext.processButton(BotState.YAROSLAVL,chatId, buttonQuery.getMessage());
                return  Optional.of(replyMessage);
            case "buttonYourCity":
                replyMessage = botStateContext.processButton(BotState.YOUR_CITY,chatId, buttonQuery.getMessage());

                return  Optional.of(replyMessage);
        }

        return Optional.empty();
    }


}