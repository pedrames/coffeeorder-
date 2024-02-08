package com.example.afinal;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private TextView orderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderTextView = findViewById(R.id.order_text_view);

        List<OrderItem> orderList = getIntent().getParcelableArrayListExtra("orderList");
        displayOrder(orderList);
    }

    private void displayOrder(List<OrderItem> orderList) {
        StringBuilder orderBuilder = new StringBuilder();

        for (OrderItem orderItem : orderList) {
            orderBuilder.append("Item: ").append(orderItem.getItemName()).append("\n");
            orderBuilder.append("Quantity: ").append(orderItem.getQuantity()).append("\n");
            orderBuilder.append("Price: ").append(orderItem.getPrice()).append("\n");
            orderBuilder.append("Subtotal: ").append(orderItem.getSubtotal()).append("\n\n");
        }

        orderTextView.setText(orderBuilder.toString());
    }
}
