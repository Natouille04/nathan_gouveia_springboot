package com.safetynet.safetynetalerts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonUpdateDTO {
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}