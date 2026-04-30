package com.safetynet.safetynetalerts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonInfoLastNameResponseDTO {
    private String FirstName;
    private String LastName;
    private String Address;
    private int Age;
    private String Email;
    private MedRecDTO MedicalRecord;
}
