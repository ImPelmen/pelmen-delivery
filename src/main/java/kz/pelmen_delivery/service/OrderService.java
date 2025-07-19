package kz.pelmen_delivery.service;

import kz.pelmen_delivery.model.dto.DomainOrderDto;
import kz.pelmen_delivery.model.enums.OrderStatus;
import kz.pelmen_delivery.model.request.ChangeOrderStatusRequest;
import kz.pelmen_delivery.model.request.OrderRequest;

import java.util.List;

public interface OrderService {

    List<DomainOrderDto> getAll();

    DomainOrderDto handleOrder(OrderRequest request, String username);

    DomainOrderDto getOrderById(Long id);

    DomainOrderDto clearOrder(Long id);

    List<DomainOrderDto> getOrderByUsername(String username);

    DomainOrderDto changeOrderStatus(Long id, ChangeOrderStatusRequest request);
}
