package com.user.service.serviceimpl;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exception.ResourceNotFoundException;
import com.user.service.external.service.HotelService;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HotelService hotelService;
    @Override
    public User saveUser(User user) {
        String randomId =  UUID.randomUUID().toString();
        user.setUserId(randomId);
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user details doesn't exist"+userId));
        log.info("user id is***********"+user.getUserId());
        Rating[] obj = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        log.info("Here is the list*********"+obj);
        List<Rating> ratings = Arrays.stream(obj).toList();
        List<Rating> newRatingList = ratings.stream().map(rating -> {
            log.info("hotel id***********"+rating.getHotelId());
            //http://localhost:8082/hotelservice/
           //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotelservice/"+rating.getHotelId(), Hotel.class);

            Hotel hotel =  hotelService.getHotel(rating.getHotelId());
           log.info("response"+hotel);
           rating.setHotel(hotel);
           return  rating;
        }).collect(Collectors.toList());
        user.setRatings(newRatingList);
        return user;
    }
}
