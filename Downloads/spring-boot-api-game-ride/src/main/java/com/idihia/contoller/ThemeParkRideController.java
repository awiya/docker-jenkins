package com.idihia.contoller;

import com.idihia.entity.ThemeParkRide;
import com.idihia.repository.ThemeParkRideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/rides")
public class ThemeParkRideController {

    private final ThemeParkRideRepository themeParkRideRepository;

    @Autowired
    public ThemeParkRideController(ThemeParkRideRepository themeParkRideRepository) {
        this.themeParkRideRepository = themeParkRideRepository;
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<ThemeParkRide> getRides() {
        return themeParkRideRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public ThemeParkRide getRide(@PathVariable Long id) {
        return themeParkRideRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("The ride with the id: %s is not found",id)));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ThemeParkRide createRide(@RequestBody @Valid ThemeParkRide ride) {
        return themeParkRideRepository.save(ride);
    }
}
