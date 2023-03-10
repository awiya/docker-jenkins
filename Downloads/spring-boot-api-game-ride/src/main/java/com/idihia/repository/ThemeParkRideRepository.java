package com.idihia.repository;

import com.idihia.entity.ThemeParkRide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeParkRideRepository extends JpaRepository<ThemeParkRide,Long> {
    List<ThemeParkRide> findByName(String name);
}
