package com.plainpixel.finalapplication;

public class WalletItem {
    private String transcationCode;
    private String orderDate;
    private Integer totalOrderCost;

    public WalletItem(String transcationCode, String orderDate, Integer totalOrderCost)
    {
        this.transcationCode=transcationCode;
        this.orderDate = orderDate;
        this.totalOrderCost = totalOrderCost;
    }

    public String getTranscationCode()
    {
        return transcationCode;
    }

    public void setTranscationCode(String transcationCode)
    {
        this.transcationCode = transcationCode;
    }

    public String getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(String orderDate)
    {
        this.orderDate = orderDate;
    }

    public Integer getTotalOrderCost()
    {
        return totalOrderCost;
    }

    public void setTotalOrderCost(Integer totalOrderCost)
    {
        this.totalOrderCost = totalOrderCost;
    }

}
