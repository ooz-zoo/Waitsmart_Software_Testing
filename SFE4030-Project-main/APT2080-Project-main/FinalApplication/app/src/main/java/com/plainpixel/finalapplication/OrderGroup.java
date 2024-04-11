package com.plainpixel.finalapplication;

import java.util.ArrayList;
import java.util.List;

public class OrderGroup {

    private String transcationCode;
    private String orderDate;
    private List<Integer> itemTotalCost;

    public OrderGroup(String transcationCode, String orderDate) {
        this.transcationCode = transcationCode;
        this.orderDate = orderDate;
        this.itemTotalCost = new ArrayList<>();
}

    public String getTranscationCode() {
        return transcationCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public List<Integer> getItemTotalCost() {
        return itemTotalCost;
    }

    public void addItemTotalCost(int totalCost) {
       itemTotalCost.add(totalCost);
    }

    public int getTotalOrderCost()
    {
        int totalCost = 0;
        for (int cost : itemTotalCost)
        {
            totalCost += cost;
        }
        return  totalCost;
    }
}


