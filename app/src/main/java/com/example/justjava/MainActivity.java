package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        EditText getCustomerName = findViewById(R.id.customer_name_field);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        boolean hasChocolate = chocolateCheckbox.isChecked();
        String customerName = getCustomerName.getText().toString();
        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String priceMessage = callOrderSummary(customerName, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * Calculates the price of the order.
     *addwhippedCream has cream or  not
     * returns total price
     */
    private int calculatePrice(boolean addChocolate, boolean addWhippedCream) {
        int basePrice = 5;

        if (addChocolate){
            basePrice += 2;
        }

        if (addWhippedCream){
            basePrice += 1;
        }

        return quantity * basePrice;
    }
    private String callOrderSummary(String customerName, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = "Name : " + customerName+ "\n" +
                "Add whipped cream? "+ addWhippedCream +
                "\nAdd chocolate? " + addChocolate +
                "\nQuantity : " + quantity +
                "\nTotal : $" + price + "" +
                "\nThank you!";
        return priceMessage;
    }
    public void increment(View view){
        if(quantity==100){
            //Show error message
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity +=1;
        displayQuantity(quantity);
    }
    public void decrement(View view){
        if (quantity==1){
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }


}