package com.safetynet.safetynetalerts.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChildAlertResponseDTO {
    private String FirstName;
    private String LastName;
    private int Age;
    private List<HouseHoldMemberDTO> HouseHoldMember;
}
