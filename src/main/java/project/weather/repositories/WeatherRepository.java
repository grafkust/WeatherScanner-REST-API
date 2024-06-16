package project.weather.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.weather.models.Weather;

import java.util.Arrays;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    List<Weather> findAllByRainingIsTrue();




}
