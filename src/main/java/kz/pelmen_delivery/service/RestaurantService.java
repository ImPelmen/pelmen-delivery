package kz.pelmen_delivery.service;


import kz.pelmen_delivery.model.dto.RestaurantDto;
import kz.pelmen_delivery.model.request.RestaurantRequest;

import java.util.List;

public interface RestaurantService {

    List<RestaurantDto> getAllRestaurants();

    void createRestaurant(RestaurantRequest request);

    RestaurantDto getRestaurantInfo(Long id);

    void updateRestaurant(Long id, RestaurantRequest request);

    void deleteRestaurant(Long id);

    RestaurantDto findByName(String name);
}
