package com.motorcycle.service.controller;

import com.motorcycle.service.entity.Motorcycle;
import com.motorcycle.service.service.MotorcycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
public class MotorcycleController {

    @Autowired
    private MotorcycleService motorcycleService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Motorcycle>> findByUser(@PathVariable Integer userId) {
        List<Motorcycle> lstCars = motorcycleService.findByUser(userId);
        return lstCars.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lstCars);
    }

    @GetMapping
    public ResponseEntity<List<Motorcycle>> getAll() {
        List<Motorcycle> lstCars = motorcycleService.getAll();
        return lstCars.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lstCars);
    }

    @GetMapping(value = "/{prId}")
    public ResponseEntity<Motorcycle> getMotorcycleById(@PathVariable Integer prId) {
        Motorcycle user = motorcycleService.getMotorcycleById(prId);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Motorcycle> save(@RequestBody Motorcycle car) {
        Motorcycle carSave = motorcycleService.save(car);
        return ResponseEntity.ok(carSave);
    }
}
