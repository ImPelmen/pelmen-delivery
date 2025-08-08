package kz.pelmen_delivery.service.Impl;

import kz.pelmen_delivery.exception.RestaurantNotFoundException;
import kz.pelmen_delivery.exception.RoleNotFoundException;
import kz.pelmen_delivery.exception.UserNotFoundException;
import kz.pelmen_delivery.model.entity.DomainUser;
import kz.pelmen_delivery.model.entity.Restaurant;
import kz.pelmen_delivery.model.entity.RestaurantUser;
import kz.pelmen_delivery.model.entity.Role;
import kz.pelmen_delivery.model.enums.RoleTitle;
import kz.pelmen_delivery.model.request.EmployeeRequest;
import kz.pelmen_delivery.repository.DomainUserRepository;
import kz.pelmen_delivery.repository.RestaurantRepository;
import kz.pelmen_delivery.repository.RestaurantUserRepository;
import kz.pelmen_delivery.repository.RoleRepository;
import kz.pelmen_delivery.service.RestaurantEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantEmployeeServiceImpl implements RestaurantEmployeeService {

    private final DomainUserRepository domainUserRepository;

    private final RestaurantRepository restaurantRepository;

    private final RestaurantUserRepository restaurantUserRepository;

    private final RoleRepository roleRepository;

    @Override
    public void addEmployee(Long id, EmployeeRequest request) {
        String email = request.getEmail();
        DomainUser employee = domainUserRepository.findByEmail(email).orElseThrow(() -> {
            log.error("User with email {} is not found!", email);
            return new UserNotFoundException(
                    String.format(
                            "Пользователь с email %s не найден! Пользователю необходимо зарегистрироваться", email));
        });

        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> {
            log.error("Restaurant with id {} is not found!", id);
            return new RestaurantNotFoundException(String.format("Ресторан с номером %s не найден!", id));
        });

        RestaurantUser restaurantUser = RestaurantUser.builder()
                .restaurant(restaurant)
                .userLogin(email)
                .build();

        restaurantUserRepository.save(restaurantUser);
        List<Role> roles = employee.getRoles();
        Role restaurantRole = roleRepository.findByName(RoleTitle.RESTAURANT).orElseThrow(() -> {
            log.error("Restaurant role is not found while adding employee!");
            return new RoleNotFoundException("Роль ресторана не найдена!");
        });

        roles.add(restaurantRole);
        employee.setRoles(roles);
        domainUserRepository.save(employee);
    }

    @Override
    public void removeEmployee(Long id, EmployeeRequest request) {
        String email = request.getEmail();
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> {
            log.error("Restaurant with id {} is not found!", id);
            return new RestaurantNotFoundException(String.format("Ресторан с номером %s не найден!", id));
        });
        RestaurantUser restaurantUser = restaurantUserRepository.findByUserLoginAndRestaurant(email, restaurant).orElseThrow(() -> {
            log.error("User with email {} is not employee of restaurant with name {}", email, restaurant.getName());
            return new UserNotFoundException(
                    String.format("Пользователь с email %s не является сотрудником ресторана с названием %s",
                            email, restaurant.getName()));
        });

        restaurantUserRepository.delete(restaurantUser);
    }
}
