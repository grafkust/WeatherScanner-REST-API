package project.weather.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.weather.dto.SensorDTO;
import project.weather.models.Sensor;
import project.weather.services.SensorServices;
import project.weather.util.AllErrorResponse;
import project.weather.util.ErrorMessageBuilder;
import project.weather.util.customErrors.SensorNotUniqueException;
import project.weather.util.customErrors.SensorNotValidException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sensor")
public class SensorController {


    private final SensorServices sensorServices;
    private final ModelMapper modelMapper;
    private final ErrorMessageBuilder errorMsgBuilder;

    public SensorController(SensorServices sensorServices, ModelMapper modelMapper, ErrorMessageBuilder errorMsgBuilder){
        this.sensorServices = sensorServices;
        this.modelMapper = modelMapper;
        this.errorMsgBuilder = errorMsgBuilder;
    }


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> saveSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            throw new SensorNotValidException(errorMsgBuilder.errorMsg(bindingResult));
        }

        sensorServices.saveSensor(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }




    @ExceptionHandler
    private ResponseEntity<AllErrorResponse> handleException(SensorNotUniqueException e){
        AllErrorResponse response = new AllErrorResponse(
                "Sensor with this name is already exist", LocalDateTime.now() );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<AllErrorResponse> handleException(SensorNotValidException e){
        AllErrorResponse response = new AllErrorResponse(
                e.getMessage(), LocalDateTime.now() );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

}
