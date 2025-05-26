package com.example.MicroServiceStation.Repositories;

import com.example.MicroServiceStation.Models.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {

}
