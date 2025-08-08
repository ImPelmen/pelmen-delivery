package kz.pelmen_delivery.service;

import kz.pelmen_delivery.model.request.EmployeeRequest;

public interface RestaurantEmployeeService {

    void addEmployee(Long id, EmployeeRequest request);

    void removeEmployee(Long id, EmployeeRequest request);
}
