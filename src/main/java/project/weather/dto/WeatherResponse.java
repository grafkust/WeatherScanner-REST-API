package project.weather.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {
    private List<WeatherDTO> weathers;

    public WeatherResponse(List<WeatherDTO> weathers){
        this.weathers = weathers;
    }

}
