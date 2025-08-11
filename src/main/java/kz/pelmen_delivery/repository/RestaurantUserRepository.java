package kz.pelmen_delivery.repository;

import kz.pelmen_delivery.model.entity.Restaurant;
import kz.pelmen_delivery.model.entity.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantUserRepository extends JpaRepository<RestaurantUser, Long> {

    Optional<RestaurantUser> findByUserLoginAndRestaurant(String email, Restaurant restaurant);

    Optional<RestaurantUser> findByUserLogin(String email);

    Optional<List<RestaurantUser>> findByRestaurant(Restaurant restaurant);
}
