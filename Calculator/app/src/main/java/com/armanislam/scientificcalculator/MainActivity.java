package com.armanislam.scientificcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvDisplay;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean waitingForOperand = false;

    // Memory variables
    private double memoryValue = 0;
    private boolean memoryHasValue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        // Number buttons
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);

        // Operator buttons
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnSubtract).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnPower).setOnClickListener(this);
        findViewById(R.id.btnEquals).setOnClickListener(this);

        // Scientific buttons
        findViewById(R.id.btnSin).setOnClickListener(this);
        findViewById(R.id.btnCos).setOnClickListener(this);
        findViewById(R.id.btnTan).setOnClickListener(this);
        findViewById(R.id.btnLog).setOnClickListener(this);
        findViewById(R.id.btnLn).setOnClickListener(this);
        findViewById(R.id.btnSqrt).setOnClickListener(this);
        findViewById(R.id.btnPi).setOnClickListener(this);
        findViewById(R.id.btnE).setOnClickListener(this);

        // Memory buttons
        findViewById(R.id.btnMPlus).setOnClickListener(this);
        findViewById(R.id.btnMMinus).setOnClickListener(this);
        findViewById(R.id.btnMR).setOnClickListener(this);
        findViewById(R.id.btnMC).setOnClickListener(this);

        // Other buttons
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnDel).setOnClickListener(this);
        findViewById(R.id.btnParen).setOnClickListener(this);
        findViewById(R.id.btnConvert).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "C":
                currentInput = "";
                operator = "";
                firstOperand = 0;
                waitingForOperand = false;
                tvDisplay.setText("0");
                break;

            case "DEL":
                if (currentInput.length() > 0) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    tvDisplay.setText(currentInput.isEmpty() ? "0" : currentInput);
                }
                break;

            case "=":
                if (!operator.isEmpty() && !waitingForOperand) {
                    double secondOperand = Double.parseDouble(currentInput);
                    double result = performCalculation(firstOperand, secondOperand, operator);
                    currentInput = String.valueOf(result);
                    tvDisplay.setText(currentInput);
                    operator = "";
                    waitingForOperand = true;
                }
                break;

            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
                if (!currentInput.isEmpty()) {
                    firstOperand = Double.parseDouble(currentInput);
                    operator = buttonText;
                    waitingForOperand = true;
                }
                break;

            case "sin":
            case "cos":
            case "tan":
            case "log":
            case "ln":
            case "√":
                if (!currentInput.isEmpty()) {
                    double operand = Double.parseDouble(currentInput);
                    double result = performUnaryOperation(operand, buttonText);
                    currentInput = String.valueOf(result);
                    tvDisplay.setText(currentInput);
                    waitingForOperand = true;
                }
                break;

            case "π":
                currentInput = String.valueOf(Math.PI);
                tvDisplay.setText(currentInput);
                waitingForOperand = true;
                break;

            case "e":
                currentInput = String.valueOf(Math.E);
                tvDisplay.setText(currentInput);
                waitingForOperand = true;
                break;

            case "()":
                currentInput += "(";
                tvDisplay.setText(currentInput);
                break;

            case "UNIT CONVERTER":
                startActivity(new Intent(this, UnitConverterActivity.class));
                break;

            // Memory operations
            case "M+":
                if (!currentInput.isEmpty()) {
                    double currentValue = Double.parseDouble(currentInput);
                    memoryValue += currentValue;
                    memoryHasValue = true;
                    // Show memory indicator
                    tvDisplay.setText(currentInput + " M+");
                }
                break;

            case "M-":
                if (!currentInput.isEmpty()) {
                    double currentValue = Double.parseDouble(currentInput);
                    memoryValue -= currentValue;
                    memoryHasValue = true;
                    // Show memory indicator
                    tvDisplay.setText(currentInput + " M-");
                }
                break;

            case "MR":
                if (memoryHasValue) {
                    currentInput = String.valueOf(memoryValue);
                    tvDisplay.setText(currentInput);
                    waitingForOperand = true;
                }
                break;

            case "MC":
                memoryValue = 0;
                memoryHasValue = false;
                tvDisplay.setText("0");
                break;

            default:
                if (waitingForOperand) {
                    currentInput = "";
                    waitingForOperand = false;
                }
                currentInput += buttonText;
                tvDisplay.setText(currentInput);
                break;
        }
    }

    private double performCalculation(double first, double second, String op) {
        switch (op) {
            case "+": return first + second;
            case "-": return first - second;
            case "*": return first * second;
            case "/": return first / second;
            case "^": return Math.pow(first, second);
            default: return second;
        }
    }

    private double performUnaryOperation(double operand, String op) {
        switch (op) {
            case "sin": return Math.sin(operand);
            case "cos": return Math.cos(operand);
            case "tan": return Math.tan(operand);
            case "log": return Math.log10(operand);
            case "ln": return Math.log(operand);
            case "√": return Math.sqrt(operand);
            default: return operand;
        }
    }
}