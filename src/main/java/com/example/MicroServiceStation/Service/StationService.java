package com.example.MicroServiceStation.Service;

import com.example.MicroServiceStation.Client.BikeClient;
import com.example.MicroServiceStation.DTO.BikeDTO;
import com.example.MicroServiceStation.DTO.StationDTO;
import com.example.MicroServiceStation.Models.Station;
import com.example.MicroServiceStation.Repositories.StationRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StationService {

    @Autowired
    private BikeClient bikeClient;

    @Autowired
    private StationRepository stationRepository;

    public StationDTO getStationById(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found with ID: " + id));
        List<BikeDTO> bikeDTO = null;
        try {
            if (station.getBikeId() != null) {
                bikeDTO = bikeClient.getBikesByStation(station.getBikeId());
            }

        } catch (FeignException e) {
            System.out.println("Warning: Bike service is unavailable.");
        }
        return new StationDTO(station.getId(), station.getName(), station.getLocation(), bikeDTO);
    }


    public List<StationDTO> getAllStations() {
        try {
            List<Station> stations = stationRepository.findAll();
            return stations.stream().map(station -> {
               List<BikeDTO> bikeDTo = null;
                try{
                    if (station.getBikeId() != null) {
                        bikeDTo = bikeClient.getBikesByStation(station.getBikeId());
                    }
                }catch (FeignException e) {
                    System.out.println("Warning: Bike service is unavailable.");
                }

                return  new StationDTO(station.getId(), station.getName(), station.getLocation(), bikeDTo);
            }).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("No stations Found: " + e.getMessage());
        }
    }


    public Station createStation(Station station) {
        try {
            return stationRepository.save(station);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating station: " + e.getMessage());
        }
    }


    public Station updateStation(Long id, Station station) {
        try {
            station.setId(id);
            return stationRepository.save(station);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error updating station: " + e.getMessage());
        }
    }


    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }


    public Station assignBikeToStation(Long stationId, Long bikeId) {
        try {
            Station station = stationRepository.findById(stationId)
                    .orElseThrow(() -> new RuntimeException("Station not found"));

            BikeDTO bike = bikeClient.getBikeById(bikeId);
            station.setBikeId(bike.getId());

            return stationRepository.save(station);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Bike with ID " + bikeId + " not found in bike-service");
        } catch (RuntimeException e) {
            throw new RuntimeException("Error assigning bike to station with ID: " + stationId);
        }

    }
}

