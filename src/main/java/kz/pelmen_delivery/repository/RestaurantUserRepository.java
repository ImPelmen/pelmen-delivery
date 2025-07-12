package kz.pelmen_delivery.repository;

import kz.pelmen_delivery.model.entity.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantUserRepository extends JpaRepository<RestaurantUser, Long> {
}
