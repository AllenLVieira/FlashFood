package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.OrderNotFoundException;
import br.com.allen.flashfood.domain.model.*;
import br.com.allen.flashfood.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OrderRegistrationService {

    private final OrderRepository orderRepository;
    private final RestaurantRegistrationService restaurantService;
    private final CityRegistrationService cityService;
    private final UserRegistrationsService userService;
    private final ProductRegistrationService productService;
    private final PaymentMethodRegistrationService paymentMethodService;

    @Transactional
    public DeliveryOrder createOrder(DeliveryOrder order) {
        validateOrder(order);
        validateItems(order);

        order.setFreightRate(order.getRestaurant().getFreightRate());
        order.calculateTotalAmount();
        return orderRepository.save(order);
    }

    private void validateItems(DeliveryOrder order) {
        order.getItems().forEach(item -> {
            Product product = productService.findProductOrElseThrow(order.getRestaurant().getId(),
                    item.getProduct().getId());

            item.setOrder(order);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());
        });
    }

    private void validateOrder(DeliveryOrder order) {
        City city = cityService.findCityOrElseThrow(order.getDeliveryAddress().getCity().getId());
        User user = userService.findUserOrElseThrow(order.getUser().getId());
        Restaurant restaurant = restaurantService.findRestaurantOrElseThrow(order.getRestaurant().getId());
        PaymentMethod paymentMethod = paymentMethodService.
                findPaymentMethodOrElseThrow(order.getPaymentMethod().getId());

        order.getDeliveryAddress().setCity(city);
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setPaymentMethod(paymentMethod);

        if (restaurant.doesNotAcceptPaymentMethod(paymentMethod)) {
            throw new BusinessException(String.format("Payment method '%s' is not accepted by this restaurant.",
                    paymentMethod.getDescription()));
        }
    }

    public DeliveryOrder findOrderOrElseThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
