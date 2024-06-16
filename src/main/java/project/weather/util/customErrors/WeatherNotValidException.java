package project.weather.util.customErrors;

public class WeatherNotValidException extends RuntimeException{

    public WeatherNotValidException(String msg){
        super(msg);
    }
}
