package kz.pelmen_delivery.service.Impl;

import jakarta.transaction.Transactional;
import kz.pelmen_delivery.exception.MealNotFoundException;
import kz.pelmen_delivery.exception.OrderNotFoundException;
import kz.pelmen_delivery.exception.RestaurantNotFoundException;
import kz.pelmen_delivery.model.enums.OrderStatus;
import kz.pelmen_delivery.model.dto.DomainOrderDto;
import kz.pelmen_delivery.model.entity.DomainOrder;
import kz.pelmen_delivery.model.entity.Meal;
import kz.pelmen_delivery.model.entity.Restaurant;
import kz.pelmen_delivery.model.request.OrderRequest;
import kz.pelmen_delivery.repository.DomainOrderRepository;
import kz.pelmen_delivery.repository.MealRepository;
import kz.pelmen_delivery.repository.RestaurantRepository;
import kz.pelmen_delivery.service.OrderService;
import kz.pelmen_delivery.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final DomainOrderRepository orderRepository;

    private final ObjectMapperUtil objectMapperUtil;

    private final MealRepository mealRepository;

    private final RestaurantRepository restaurantRepository;

    @Override
    public List<DomainOrderDto> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> objectMapperUtil.convert(order, DomainOrderDto.class))
                .toList();
    }

    @Override
    public DomainOrderDto handleOrder(OrderRequest request, String username) {
        DomainOrder order;
        Long mealId = request.getMealId();
        Optional<Meal> mealOptional = mealRepository.findById(mealId);
        if (mealOptional.isEmpty()) {
            log.error("Meal with id {} is not found while adding in order by user with username {}", mealId, username);
            throw new MealNotFoundException(String.format("Блюдо с номером %s не найдено!", mealId));
        }
        Meal meal = mealOptional.get();
        try {
            order = orderRepository.findByRestaurantIdAndCreateByAndStatusIn(request.getRestaurantId(), username, OrderStatus.getActiveStatuses())
                    .orElseThrow(
                            () -> new OrderNotFoundException(""));
            addMeal(order, meal);
        } catch (OrderNotFoundException ignored) {
            order = createOrder(request, username, meal);
        }
        return objectMapperUtil.convert(order, DomainOrderDto.class);
    }

    @Override
    public DomainOrderDto getOrderById(Long id) {
        DomainOrder order = findOrderById(id);
        return objectMapperUtil.convert(order, DomainOrderDto.class);
    }

    @Override
    @Transactional
    public DomainOrderDto clearOrder(Long id) {
        DomainOrder order = findOrderById(id);
        order.getMeals().clear();
        orderRepository.save(order);
        return objectMapperUtil.convert(order, DomainOrderDto.class);
    }

    @Override
    public List<DomainOrderDto> getOrderByUsername(String username) {
        List<DomainOrder> orders = orderRepository.findAllByCreatedBy(username);
        return orders.stream()
                .map(order -> objectMapperUtil.convert(order, DomainOrderDto.class))
                .toList();
    }

    private DomainOrder createOrder(OrderRequest request, String username, Meal meal) {
        Long restaurantId = request.getRestaurantId();
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            throw new RestaurantNotFoundException(String.format("Ресторан с номером %s не найден", restaurantId));
        }
        Restaurant restaurant = restaurantOptional.get();

        DomainOrder domainOrder = DomainOrder.builder()
                .createdBy(username)
                .restaurant(restaurant)
                .meals(List.of(meal))
                .status(OrderStatus.OPEN)
                .build();
        orderRepository.save(domainOrder);
        return domainOrder;
    }

    private void addMeal(DomainOrder order, Meal meal) {
        List<Meal> meals = order.getMeals();
        meals.add(meal);
        order.setMeals(meals);
        orderRepository.save(order);
    }

    private DomainOrder findOrderById(Long id) {
        Optional<DomainOrder> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            log.error("Order with id {} is not found", id);
            throw new OrderNotFoundException(String.format("Заказ с номером %s не найден!", id));
        }
        return orderOptional.get();
    }
}
