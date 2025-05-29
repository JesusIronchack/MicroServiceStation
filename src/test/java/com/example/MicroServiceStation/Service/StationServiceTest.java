package com.example.MicroServiceStation.Service;

import com.example.MicroServiceStation.Client.BikeClient;
import com.example.MicroServiceStation.DTO.BikeDTO;
import com.example.MicroServiceStation.DTO.StationDTO;
import com.example.MicroServiceStation.Models.Station;
import com.example.MicroServiceStation.Repositories.StationRepository;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @Mock
    private BikeClient bikeClient;

    @InjectMocks
    private StationService stationService;

    private Station testStation1;
    private Station testStation2;
    private BikeDTO testBikeDTO;

    @BeforeEach
    void setUp() {
        testStation1 = new Station();
        testStation1.setId(1L);
        testStation1.setName("Central Station");
        testStation1.setLocation("Downtown");
        testStation1.setBikeId(10L);

        testStation2 = new Station();
        testStation2.setId(2L);
        testStation2.setName("North Station");
        testStation2.setLocation("Uptown");

        testBikeDTO = new BikeDTO();
        testBikeDTO.setId(10L);
        testBikeDTO.setModel("Mountain Bike");
        testBikeDTO.setStatus("AVAILABLE");

    }

    @AfterEach
    void tearDown() {
        testStation1 = null;
        testStation2 = null;
        testBikeDTO = null;
    }

    @Test
    void testGetAllStations() {
        when(stationRepository.findAll()).thenReturn(List.of(testStation1, testStation2));
        when(bikeClient.getBikesByStation(1L)).thenReturn(List.of(testBikeDTO));
        when(bikeClient.getBikesByStation(2L)).thenReturn(List.of());
        List<StationDTO> result = stationService.getAllStations();

        assertNotNull(result, "El resultado no debería ser null");
        assertEquals(2, result.size(), "Deberían retornarse 2 estaciones");

        // Verificamos la primera estación
        StationDTO station1DTO = result.get(0);
        assertEquals(1L, station1DTO.getId());
        assertEquals("Central Station", station1DTO.getName());

        // Verificamos la segunda estación
        StationDTO station2DTO = result.get(1);
        assertEquals(2L, station2DTO.getId());
        assertEquals("North Station", station2DTO.getName());

    }


    @Test
    void testGetStationByIdWithBikes() {
        when(stationRepository.findById(1L)).thenReturn(Optional.of(testStation1));
        when(bikeClient.getBikesByStation(1L)).thenReturn(List.of(testBikeDTO));

        StationDTO result = stationService.getStationById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Central Station", result.getName());
        assertNotNull(result.getBike());
        assertEquals(1, result.getBike().size());
        assertEquals("Mountain Bike", result.getBike().get(0).getModel());

    }
}
