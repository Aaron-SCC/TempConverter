package com.example.tempconverter;

import androidx.appcompat.app.AppCompatActivity;

//import android.os.Bundle;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class MainActivity extends AppCompatActivity
        implements OnEditorActionListener {

//    <TextView
//    android:id="@+id/textViewFahrenheit"
//
//    <TextView
//    android:id="@+id/textViewCelsius"
//
//    <TextView//
//    android:id="@+id/textViewConvertedDegrees"//
//
//    <TextView
//    android:id="@+id/textViewDegrees"
//
//    <EditText//
//    android:id="@+id/editTextUserTemp"//


    // define variables for the widgets
    private EditText editTextUserTemp;
//    private TextView percentTextView;
//    private Button   percentUpButton;
//    private Button   percentDownButton;
//    private TextView tipTextView;
    private TextView textViewConvertedDegrees;

    // define the SharedPreferences object
    private SharedPreferences savedValues;

    // define instance variables that should be saved
    private String billAmountString = "";
    private float tipPercent = .15f;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the widgets
        editTextUserTemp = (EditText) findViewById(R.id.editTextUserTemp);
//        percentTextView = (TextView) findViewById(R.id.percentTextView);
//        percentUpButton = (Button) findViewById(R.id.percentUpButton);
//        percentDownButton = (Button) findViewById(R.id.percentDownButton);
//        tipTextView = (TextView) findViewById(R.id.tipTextView);
        textViewConvertedDegrees = (TextView) findViewById(R.id.textViewConvertedDegrees);

        // set the listeners
        editTextUserTemp.setOnEditorActionListener(this);
//        percentUpButton.setOnClickListener(this);
//        percentDownButton.setOnClickListener(this);

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = savedValues.edit();
        editor.putString("billAmountString", billAmountString);
        editor.putFloat("tipPercent", tipPercent);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        billAmountString = savedValues.getString("billAmountString", "");
        tipPercent = savedValues.getFloat("tipPercent", 0.15f);

        // set the bill amount on its widget
        editTextUserTemp.setText(billAmountString);

        // calculate and display
        calculateAndDisplay();
    }

    public void calculateAndDisplay() {

        // get the bill amount
        billAmountString = editTextUserTemp.getText().toString();
        float billAmount;
        if (billAmountString.equals("")) {
            billAmount = 0;
        }
        else {
            billAmount = Float.parseFloat(billAmountString);
        }

        // calculate tip and total
        float tipAmount = billAmount * tipPercent;
        double totalAmount = ( billAmount - 32.0 )*( 5.0/9.0 ) ; // F to C formula

        // display the other results with formatting
        NumberFormat currency = NumberFormat.getCurrencyInstance();
//        DecimalFormat temp = DecimalFormat.getInstance();
//        tipTextView.setText(currency.format(tipAmount));
        textViewConvertedDegrees.setText(currency.format(totalAmount));
//        textViewConvertedDegrees.setText(temp.format(totalAmount));
//        double textViewConvertedDegrees = Math.round(totalAmount * 100) / 100.0;

//        NumberFormat percent = NumberFormat.getPercentInstance();
//        percentTextView.setText(percent.format(tipPercent));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }
        return false;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.percentDownButton:
//                tipPercent = tipPercent - .01f;
//                calculateAndDisplay();
//                break;
//            case R.id.percentUpButton:
//                tipPercent = tipPercent + .01f;
//                calculateAndDisplay();
//                break;
//        }
//    }


}
