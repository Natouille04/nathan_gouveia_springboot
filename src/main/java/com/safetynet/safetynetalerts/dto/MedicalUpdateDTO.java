package com.safetynet.safetynetalerts.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MedicalUpdateDTO {
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;
}
