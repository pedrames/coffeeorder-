package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button placeOrderButton;
    private List<OrderItem> orderList;
    private TextView orderTextView;
    private Button viewOrderButton; // New button reference for "View Order" button

    private static final int REQUEST_CODE_LATTE = 1;
    private static final int REQUEST_CODE_MOCHA = 2;
    private static final int REQUEST_CODE_AMERICANO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placeOrderButton = findViewById(R.id.place_order_button);
        registerForContextMenu(placeOrderButton);

        orderList = new ArrayList<>();
        orderTextView = findViewById(R.id.order_text_view);

        Button viewOrderButton = findViewById(R.id.view_order_button);
        viewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrderDetails();

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_latte:
                startLatteActivity();
                return true;
            case R.id.menu_mocha:
                startMochaActivity();
                return true;
            case R.id.menu_americano:
                startAmericanoActivity();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void startLatteActivity() {
        Intent intent = new Intent(this, LatteActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LATTE);
        updateOrderTextView(); //
    }

    public void startMochaActivity() {
        Intent intent = new Intent(this, MochaActivity.class);
        startActivityForResult(intent, REQUEST_CODE_MOCHA);
    }

    public void startAmericanoActivity() {
        Intent intent = new Intent(this, AmericanoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_AMERICANO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                OrderItem orderItem = (OrderItem) data.getParcelableExtra("orderItem");
                if (orderItem != null) {
                    addOrderItem(orderItem);
                    updateOrderTextView();
                }
            }
        }
    }


    public void addOrderItem(OrderItem orderItem) {
        orderList.add(orderItem);
        updateOrderTextView(); //
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderList.remove(orderItem);
    }

    public void updateOrderTextView() {
        StringBuilder orderDetails = new StringBuilder("Order details:\n");
        double totalPrice = 0;
        double tax = 0;

        for (OrderItem orderItem : orderList) {
            orderDetails.append(orderItem.getName()).append(" (").append(orderItem.getQuantity()).append(")\n");
            totalPrice += orderItem.getQuantity() * orderItem.getPrice();
        }

        tax = totalPrice * 0.15; // Assuming tax rate is 15%
        double totalPriceWithTax = totalPrice + tax;

        orderDetails.append("\nTotal Price: $").append(String.format("%.2f", totalPrice));
        orderDetails.append("\nTax: $").append(String.format("%.2f", tax));
        orderDetails.append("\nTotal Price with Tax: $").append(String.format("%.2f", totalPriceWithTax));
        orderTextView.setText(orderDetails.toString());
    }

    private void showOrderDetails() {
        StringBuilder orderDetails = new StringBuilder("Order details:\n");
        double totalPrice = 0;
        double tax = 0;

        for (OrderItem orderItem : orderList) {
            orderDetails.append("Drink: ").append(orderItem.getName()).append("\n");
            orderDetails.append("Quantity: ").append(orderItem.getQuantity()).append("\n");
            orderDetails.append("Price: $").append(String.format("%.2f", orderItem.getPrice())).append("\n");
            orderDetails.append("Subtotal: $").append(String.format("%.2f", orderItem.getSubtotal())).append("\n");
            orderDetails.append("\n");

            totalPrice += orderItem.getSubtotal();
        }

        tax = calculateTax(totalPrice);
        double totalAmount = totalPrice + tax;

        orderDetails.append("Tax: $").append(String.format("%.2f", tax)).append("\n");
        orderDetails.append("Total Price: $").append(String.format("%.2f", totalPrice)).append("\n");
        orderDetails.append("Total Amount (including tax): $").append(String.format("%.2f", totalAmount));

        Toast.makeText(this, orderDetails.toString(), Toast.LENGTH_SHORT).show();
    }

    private double calculateTax(double amount) {
        // Assuming tax rate of 10%
        double taxRate = 0.1;
        return amount * taxRate;
    }
}