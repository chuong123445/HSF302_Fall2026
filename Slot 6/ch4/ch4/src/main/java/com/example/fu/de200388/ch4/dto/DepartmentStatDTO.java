package com.example.fu.de200388.ch4.dto;

public record DepartmentStatDTO(
        String code,
        String name,
        long studentCount,
        Double averageGpa
) {
}
