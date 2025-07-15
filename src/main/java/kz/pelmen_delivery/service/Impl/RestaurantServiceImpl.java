package kz.pelmen_delivery.service.Impl;

import kz.pelmen_delivery.exception.RestaurantAlreadyExistsException;
import kz.pelmen_delivery.exception.RestaurantNotFoundException;
import kz.pelmen_delivery.util.ObjectMapperUtil;
import kz.pelmen_delivery.model.dto.RestaurantDto;
import kz.pelmen_delivery.model.entity.Restaurant;
import kz.pelmen_delivery.model.request.RestaurantRequest;
import kz.pelmen_delivery.repository.RestaurantRepository;
import kz.pelmen_delivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final ObjectMapperUtil objectMapperUtil;

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(restaurant -> objectMapperUtil.convert(restaurant, RestaurantDto.class)).toList();
    }

    @Override
    public void createRestaurant(RestaurantRequest request) {
        String name = request.getName();
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByName(name);
        if (restaurantOptional.isPresent()) {
            log.error("Restaurant with name {} is already exists", name);
            throw new RestaurantAlreadyExistsException(
                    String.format("Ресторан с названием %s уже существует", name));
        }
        Restaurant restaurant = Restaurant.builder()
                .name(name)
                .address(request.getAddress())
                .description(request.getDescription())
                .build();

        restaurantRepository.save(restaurant);
    }

    @Override
    public RestaurantDto getRestaurantInfo(Long id) {
        Restaurant restaurant = findRestaurantById(id);
        return objectMapperUtil.convert(restaurant, RestaurantDto.class);
    }

    @Override
    public void updateRestaurant(Long id, RestaurantRequest request) {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setDescription(request.getDescription());
        restaurantRepository.save(restaurant);
    }

    private Restaurant findRestaurantById(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isEmpty()) {
            log.error("Restaurant with id {} is not found", id);
            throw new RestaurantNotFoundException(String.format("Ресторан с номером %s не найден", id));
        }
        return restaurantOptional.get();
    }

    @Override
    public void deleteRestaurant(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isEmpty()) {
            throw new RestaurantNotFoundException(String.format("Ресторан с номером %s не найден", id));
        }
        Restaurant restaurant = restaurantOptional.get();
        restaurantRepository.delete(restaurant);
    }
}
