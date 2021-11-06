package ru.dwerd.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Fact {
    Long temp;
    Long feels_like;
    String condition;
    Long wind_speed;
    Long pressure_mm;
    Long humidity;

    public String getCondition() {
        String newCondition ="";
        if(condition.contains("-")) {
            newCondition = condition.replace("-","_");
            return newCondition;
        } else return condition;
    }

    public Long getTemp() {
        return temp;
    }

    public Long getFeels_like() {
        return feels_like;
    }

    public Long getWind_speed() {
        return wind_speed;
    }

    public Long getPressure_mm() {
        return pressure_mm;
    }

    public Long getHumidity() {
        return humidity;
    }
}
