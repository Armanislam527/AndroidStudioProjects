package com.armanislam.scientificcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class UnitConverterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerCategory, spinnerFromUnit, spinnerToUnit;
    private EditText etInputValue;
    private TextView tvOutputValue;
    private Button btnConvert, btnBack;

    private Map<String, String[]> unitCategories = new HashMap<>();
    private String currentCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);
        etInputValue = findViewById(R.id.etInputValue);
        tvOutputValue = findViewById(R.id.tvOutputValue);
        btnConvert = findViewById(R.id.btnConvert);
        btnBack = findViewById(R.id.btnBack);

        setupUnitCategories();
        setupSpinners();

        btnConvert.setOnClickListener(v -> convertUnits());
        btnBack.setOnClickListener(v -> finish());
    }

    private void setupUnitCategories() {
        unitCategories.put("Length", new String[]{"Meter", "Kilometer", "Centimeter", "Inch", "Foot", "Mile"});
        unitCategories.put("Weight", new String[]{"Kilogram", "Gram", "Pound", "Ounce", "Ton"});
        unitCategories.put("Temperature", new String[]{"Celsius", "Fahrenheit", "Kelvin"});
        unitCategories.put("Volume", new String[]{"Liter", "Milliliter", "Gallon", "Cup", "Fluid Ounce"});
        unitCategories.put("Area", new String[]{"Square Meter", "Square Kilometer", "Acre", "Hectare"});
    }

    private void setupSpinners() {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                unitCategories.keySet().toArray(new String[0]));

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
        spinnerCategory.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentCategory = parent.getItemAtPosition(position).toString();
        updateUnitSpinners();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void updateUnitSpinners() {
        String[] units = unitCategories.get(currentCategory);
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, units);

        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromUnit.setAdapter(unitAdapter);
        spinnerToUnit.setAdapter(unitAdapter);
    }

    private void convertUnits() {
        String inputStr = etInputValue.getText().toString();
        if (inputStr.isEmpty()) return;

        double inputValue = Double.parseDouble(inputStr);
        String fromUnit = spinnerFromUnit.getSelectedItem().toString();
        String toUnit = spinnerToUnit.getSelectedItem().toString();

        double result = convert(inputValue, fromUnit, toUnit, currentCategory);
        tvOutputValue.setText(String.valueOf(result));
    }

    private double convert(double value, String from, String to, String category) {
        // Conversion logic for each category
        switch (category) {
            case "Length":
                return convertLength(value, from, to);
            case "Weight":
                return convertWeight(value, from, to);
            case "Temperature":
                return convertTemperature(value, from, to);
            case "Volume":
                return convertVolume(value, from, to);
            case "Area":
                return convertArea(value, from, to);
            default:
                return value;
        }
    }

    private double convertLength(double value, String from, String to) {
        // Convert to meters first
        double meters = 0;
        switch (from) {
            case "Meter": meters = value; break;
            case "Kilometer": meters = value * 1000; break;
            case "Centimeter": meters = value / 100; break;
            case "Inch": meters = value * 0.0254; break;
            case "Foot": meters = value * 0.3048; break;
            case "Mile": meters = value * 1609.34; break;
        }

        // Convert from meters to target unit
        switch (to) {
            case "Meter": return meters;
            case "Kilometer": return meters / 1000;
            case "Centimeter": return meters * 100;
            case "Inch": return meters / 0.0254;
            case "Foot": return meters / 0.3048;
            case "Mile": return meters / 1609.34;
            default: return meters;
        }
    }

    private double convertWeight(double value, String from, String to) {
        // Convert to kilograms first
        double kg = 0;
        switch (from) {
            case "Kilogram": kg = value; break;
            case "Gram": kg = value / 1000; break;
            case "Pound": kg = value * 0.453592; break;
            case "Ounce": kg = value * 0.0283495; break;
            case "Ton": kg = value * 1000; break;
        }

        // Convert from kg to target unit
        switch (to) {
            case "Kilogram": return kg;
            case "Gram": return kg * 1000;
            case "Pound": return kg / 0.453592;
            case "Ounce": return kg / 0.0283495;
            case "Ton": return kg / 1000;
            default: return kg;
        }
    }

    private double convertTemperature(double value, String from, String to) {
        // Convert to Celsius first
        double celsius = 0;
        switch (from) {
            case "Celsius": celsius = value; break;
            case "Fahrenheit": celsius = (value - 32) * 5/9; break;
            case "Kelvin": celsius = value - 273.15; break;
        }

        // Convert from Celsius to target unit
        switch (to) {
            case "Celsius": return celsius;
            case "Fahrenheit": return (celsius * 9/5) + 32;
            case "Kelvin": return celsius + 273.15;
            default: return celsius;
        }
    }

    private double convertVolume(double value, String from, String to) {
        // Convert to liters first
        double liters = 0;
        switch (from) {
            case "Liter": liters = value; break;
            case "Milliliter": liters = value / 1000; break;
            case "Gallon": liters = value * 3.78541; break;
            case "Cup": liters = value * 0.236588; break;
            case "Fluid Ounce": liters = value * 0.0295735; break;
        }

        // Convert from liters to target unit
        switch (to) {
            case "Liter": return liters;
            case "Milliliter": return liters * 1000;
            case "Gallon": return liters / 3.78541;
            case "Cup": return liters / 0.236588;
            case "Fluid Ounce": return liters / 0.0295735;
            default: return liters;
        }
    }

    private double convertArea(double value, String from, String to) {
        // Convert to square meters first
        double sqMeters = 0;
        switch (from) {
            case "Square Meter": sqMeters = value; break;
            case "Square Kilometer": sqMeters = value * 1000000; break;
            case "Acre": sqMeters = value * 4046.86; break;
            case "Hectare": sqMeters = value * 10000; break;
        }

        // Convert from sqMeters to target unit
        switch (to) {
            case "Square Meter": return sqMeters;
            case "Square Kilometer": return sqMeters / 1000000;
            case "Acre": return sqMeters / 4046.86;
            case "Hectare": return sqMeters / 10000;
            default: return sqMeters;
        }
    }
}