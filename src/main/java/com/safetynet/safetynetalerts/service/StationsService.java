package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.AddressDTO;
import com.safetynet.safetynetalerts.dto.InhabitantDTO;
import com.safetynet.safetynetalerts.dto.MedRecDTO;
import com.safetynet.safetynetalerts.dto.PersonMedInfoDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@Service
public class StationsService {
    private final DataRepository dataRepository;

    public StationsService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<AddressDTO> AddressByFirestation(int StationNumber) {
        List<AddressDTO> response = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();

        List<Person> Persons = dataRepository.getPersonList();
        List<Firestation> FireStations = dataRepository.getFireStationList();
        List<MedicalRecord> MedicalRecords = dataRepository.getMedicalRecordList();

        List<Firestation> filteredFireStations = FireStations.stream().filter(s -> Objects.equals(StationNumber, parseInt(s.getStation()))).toList();

        List<String> filteredAddresses = filteredFireStations.stream()
                .map(Firestation::getAddress)
                .toList();

        List<Person> filteredPersons = Persons.stream()
                .filter(p -> filteredAddresses.contains(p.getAddress()))
                .toList();

        filteredAddresses.forEach(address -> {
            AddressDTO addressDTO = new AddressDTO();
            List<InhabitantDTO> inhabitants = new ArrayList<>();

            List<Person> personsAtAddress = filteredPersons.stream().filter(p -> Objects.equals(p.getAddress(), address)).toList();

            personsAtAddress.forEach(p -> {
                InhabitantDTO inhabitant = new InhabitantDTO();
                MedRecDTO MedRec = new MedRecDTO();

                MedicalRecord MedicalRecord = MedicalRecords.stream()
                        .filter(m -> Objects.equals(m.getFirstName(), p.getFirstName()) && Objects.equals(m.getLastName(), p.getLastName()))
                        .toList()
                        .get(0);

                MedRec.setMedications(MedicalRecord.getMedications());
                MedRec.setAllergies(MedicalRecord.getAllergies());

                inhabitant.setFirstName(p.getFirstName());
                inhabitant.setLastName(p.getLastName());
                inhabitant.setAge(Period.between(LocalDate.parse(MedicalRecord.getBirthdate(), formatter), now).getYears());
                inhabitant.setPhone(p.getPhone());
                inhabitant.setMedicalRecord(MedRec);

                inhabitants.add(inhabitant);
            });

            addressDTO.setAddress(address);
            addressDTO.setInhabitants(inhabitants);

            response.add(addressDTO);
        });

        return response;
    }
}
