package com.user.service.controller;

import com.user.service.entity.User;
import com.user.service.model.CarDto;
import com.user.service.model.MotorcycleDto;
import com.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> lstUsers = userService.getAll();
        return lstUsers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lstUsers);
    }

    @GetMapping(value = "/{prId}")
    public ResponseEntity<User> getUserById(Integer prId) {
        User user = userService.getUserById(prId);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        User userSave = userService.save(user);
        return ResponseEntity.ok(userSave);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetUser")
    @GetMapping(value = "/get/{prId}")
    public ResponseEntity<Map<String, Object>> getUserCompleteById(@PathVariable Integer prId) {
        Map<String, Object> user = userService.getUser(prId);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<CarDto>> getCars(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<CarDto> lstCars = userService.getCars(userId);
            return lstCars == null || lstCars.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lstCars);
        }
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
    @GetMapping("/motos/{userId}")
    public ResponseEntity<List<MotorcycleDto>> getMotos(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<MotorcycleDto> lstMotorcycles = userService.getMotorcycles(userId);
            return lstMotorcycles == null || lstMotorcycles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lstMotorcycles);
        }
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCars")
    @PostMapping("/cars/{userId}")
    public ResponseEntity<CarDto> saveCar(@PathVariable Integer userId, @RequestBody CarDto car) {
        CarDto carSave = userService.saveCar(userId, car);
        return ResponseEntity.ok(carSave);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMotos")
    @PostMapping("/motos/{userId}")
    public ResponseEntity<MotorcycleDto> saveMotorcycle(@PathVariable Integer userId, @RequestBody MotorcycleDto moto) {
        MotorcycleDto carSave = userService.saveMotorcycle(userId, moto);
        return ResponseEntity.ok(carSave);
    }

    private ResponseEntity<List<CarDto>> fallBackGetCars(@PathVariable Integer userId, RuntimeException exception) {
        return new ResponseEntity("No tiene acceso al cat??logo de carros del usuario: " + userId, HttpStatus.BAD_GATEWAY);
    }

    private ResponseEntity<CarDto> fallBackSaveCars(@PathVariable Integer userId, @RequestBody CarDto car, RuntimeException exception) {
        return new ResponseEntity("No puede registrar carros para el usuario: " + userId, HttpStatus.BAD_GATEWAY);
    }

    private ResponseEntity<List<MotorcycleDto>> fallBackGetMotos(@PathVariable Integer userId, RuntimeException exception) {
        return new ResponseEntity("No tiene acceso al cat??logo de motos del usuario: " + userId, HttpStatus.BAD_GATEWAY);
    }

    private ResponseEntity<MotorcycleDto> fallBackSaveMotos(@PathVariable Integer userId, @RequestBody MotorcycleDto moto, RuntimeException exception) {
        return new ResponseEntity("No puede registrar motos para el usuario: " + userId, HttpStatus.BAD_GATEWAY);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetUser(@PathVariable Integer userId, RuntimeException exception) {
        return new ResponseEntity("No tiene acceso al cat??logo de veh??culos del usuario: " + userId, HttpStatus.BAD_GATEWAY);
    }
}
