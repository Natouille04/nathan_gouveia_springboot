package com.safetynet.safetynetalerts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonMedInfoDTO {
    private String FirstName;
    private String LastName;
    private int Age;
    private MedRecDTO MedRec;
}
