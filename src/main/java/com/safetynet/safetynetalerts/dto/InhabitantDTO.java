package com.safetynet.safetynetalerts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InhabitantDTO {
    private String FirstName;
    private String LastName;
    private String Phone;
    private int Age;
    private MedRecDTO MedicalRecord;
}
