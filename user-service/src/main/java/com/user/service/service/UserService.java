package com.user.service.service;

import com.user.service.entity.User;
import com.user.service.feignClients.CarFeignClient;
import com.user.service.feignClients.MotorcycleFeignClient;
import com.user.service.model.CarDto;
import com.user.service.model.MotorcycleDto;
import com.user.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CarFeignClient carFeignClient;
    @Autowired
    private MotorcycleFeignClient motorcycleFeignClient;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(Integer prId) {
        return userRepository.findById(prId).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<CarDto> getCars(Integer prUserId) {
        return carFeignClient.getCars(prUserId);
//        return restTemplate.getForObject("http://localhost:8002/cars/user/" + prUserId, List.class);
    }

    public List<MotorcycleDto> getMotorcycles(Integer prUserId) {
        return motorcycleFeignClient.getMotos(prUserId);
//        return restTemplate.getForObject("http://localhost:8003/motos/user/" + prUserId, List.class);
    }

    public CarDto saveCar(Integer userId, CarDto car) {
        car.setUser(userId);
        return carFeignClient.save(car);
    }

    public MotorcycleDto saveMotorcycle(Integer userId, MotorcycleDto moto) {
        moto.setUser(userId);
        return motorcycleFeignClient.save(moto);
    }

    public Map<String, Object> getUser(Integer prUserId) {
        User user = this.getUserById(prUserId);
        Map<String, Object> result = new HashMap<>();
        if (user == null) {
            result.put("message", "User not found");
        } else {
            result.put("user", user);
        }
        result.put("cars", this.getCars(prUserId));
        result.put("mostos", this.getMotorcycles(prUserId));
        return result;
    }
}
