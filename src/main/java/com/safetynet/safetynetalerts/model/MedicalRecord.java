package com.safetynet.safetynetalerts.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;
}