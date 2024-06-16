package project.weather.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import project.weather.models.Sensor;

public class WeatherDTO {

    @NotNull
    @Max(value = 100)
    @Min(value = -100)
    @Getter @Setter
    private Double temperature;

    @NotNull
    @Getter @Setter
    private Boolean raining;

    @Getter @Setter
    private SensorDTO sensor;
}
