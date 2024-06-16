package project.weather.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.weather.models.Sensor;
import project.weather.repositories.SensorRepository;
import project.weather.util.customErrors.SensorNotUniqueException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorServices {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorServices(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void saveSensor(Sensor sensor){
        if (sensorRepository.findByName(sensor.getName()) != null)
            throw new SensorNotUniqueException();
        sensorRepository.save(sensor);
    }

    public List<Sensor> findAll(){
        return sensorRepository.findAll();
    }

    public Sensor findByName(String name){
        return sensorRepository.findByName(name);
    }



}
