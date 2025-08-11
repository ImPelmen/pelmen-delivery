package kz.pelmen_delivery.service;

import kz.pelmen_delivery.model.dto.DomainOrderDto;
import kz.pelmen_delivery.model.entity.DomainOrder;
import kz.pelmen_delivery.model.request.ChangeOrderStatusRequest;
import kz.pelmen_delivery.model.request.OrderRequest;

import java.util.List;

public interface OrderService {

    List<DomainOrderDto> getAll();

    DomainOrderDto handleOrder(OrderRequest request, String username);

    DomainOrderDto getOrderById(Long id);

    DomainOrderDto clearOrder(Long id);

    List<DomainOrderDto> getUsersOrder(String email);

    List<DomainOrderDto> getAvailableOrders(String email);

    DomainOrderDto changeOrderStatus(Long id, ChangeOrderStatusRequest request, String email);

    List<DomainOrderDto> getCourierOrderHistory(String email);

    List<DomainOrderDto> getCourierAllOrders(String email);
}
