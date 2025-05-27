package com.example.MicroServiceStation.Client;

import com.example.MicroServiceStation.DTO.BikeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "MicroServiceBike")
public interface BikeClient {
    @GetMapping("/api/bikes/{id}")
    BikeDTO getBikeById(@PathVariable Long id);

    //Muestra todas las bicicletas de una estacion
    @GetMapping("/api/bikes/stations/{stationId}")
    List<BikeDTO> getBikesByStation(@PathVariable Long stationId);
}
