package com.plainpixel.finalapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cartitem implements Parcelable {
    private String itemName;
    private String itemPrice;
    private String itemImage;
    private String quantity;

    private int totalcost;
    private String timetaken;


    public Cartitem(String itemName, String itemPrice, String itemImage, String quantity, int totalcost, String timetaken)
    {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.quantity = quantity;
        this.totalcost = totalcost;
        this.timetaken = timetaken;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemPrice);
        dest.writeString(itemImage);
        dest.writeString(quantity);
        dest.writeInt(totalcost);
        dest.writeString(timetaken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Cartitem> CREATOR = new Parcelable.Creator<Cartitem>() {
        @Override
        public Cartitem createFromParcel(Parcel source) {
            return new Cartitem(source);
        }

        @Override
        public Cartitem[] newArray(int size) {
            return new Cartitem[size];
        }
    };

    public String getItemName()
    {
        return itemName;
    }

    public String getItemPrice()
    {
        return itemPrice;
    }

    public String getItemImage()
    {
        return itemImage;
    }

    public String getQuantity(){return quantity;}

    public int getTotalcost(){return totalcost;}

    public String getTimetaken() {return timetaken;}

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
    public void setTotalcost(int totalcost)
    {
        this.totalcost = totalcost;
    }
    protected Cartitem (Parcel in)
    {
        itemName = in.readString();
        itemPrice = in.readString();
        itemImage = in.readString();
        quantity = in.readString();
        totalcost = in.readInt();
        timetaken = in.readString();
    }

}
