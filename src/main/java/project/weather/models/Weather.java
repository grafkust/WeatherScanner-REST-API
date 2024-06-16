package project.weather.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "Weather")
public class Weather {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Column(name = "temperature")
    @NotNull(message = "Temperature can't be empty")
    @Max(value = 100, message = "Temperature should be between -100 and 100")
    @Min(value = -100, message = "Temperature should be between -100 and 100")
    @Getter @Setter
    private Double temperature;

    @Setter @Getter
    @Column(name = "raining")
    @NotNull(message = "Raining can't be empty")
    private Boolean raining;

    @Column(name = "updated_at")
    @Getter @Setter
    private LocalDateTime updated_at;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "sensor",
            referencedColumnName ="name")
    @Getter @Setter
    private Sensor sensor;

}
