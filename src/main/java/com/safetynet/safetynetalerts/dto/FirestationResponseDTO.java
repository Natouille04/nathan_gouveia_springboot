package com.safetynet.safetynetalerts.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FirestationResponseDTO {
    private List<PersonInfoDTO> persons;
    private int adultCount;
    private int childCount;
}
