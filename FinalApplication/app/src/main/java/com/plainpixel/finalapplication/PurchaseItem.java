package com.plainpixel.finalapplication;

public class PurchaseItem {

    private String Itemname;
    private String itemCost;

    private String itemQuantity;
   // private boolean orderReady;



    public PurchaseItem(String Itemname, String itemCost, String itemQuantity) {
        this.Itemname = Itemname;
        this.itemCost = itemCost;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return Itemname;
    }

    public String getItemCost() {
        return itemCost;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    /**public boolean isOrderReady()
    {
        return orderReady;
    }

    public void setOrderReady(boolean orderReady)
    {
        this.orderReady = orderReady;
    }**/

}
