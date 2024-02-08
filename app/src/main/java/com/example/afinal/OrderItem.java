package com.example.afinal;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {
    private String itemName;
    private int quantity;
    private double price;

    public OrderItem(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = calculatePrice();
    }

    private double calculatePrice() {

        double itemPrice = 0.0;
        switch (itemName) {
            case "Latte":
                itemPrice = 2.5;
                break;
            case "Mocha":
                itemPrice = 3.0;
                break;
            case "Americano":
                itemPrice = 2.0;
                break;
        }
        return itemPrice * quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getSubtotal() {
        return quantity * price;
    }

    // Parcelable implementation
    protected OrderItem(Parcel in) {
        itemName = in.readString();
        quantity = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeInt(quantity);
        dest.writeDouble(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return itemName;
    }
}
