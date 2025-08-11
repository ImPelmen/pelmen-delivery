package kz.pelmen_delivery.service;

import kz.pelmen_delivery.model.dto.DomainUserDto;
import kz.pelmen_delivery.model.request.EmployeeRequest;

import java.util.List;

public interface RestaurantEmployeeService {

    List<DomainUserDto> getRestaurantEmployees(Long id);

    void addEmployee(Long id, EmployeeRequest request);

    void removeEmployee(Long id, EmployeeRequest request);
}
