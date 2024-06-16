package project.weather.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import project.weather.models.Weather;

import java.util.List;

public class SensorDTO {

    @Size(min = 3, max = 30)
    @NotEmpty
    @Setter @Getter
    private String name;


}
