package project.weather.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.weather.models.Weather;
import project.weather.repositories.WeatherRepository;
import project.weather.util.customErrors.UnknownSensorException;
import project.weather.util.customErrors.WeatherNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class WeatherServices {

    private final WeatherRepository weatherRepository;
    private final SensorServices sensorServices;

    @Autowired
    public WeatherServices(WeatherRepository weatherRepository, SensorServices sensorServices) {
        this.weatherRepository = weatherRepository;
        this.sensorServices = sensorServices;
    }

    public List<Weather> findAll(){
        return weatherRepository.findAll();
    }


    //Ищем одну погоду по id. Если она не найдена, пробрасываем вместо результата кастомное исключение
    public Weather findOne(int id){
        Optional<Weather> weather = weatherRepository.findById(id);
        return weather.orElseThrow(WeatherNotFoundException::new);
    }

    //Save ликвиден только для weather, где sensor exist
    @Transactional
    public void saveWeather(Weather weather){
        //Проверяем есть ли такой sensor в базе. Если false выбрасываем exception, который будем отлавливать в controller
        if (sensorServices.findByName(weather.getSensor().getName()) == null) {
            throw new UnknownSensorException();
        }
        enrichWeather(weather);
        weatherRepository.save(weather);
    }

    @Transactional
    public void enrichWeather(Weather weather){
        weather.setUpdated_at(LocalDateTime.now());
        weather.setSensor(sensorServices.findByName(weather.getSensor().getName()));
    }


    public int getRainyDaysCount(){
        return weatherRepository.findAllByRainingIsTrue().size();
    }
}


