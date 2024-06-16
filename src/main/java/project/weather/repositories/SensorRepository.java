package project.weather.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.weather.models.Sensor;
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    Sensor findByName(String name);
}
