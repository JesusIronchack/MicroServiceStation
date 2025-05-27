package com.example.MicroServiceStation.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationDTO {
    private Long id;
    private String name;
    private String location;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long bikeId;
    private List<BikeDTO> bike;

    public StationDTO(Long id, String name, String location,Long bikeId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.bikeId = bikeId;
    }

    public StationDTO(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
    public StationDTO(Long id, String name, String location, List<BikeDTO> bike) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.bike = bike;
    }

}
