package com.example.afinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AmericanoActivity extends AppCompatActivity {

    private EditText quantityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_americano);

        quantityEditText = findViewById(R.id.quantity_edit_text);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button sendOrderButton = findViewById(R.id.send_order_button);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button cancelOrderButton = findViewById(R.id.cancel_order_button);

        sendOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOrder();
            }
        });

        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder();
            }
        });
    }

    private void sendOrder() {
        int quantity = Integer.parseInt(quantityEditText.getText().toString());
        if (quantity > 0) {
            OrderItem orderItem = new OrderItem("Americano", quantity);
            addOrderItem(orderItem); //
            Intent intent = new Intent();
            intent.putExtra("orderItem", orderItem);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Please enter a valid quantity.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addOrderItem(OrderItem orderItem) {

    }

    private void cancelOrder() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
