package com.example.MicroServiceStation.Service;

import com.example.MicroServiceStation.Models.Station;
import com.example.MicroServiceStation.Repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public Station createStation(Station station) {
        return stationRepository.save(station);
    }

    public Station updateStation(Long id, Station station) {
        station.setId(id);
        return stationRepository.save(station);
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

}

