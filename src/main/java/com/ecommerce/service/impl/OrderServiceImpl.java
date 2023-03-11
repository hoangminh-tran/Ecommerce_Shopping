package com.ecommerce.service.impl;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderDetailsRepository;
import com.ecommerce.repository.OrdersRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.OrderService;
import com.fasterxml.jackson.core.JsonToken;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    @Override
    public Orders save(Orders dto) {
        return ordersRepository.save(new Orders(dto.getOrdDate(), dto.getShipDate(), dto.getOrder_Status(), dto.getUser()));
    }

    @Override
    public List<Orders> findListOrdersByUsername(String username) {
        return ordersRepository.findListOrdersByUsername(username);
    }

    @Override
    public List<Orders> findListOrdersByUsernameAndStatus(String name, int status) {
        return ordersRepository.findListOrdersByUsernameAndStatus(name, status);
    }

    @Override
    public void updateStatusOrders(int status, Long order_id) {
        ordersRepository.updateStatusOrders(status, order_id);
    }

    @Override
    public List<Orders> findOrdersByDate(Date Date_from, Date Date_to) {
        return ordersRepository.findOrdersByDate(Date_from, Date_to);
    }

    @Override
    public List<OrderDTO> findAllOrderToManage() {
        List<Orders> orders = ordersRepository.findAll();
        List<OrderDTO> list = new ArrayList<>();
        for(Orders order : orders)
        {
            User customer = userRepository.findById(order.getUser().getUser_id()).get();
            if(customer != null)
            {
                String status = "";
                switch (order.getOrder_Status())
                {
                    case 1:
                        status = "Completed Order";
                        break;
                    case 2:
                        status = "Process Order";
                        break;
                    case 3:
                        status = "Cancel Order";
                        break;
                }
                list.add(new OrderDTO(order.getOrder_Id(), order.getOrdDate(), order.getShipDate(),
                        status, customer.getEmail()));
            }
        }
        return list;
    }

    @Override
    public List<OrderDTO> searchOrderByName(String name) {
        List<OrderDTO> orderDTOs = findAllOrderToManage();
        List<OrderDTO> list = new ArrayList<>();
        List<User> list_customer = userRepository.findAllUserByEmailLike(name);
        Set<String> set = new HashSet<>();
        for (User customer : list_customer)
        {
            if(!set.contains(customer.getEmail()))
            {
                set.add(customer.getEmail());
            }
        }
        for(OrderDTO dto : orderDTOs)
        {
            if(set.contains(dto.getEmail()))
            {
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    public List<OrderDTO> searchOrdersByDate(Date Date_from, Date Date_to) {
        List<Orders> orders = findOrdersByDate(Date_from, Date_to);
        List<OrderDTO> list = new ArrayList<>();
        for (Orders order : orders)
        {
            User customer = userRepository.findById(order.getUser().getUser_id()).get();
            String status = "";
            switch (order.getOrder_Status())
            {
                case 1:
                    status = "Completed Order";
                    break;
                case 2:
                    status = "Process Order";
                    break;
                case 3:
                    status = "Cancel Order";
                    break;
            }
            list.add(new OrderDTO(order.getOrder_Id(), order.getOrdDate(), order.getShipDate(), status
                    , customer.getEmail()));
        }
        return list;
    }

    @Override
    public List<Orders> findCustomerOrdersByDate(Date Date_from, Date Date_to, String email) {
        return ordersRepository.findCustomerOrdersByDate(Date_from, Date_to, email);
    }
}
