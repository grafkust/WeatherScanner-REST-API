package project.weather.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.weather.dto.WeatherDTO;
import project.weather.dto.WeatherResponse;
import project.weather.models.Weather;
import project.weather.services.WeatherServices;
import project.weather.util.AllErrorResponse;
import project.weather.util.ErrorMessageBuilder;
import project.weather.util.customErrors.UnknownSensorException;
import project.weather.util.customErrors.WeatherNotFoundException;
import project.weather.util.customErrors.WeatherNotValidException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("measurements")
public class WeatherController {


    private final WeatherServices weatherServices;
    private final ModelMapper modelMapper;
    private final ErrorMessageBuilder errorMsgBuilder;

    public WeatherController(WeatherServices weatherServices, ModelMapper modelMapper, ErrorMessageBuilder errorMsgBuilder) {
        this.weatherServices = weatherServices;
        this.modelMapper = modelMapper;
        this.errorMsgBuilder = errorMsgBuilder;
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> saveWeather(@RequestBody @Valid WeatherDTO weatherDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            throw new WeatherNotValidException(errorMsgBuilder.errorMsg(bindingResult));
        }

        weatherServices.saveWeather(convertToWeather(weatherDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping()
    public WeatherResponse getAllWeatherMeasurements(){
        return new WeatherResponse(weatherServices.findAll().stream().map(this::convertToWeatherDTO).collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public int RainyDaysCount(){
        return weatherServices.getRainyDaysCount();
    }

    @GetMapping("/{id}")
    public WeatherDTO getWeather(@PathVariable("id") int id){
        Weather weather = weatherServices.findOne(id);
        return convertToWeatherDTO(weather);
    }


    @ExceptionHandler
    private ResponseEntity<AllErrorResponse> handleException(WeatherNotFoundException e){

        AllErrorResponse response = new AllErrorResponse(
                "Weather with this id wasn't found", LocalDateTime.now() );
        //преобразуем ответ в JSON и добавляем нашему HTTP ответу заголовок
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler
    private ResponseEntity<AllErrorResponse> handleException(WeatherNotValidException e){
        AllErrorResponse response = new AllErrorResponse(
                e.getMessage(), LocalDateTime.now() );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    private ResponseEntity<AllErrorResponse> handleException(UnknownSensorException e){
        AllErrorResponse response = new AllErrorResponse(
                "A Sensor with this name has not been registered before", LocalDateTime.now() );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private WeatherDTO convertToWeatherDTO(Weather weather){
        return  modelMapper.map(weather, WeatherDTO.class);
    }

    private Weather convertToWeather(WeatherDTO weatherDTO){
        return  modelMapper.map(weatherDTO, Weather.class);
    }
}
