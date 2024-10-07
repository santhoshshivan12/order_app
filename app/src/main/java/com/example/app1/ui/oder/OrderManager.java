package com.example.app1.ui.oder;

import java.util.ArrayList;
import java.util.List;


    public class OrderManager {
        private static OrderManager instance;
        private List<CartItem> orders;


        private OrderManager() {
            orders = new ArrayList<>();
        }

        public static synchronized OrderManager getInstance() {
            if (instance == null) {
                instance = new OrderManager();
            }
            return instance;
        }

        public List<CartItem> getOrders() {
            return orders;
        }

        public void addOrder(CartItem item) {
            orders.add(item);
        }



        public void clearOrders() {
            orders.clear();
        }
    }

