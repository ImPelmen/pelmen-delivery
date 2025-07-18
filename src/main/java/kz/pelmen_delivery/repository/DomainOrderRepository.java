package kz.pelmen_delivery.repository;

import kz.pelmen_delivery.model.dto.DomainOrderDto;
import kz.pelmen_delivery.model.enums.OrderStatus;
import kz.pelmen_delivery.model.entity.DomainOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DomainOrderRepository extends JpaRepository<DomainOrder, Long> {

    Optional<DomainOrder> findByRestaurantIdAndCreateByAndStatusIn(Long restaurantId, String createdBy,
                                                                   Set<OrderStatus> statuses);

    List<DomainOrder> findAllByCreatedBy(String username);
}
