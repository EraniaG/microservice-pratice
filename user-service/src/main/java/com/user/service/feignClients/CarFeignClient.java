package com.user.service.feignClients;

import com.user.service.model.CarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service", url = "http://localhost:8002")
@RequestMapping("/cars")
public interface CarFeignClient {

    @PostMapping
    CarDto save(@RequestBody CarDto car);

    @GetMapping(value = "/user/{prId}")
    List<CarDto> getCars(@PathVariable Integer prId);
}
