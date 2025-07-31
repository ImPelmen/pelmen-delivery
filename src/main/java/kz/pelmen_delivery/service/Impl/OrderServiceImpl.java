package kz.pelmen_delivery.service.Impl;

import jakarta.transaction.Transactional;
import kz.pelmen_delivery.exception.*;
import kz.pelmen_delivery.model.entity.*;
import kz.pelmen_delivery.model.enums.OrderStatus;
import kz.pelmen_delivery.model.dto.DomainOrderDto;
import kz.pelmen_delivery.model.request.ChangeOrderStatusRequest;
import kz.pelmen_delivery.model.request.OrderRequest;
import kz.pelmen_delivery.repository.*;
import kz.pelmen_delivery.service.OrderService;
import kz.pelmen_delivery.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final DomainOrderRepository orderRepository;

    private final MealRepository mealRepository;

    private final RestaurantRepository restaurantRepository;

    private final DomainObjectRepository domainObjectRepository;

    private final DomainUserRepository domainUserRepository;

    @Override
    public List<DomainOrderDto> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> ModelMapperUtil.map(order, DomainOrderDto.class))
                .toList();
    }

    @Override
    public DomainOrderDto handleOrder(OrderRequest request, String email) {
        DomainOrder order;
        Long mealId = request.getMealId();
        Optional<Meal> mealOptional = mealRepository.findById(mealId);
        if (mealOptional.isEmpty()) {
            log.error("Meal with id {} is not found while adding in order by user with email {}", mealId, email);
            throw new MealNotFoundException(String.format("Блюдо с номером %s не найдено!", mealId));
        }
        Meal meal = mealOptional.get();
        try {
            order = orderRepository.findByRestaurantIdAndCreatedByAndStatusIn(meal.getMealCategory()
                                    .getRestaurant().getId(),
                            email, OrderStatus.getActiveStatuses())
                    .orElseThrow(
                            () -> new OrderNotFoundException(""));
            order = addMeal(order, meal);
        } catch (OrderNotFoundException ignored) {
            order = createOrder(request, email, meal);
        }
        return ModelMapperUtil.map(order, DomainOrderDto.class);
    }

    @Override
    public DomainOrderDto getOrderById(Long id) {
        DomainOrder order = findOrderById(id);
        return ModelMapperUtil.map(order, DomainOrderDto.class);
    }

    @Override
    @Transactional
    public DomainOrderDto clearOrder(Long id) {
        DomainOrder order = findOrderById(id);
        order.getMeals().clear();
        orderRepository.save(order);
        return ModelMapperUtil.map(order, DomainOrderDto.class);
    }

    @Override
    public List<DomainOrderDto> getUsersOrder(String email) {
        List<DomainOrder> orders = orderRepository.findAllByCreatedBy(email);
        return orders.stream()
                .map(order -> ModelMapperUtil.map(order, DomainOrderDto.class))
                .toList();
    }

    @Override
    public List<DomainOrderDto> getAvailableOrders(String email) {
        //TODO: Здесь надо будет находить пользователя
        //TODO: По роли уже надо будет определить дать запросы
        return null;
    }

    @Override
    public DomainOrderDto changeOrderStatus(Long id, ChangeOrderStatusRequest orderStatusRequest, String email) {
        //TODO: В дальнейшем надо будет проверять, может ли этот пользователь на данном этапе менять статус
        DomainOrder order = findOrderById(id);
        OrderStatus status = OrderStatus.findByTitle(orderStatusRequest.getStatusTitle());
        switch (status) {
            case OPENED -> {
                Long objectId = orderStatusRequest.getObjectId();
                if (Objects.isNull(objectId)) {
                    log.error("Can not open order without destination object");
                    throw new IllegalObjectSelectionException("Не можем сделать заказ без адреса доставки!");
                }
                Optional<DomainObject> domainObjectOptional = domainObjectRepository.findById(objectId);
                if (domainObjectOptional.isEmpty()) {
                    throw new IllegalObjectSelectionException(String.format("Адрес с номером %s не найден!", objectId));
                }
                DomainObject domainObject = domainObjectOptional.get();
                order.setDomainObject(domainObject);
            }
            case IN_WORK -> {
                //TODO: Делать ли проверку на ресторан?
                order.setStatus(OrderStatus.IN_WORK);
            }
            case WAITING_TO_PICKUP -> {
                order.setStatus(OrderStatus.WAITING_TO_PICKUP);
            }
            case DELIVERING -> {
                if (!Objects.isNull(order.getAssignedOn())) {
                    log.error("Order with id {} is already assigned by {}", id, order.getAssignedOn());
                    throw new AlreadyAssignedException(String.format("Заказ с номером %s уже взять другим пользователем!", id));
                }
                Optional<DomainUser> userOptional = domainUserRepository.findByEmail(email);
                if (userOptional.isEmpty()) {
                    log.error("User with email {} is not found!", email);
                    throw new UserNotFoundException(String.format("Пользователь с email %s не найден", email));
                }
                DomainUser user = userOptional.get();
                order.setAssignedOn(user.getEmail());
                order.setStatus(OrderStatus.DELIVERING);
            }
            case CANCELED -> {
                order.setStatus(OrderStatus.CANCELED);
            }
            case DELIVERED -> {
                order.setStatus(OrderStatus.DELIVERED);
            }
            default -> {
                log.error("Can not save status with title {} to order with id {}", orderStatusRequest.getStatusTitle(), id);
                throw new IllegalStatusException(String.format("Нельзя присвоить статус %s к заказу!", orderStatusRequest.getStatusTitle()));
            }
        }
        order.setStatus(status);
        orderRepository.save(order);
        return ModelMapperUtil.map(order, DomainOrderDto.class);
    }

    public DomainOrderDto confirmOrder(Long id) {
        DomainOrder order = findOrderById(id);
        order.setStatus(OrderStatus.OPENED);
        orderRepository.save(order);
        return ModelMapperUtil.map(order, DomainOrderDto.class);
    }

    private DomainOrder createOrder(OrderRequest request, String email, Meal meal) {
        Long restaurantId = meal.getMealCategory().getRestaurant().getId();
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            throw new RestaurantNotFoundException(String.format("Ресторан с номером %s не найден", restaurantId));
        }
        Restaurant restaurant = restaurantOptional.get();

        DomainOrder domainOrder = DomainOrder.builder()
                .createdBy(email)
                .restaurant(restaurant)
                .meals(List.of(meal))
                .status(OrderStatus.CREATED)
                .build();
        orderRepository.save(domainOrder);
        return domainOrder;
    }

    private DomainOrder addMeal(DomainOrder order, Meal meal) {
        List<Meal> meals = order.getMeals();
        meals.add(meal);
        order.setMeals(meals);
        orderRepository.save(order);
        return order;
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
