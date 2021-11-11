package ru.dwerd.weather.bot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import ru.dwerd.weather.bot.WeatherTelegramBot;
import ru.dwerd.weather.bot.api.facade.TelegramFacade;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    @Value("${telegrambot.webHookPathw}")
    private String webHookPath;
    @Value("${telegrambot.userName}")
    private String botUserName;
    @Value("${telegrambot.botToken}")
    private String botToken;
    @Bean
    public WeatherTelegramBot myWizardTelegramBot(TelegramFacade telegramFacade) {
        DefaultBotOptions options = new DefaultBotOptions();
        WeatherTelegramBot mySuperTelegramBot = new WeatherTelegramBot(options, telegramFacade);
        mySuperTelegramBot.setBotUserName(botUserName);
        mySuperTelegramBot.setBotToken(botToken);
        mySuperTelegramBot.setWebHookPath(webHookPath);
        return mySuperTelegramBot;
    }