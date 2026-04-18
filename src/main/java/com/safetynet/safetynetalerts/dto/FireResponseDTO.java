package com.safetynet.safetynetalerts.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FireResponseDTO {
    private List<PersonMedInfoDTO> PersonsMedInfos;
    private int StationNumber;
}
