package ru.dwerd.weather.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.dwerd.weather.model.Weather;

@FeignClient(name = "WeatherFeignClient", url = "https://api.weather.yandex.ru/v2/forecast?lat=55.75396&lon=37.620393&extra=true")
public interface WeatherFeignClient {

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET,value = "/")
    Weather getWeather(@RequestHeader("X-Yandex-API-Key") String header);

}
