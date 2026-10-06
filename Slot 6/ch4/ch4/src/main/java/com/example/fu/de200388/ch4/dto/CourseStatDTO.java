package com.example.fu.de200388.ch4.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


public record CourseStatDTO(String code, String name, Integer capacity,
                            Long enrolled, Double avgGpa) {


    public long remaining(){
        return capacity - enrolled;
    }
}
