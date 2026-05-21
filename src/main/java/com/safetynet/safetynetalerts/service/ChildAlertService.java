package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.ChildAlertResponseDTO;
import com.safetynet.safetynetalerts.dto.HouseHoldMemberDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ChildAlertService {
    private final DataRepository dataRepository;
    private static final Logger logger = LogManager.getLogger(ChildAlertService.class);

    public ChildAlertService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<ChildAlertResponseDTO> GetChildrenByAddress(String Address) {
        List<ChildAlertResponseDTO> response = new ArrayList<>();

        logger.debug("Parsing Persons list and Medical record list");
        List<Person> persons = dataRepository.getPersonList();
        List<MedicalRecord> medicalRecord = dataRepository.getMedicalRecordList();

        logger.debug("Defining DateTime formatter");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        logger.debug("Filtering list with chosen address");
        List<Person> personsAtAddress = persons.stream()
                .filter(p -> Objects.equals(p.getAddress(), Address))
                .toList();

        if (personsAtAddress.isEmpty()) {
            logger.debug("No one at chosen address, returning empty list");
            return List.of();
        }

        logger.debug("Filtering people only for children");
        personsAtAddress.forEach(p -> {
                    ChildAlertResponseDTO ResponseDTO = new ChildAlertResponseDTO();

                    List<MedicalRecord> MedRec = medicalRecord.stream()
                            .filter(m -> Objects.equals(m.getFirstName(), p.getFirstName()))
                            .filter(m -> Objects.equals(m.getLastName(), p.getLastName()))
                            .toList();

                    if (MedRec.isEmpty()) return;

                    LocalDate birthdate = LocalDate.parse(MedRec.get(0).getBirthdate(), formatter);
                    LocalDate now = LocalDate.now();

                    int age = Period.between(birthdate, now).getYears();

                    if(age <= 18) {
                        List<HouseHoldMemberDTO> householdMembers = new ArrayList<>();

                        personsAtAddress.forEach(m -> {
                            if (m == p) return;

                            HouseHoldMemberDTO member = new HouseHoldMemberDTO();
                            member.setFirstName(m.getFirstName());
                            member.setLastName(m.getLastName());

                            householdMembers.add(member);
                        });

                        ResponseDTO.setFirstName(p.getFirstName());
                        ResponseDTO.setLastName(p.getLastName());
                        ResponseDTO.setAge(age);
                        ResponseDTO.setHouseHoldMember(householdMembers);

                        response.add(ResponseDTO);
                    }
                });

        logger.debug("Returning list of children at address");
        return response;
    }
}