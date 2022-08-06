package com.user.service.feignClients;

import com.user.service.model.MotorcycleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "motorcycle-service", url = "http://localhost:8003")
@RequestMapping("/motos")
public interface MotorcycleFeignClient {
    @PostMapping
    MotorcycleDto save(@RequestBody MotorcycleDto moto);

    @GetMapping(value = "/user/{prId}")
    List<MotorcycleDto> getMotos(@PathVariable Integer prId);

}

