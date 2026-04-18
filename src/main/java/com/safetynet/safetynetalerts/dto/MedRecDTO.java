package com.safetynet.safetynetalerts.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MedRecDTO {
    private List<String> Medications;
    private List<String> Allergies;
}
