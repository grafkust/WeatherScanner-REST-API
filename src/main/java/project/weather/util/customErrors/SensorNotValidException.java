package project.weather.util.customErrors;

public class SensorNotValidException extends RuntimeException{
    public SensorNotValidException(String msg){
        super(msg);
    }
}
