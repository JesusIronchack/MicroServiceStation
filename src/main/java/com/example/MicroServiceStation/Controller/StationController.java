package com.example.MicroServiceStation.Controller;


import com.example.MicroServiceStation.DTO.MessageResponseDTO;
import com.example.MicroServiceStation.DTO.StationDTO;
import com.example.MicroServiceStation.Models.Station;
import com.example.MicroServiceStation.Service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/stations")
public class StationController {
    @Autowired
    private StationService stationService;

    @PostMapping
    public Station createStation(@RequestBody Station station) {
        return stationService.createStation(station);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStationById(@PathVariable Long id) {
        try {
            StationDTO stationBasicDTO = stationService.getStationById(id);
            return ResponseEntity.ok(stationBasicDTO);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponseDTO(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllStations() {
        try {
            return ResponseEntity.ok(stationService.getAllStations());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponseDTO("Error retrieving stations"));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody Station station) {
        try{
            return ResponseEntity.ok(stationService.updateStation(id, station));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponseDTO("Error updating station with ID: " + id));
        }
    }


    @DeleteMapping("/{id}")
    public void deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
    }


    @PutMapping("/{stationId}/bikes/{bikeId}")
    public ResponseEntity<?> assignBike(@PathVariable Long stationId, @PathVariable Long bikeId) {
        try {
            return ResponseEntity.ok(stationService.assignBikeToStation(stationId, bikeId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponseDTO("Error assigning bike ID " + bikeId + " to station ID " + stationId));
        }
    }
}
