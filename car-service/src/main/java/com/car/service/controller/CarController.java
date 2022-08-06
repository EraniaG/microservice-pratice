package com.car.service.controller;

import com.car.service.entity.Car;
import com.car.service.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Car>> findByUser(@PathVariable Integer userId) {
        List<Car> lstCars = carService.findByUser(userId);
        return lstCars.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lstCars);
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAll() {
        List<Car> lstCars = carService.getAll();
        return lstCars.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lstCars);
    }

    @GetMapping(value = "/{prId}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer prId) {
        Car user = carService.getCarById(prId);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Car> save(@RequestBody Car car) {
        Car carSave = carService.save(car);
        return ResponseEntity.ok(carSave);
    }
}
