package org.example.order_service.service;

import org.example.order_service.dao.OrderDao;
import org.example.order_service.dto.OrderInit;
import org.example.order_service.entity.DeliveryFee;
import org.example.order_service.entity.Invoice;
import org.example.order_service.entity.Order;
import org.example.order_service.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Component
public class OrderUtilService {



    @Autowired
    private OrderDao orderDao;


    @Transactional
    public Order createOrder(OrderInit orderInit) {

        AtomicReference<Double> totalProductCost = new AtomicReference<>(0.0);

        List<Product> products = orderInit.getItems().stream().map(it -> {
            double productCost = it.getProductCost() * it.getQty();
            totalProductCost.updateAndGet(v -> v + productCost);

            return Product
                    .builder()
                    .productCost(it.getProductCost())
                    .productName(it.getProductName())
                    .productQty(it.getQty())
                    .productImage(it.getProductImage())
                    .build();
        }).toList();

        double payable = orderInit.getInvoice().getOffer()
                + orderInit.getInvoice().getHandlingCost()
                + orderInit.getInvoice().getPlatformFee()
                + totalProductCost.get()
                + orderInit.getInvoice().getDeliveryFee()
                + orderInit.getInvoice().getTip();

        Order order = Order
                .builder()
                .referenceNo(orderInit.getReferenceId())
                .darkStoreId(orderInit.getDarkStoreId())
                .customerId(orderInit.getCustomerId())
                .products(products)
                .invoice(Invoice
                        .builder()
                        .offer(orderInit.getInvoice().getOffer())
                        .handlingCost(orderInit.getInvoice().getHandlingCost())
                        .platformFee(orderInit.getInvoice().getPlatformFee())
                        .totalProductCost(totalProductCost.get())
                        .deliveryFee(
                                DeliveryFee
                                        .builder()
                                        .deliveryFee(orderInit.getInvoice().getDeliveryFee())
                                        .tip(orderInit.getInvoice().getTip())
                                        .build()
                        )
                        .payable(payable)
                        .build()
                )
                .build();


        orderDao.saveOrder(order);

        return order;
    }


}
