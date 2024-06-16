package project.weather.util;


//Объекты класса будут отправляться пользователю в случаях если происходит ошибка

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AllErrorResponse {

    //Описываем поля, которые будут отправляться при ошибке
    @Getter @Setter
    private String message;

    @Getter @Setter
    private LocalDateTime currentTime;

    public AllErrorResponse(String message, LocalDateTime timestamp) {
        this.message = message;
        this.currentTime = timestamp;
    }
}
