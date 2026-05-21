package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireResponseDTO;
import com.safetynet.safetynetalerts.dto.MedRecDTO;
import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.dto.PersonMedInfoDTO;
import com.safetynet.safetynetalerts.model.Firestation;
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

import static java.lang.Integer.parseInt;

@Service
public class FireService {
    private DataRepository dataRepository;
    private static final Logger logger = LogManager.getLogger(FireService.class);

    public FireService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public FireResponseDTO PeopleByAddress(String Address) {
        FireResponseDTO Response = new FireResponseDTO();
        List<PersonMedInfoDTO> PersonsMedInfos = new ArrayList<>();

        logger.debug("Defining DateTime formatter and today's date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();

        logger.debug("Parsing Persons, FireStations and MedicalRecords list");
        List<Person> Persons = dataRepository.getPersonList();
        List<Firestation> Firestations = dataRepository.getFireStationList();
        List<MedicalRecord> MedicalRecords = dataRepository.getMedicalRecordList();

        logger.debug("Filtering list with chosen address");
        List<Person> PersonsAtAddress = Persons.stream()
                .filter(p -> Objects.equals(p.getAddress(), Address))
                .toList();

        logger.debug("Creating Medical record file for each persons");
        PersonsAtAddress.forEach(p -> {
            PersonMedInfoDTO PersonInfo = new PersonMedInfoDTO();
            MedicalRecord MedicalRecord = MedicalRecords.stream()
                    .filter(m -> Objects.equals(m.getFirstName(), p.getFirstName()))
                    .filter(m -> Objects.equals(m.getLastName(), p.getLastName()))
                    .toList()
                    .get(0);

            MedRecDTO MedRec = new MedRecDTO();
            MedRec.setMedications(MedicalRecord.getMedications());
            MedRec.setAllergies(MedicalRecord.getAllergies());

            PersonInfo.setFirstName(p.getFirstName());
            PersonInfo.setLastName(p.getLastName());
            PersonInfo.setAge(Period.between(LocalDate.parse(MedicalRecord.getBirthdate(), formatter), now).getYears());
            PersonInfo.setMedRec(MedRec);

            PersonsMedInfos.add(PersonInfo);
        });

        logger.debug("Getting Fire Station using chosen address");
        Firestation firestation = Firestations.stream().filter(f -> Objects.equals(f.getAddress(), Address)).toList().get(0);

        logger.debug("Setting up response");
        Response.setPersonsMedInfos(PersonsMedInfos);
        Response.setStationNumber(parseInt(firestation.getStation()));

        logger.debug("Returning response");
        return Response;
    }
}
