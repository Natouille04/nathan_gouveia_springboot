package com.safetynet.safetynetalerts.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddressDTO {
    private String Address;
    private List<InhabitantDTO> Inhabitants;
}
