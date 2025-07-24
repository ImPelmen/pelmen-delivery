package kz.pelmen_delivery.repository;

import kz.pelmen_delivery.model.entity.DomainObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainObjectRepository extends JpaRepository<DomainObject, Long> {
}
