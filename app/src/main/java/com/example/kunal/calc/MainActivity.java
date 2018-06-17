package com.example.kunal.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperator;

    //Variables to store inputs
    private Double operand1 = null;
    private String pendingOperation = "=";
    private final String state_pendingOperation="pendingOperation";
    private final String state_operand1="operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        displayOperator = findViewById(R.id.operation);
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonNEG = findViewById(R.id.buttonNEG);
        Button buttonAdd = findViewById(R.id.add);
        Button buttonSub = findViewById(R.id.sub);
        Button buttonMul = findViewById(R.id.mul);
        Button buttonDiv = findViewById(R.id.div);
        Button buttonDec = findViewById(R.id.dec);
        Button buttonEq = findViewById(R.id.eq);
        Button buttonDEL = findViewById(R.id.buttonDEL);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button pressed =(Button) view;
                newNumber.append(pressed.getText().toString());

                }
        };
        View.OnClickListener negListen = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousText=newNumber.getText().toString();
                if(previousText.length()==0){
                    newNumber.setText("-");
                }
                else{
                    if(previousText.equals("-")){
                        newNumber.setText("");
                    }
                    else {
                        Double value = Double.valueOf(previousText);
                        value *= -1;
                        newNumber.setText(value.toString());
                    }
                }

            }
        };
        View.OnClickListener delListen = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousText=newNumber.getText().toString();
                if(previousText.length()!=0&&previousText.length()!=1){
                    newNumber.setText(previousText.substring(0,previousText.length()-1));
                }
                else{
                    newNumber.setText("");
                }
            }
        };
        View.OnLongClickListener clearListen = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                newNumber.setText("");
                result.setText("");
                operand1=null;
                pendingOperation="=";
                displayOperator.setText("");
                return false;
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDec.setOnClickListener(listener);
        buttonNEG.setOnClickListener(negListen);
        buttonDEL.setOnClickListener(delListen);
        buttonDEL.setOnLongClickListener(clearListen);


        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button pressed = (Button) view;
                String op = pressed.getText().toString();
                String value = newNumber.getText().toString();
                if (value.length() != 0) {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        Operation(doubleValue, op);

                    } catch (NumberFormatException e) {
                        newNumber.setText("");
                    }
                }
                pendingOperation = op;
                displayOperator.setText(pendingOperation);
            }
        };

        buttonAdd.setOnClickListener(opListener);
        buttonSub.setOnClickListener(opListener);
        buttonMul.setOnClickListener(opListener);
        buttonDiv.setOnClickListener(opListener);
        buttonEq.setOnClickListener(opListener);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString(state_pendingOperation,pendingOperation);
        if(operand1!=null){
            outState.putDouble(state_operand1,operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        pendingOperation=savedInstanceState.getString(state_pendingOperation);
        displayOperator.setText(pendingOperation);
        operand1=savedInstanceState.getDouble(state_operand1);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void Operation(Double value, String operation) {
        if (operand1 == null) {
            operand1 = (value);
        } else {
            if (operation.equals("=")) {
                operation = pendingOperation;
            }
            switch (operation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");

    }
}