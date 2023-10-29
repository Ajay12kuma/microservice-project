package com.ratingservice.repositories;

import com.ratingservice.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating,String> {
        List<Rating> findByUserId(String userId);
        List<Rating> findByHotelId(String hotelId);
}
