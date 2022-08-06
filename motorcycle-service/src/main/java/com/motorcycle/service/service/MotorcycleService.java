package com.motorcycle.service.service;

import com.motorcycle.service.entity.Motorcycle;
import com.motorcycle.service.repository.MotorcycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorcycleService {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    public List<Motorcycle> getAll() {
        return motorcycleRepository.findAll();
    }

    public Motorcycle getMotorcycleById(Integer prId) {
        return motorcycleRepository.findById(prId).orElse(null);
    }

    public Motorcycle save(Motorcycle car) {
        return motorcycleRepository.save(car);
    }

    public List<Motorcycle> findByUser(Integer userId){
        return motorcycleRepository.findByUser(userId);
    }
}
