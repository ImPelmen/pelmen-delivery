package kz.pelmen_delivery.repository;

import kz.pelmen_delivery.model.entity.DomainOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainOrderRepository extends JpaRepository<DomainOrder, Long> {
}
