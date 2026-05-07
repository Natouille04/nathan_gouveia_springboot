package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireStationUpdateDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FireStationsService {
    private DataRepository dataRepository;

    public FireStationsService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public boolean save(Firestation firestation) {
        try {
            dataRepository.addFirestationToList(firestation);
            return true;
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Firestation update(String address, FireStationUpdateDTO firestation) {
        try {
            dataRepository.updateFireStation(address, firestation);
            Firestation updatedFirestation = dataRepository.getFireStationList().stream()
                    .filter(f -> Objects.equals(firestation.getAddress(), f.getAddress()))
                    .toList()
                    .get(0);

            return updatedFirestation;
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String address) {
        try {
            dataRepository.deleteFireStation(address);
            return true;
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
