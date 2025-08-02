package com.tdebnath.cookingrobot;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    // A simple class to hold ingredient data
    private static class Ingredient {
        String name;
        double baseAmount; // Amount for 1 person
        String unit;

        Ingredient(String name, double baseAmount, String unit) {
            this.name = name;
            this.baseAmount = baseAmount;
            this.unit = unit;
        }
    }

    // UI Elements
    private TextView recipeTitleTextView;
    private EditText personCountInput;
    private TableLayout ingredientsTable;
    private Button backButton;
    private Button nextButton;
    private TextView chefsTipContent;

    private String currentMenuTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // Find all the views from our layout file
        recipeTitleTextView = findViewById(R.id.recipe_title);
        personCountInput = findViewById(R.id.person_count_input);
        ingredientsTable = findViewById(R.id.ingredients_table);
        backButton = findViewById(R.id.button_back);
        nextButton = findViewById(R.id.button_next);
        chefsTipContent = findViewById(R.id.chefs_tip_content);

        // Get the menu title passed from MainActivity
        currentMenuTitle = getIntent().getStringExtra("MENU_TITLE");

        // Set the screen title, e.g., "Hotchpotch Preparation"
        String title = getString(R.string.preparation_title_template, currentMenuTitle);
        recipeTitleTextView.setText(title);

        // Display the initial recipe for 1 person
        updateRecipe(1);

        // Set up the listener for the person count input field
        personCountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // When the text changes, update the recipe table
                try {
                    int personCount = Integer.parseInt(s.toString());
                    if (personCount >= 1 && personCount <= 9) { // Limit to 1-9 persons
                        updateRecipe(personCount);
                    }
                } catch (NumberFormatException e) {
                    // If the box is empty, clear the table or show default
                    updateRecipe(0); // Passing 0 will clear the table
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });

        // Set up the listener for the Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finishes this activity and returns to the previous one (MainActivity)
                finish();
            }
        });

        // Set up listener for the Next button (placeholder)
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // In the future, this would open the cooking steps screen.
                // For now, we just show a message.
                Toast.makeText(RecipeActivity.this, "Cooking steps coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Updates the ingredients table based on the number of people.
     * @param personCount The number of people to cook for.
     */
    private void updateRecipe(int personCount) {
        // First, remove all rows except the header row (which is at index 0)
        while (ingredientsTable.getChildCount() > 1) {
            ingredientsTable.removeView(ingredientsTable.getChildAt(1));
        }

        if (personCount == 0) {
            return; // If input is empty or 0, just show an empty table.
        }

        List<Ingredient> ingredients = getIngredientsForMenu(currentMenuTitle);
        String chefsTip = getChefsTipForMenu(currentMenuTitle);
        chefsTipContent.setText(chefsTip);

        // Dynamically create and add a new row for each ingredient
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);

            // Calculate the new amount
            double newAmount = ingredient.baseAmount * personCount;

            // Create a new row
            TableRow row = new TableRow(this);

            // Create TextViews for the row's columns
            TextView tvSerial = createTextView(String.valueOf(i + 1), true);
            TextView tvName = createTextView(ingredient.name, false);
            // Use String.format to handle double values cleanly
            TextView tvAmount = createTextView(String.format("%.0f", newAmount), false, Gravity.END);
            TextView tvUnit = createTextView(ingredient.unit, false, Gravity.START);

            // Add the TextViews to the row
            row.addView(tvSerial);
            row.addView(tvName);
            row.addView(tvAmount);
            row.addView(tvUnit);

            // Add the new row to the table
            ingredientsTable.addView(row);
        }
    }

    /**
     * A helper method to create TextViews for our table cells, to avoid repeating code.
     */
    private TextView createTextView(String text, boolean isBold, int gravity) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        textView.setTextColor(ContextCompat.getColor(this, R.color.button_text_color));
        textView.setGravity(gravity);
        if (isBold) {
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
        }
        return textView;
    }
    // Overloaded method for default gravity
    private TextView createTextView(String text, boolean isBold) {
        return createTextView(text, isBold, Gravity.START);
    }


    /**
     * This method acts as our "database". It returns the list of ingredients
     * for a given menu title.
     */
    private List<Ingredient> getIngredientsForMenu(String menuTitle) {
        List<Ingredient> ingredients = new ArrayList<>();
        if (menuTitle == null) return ingredients;

        switch (menuTitle) {
            case "Hotchpotch":
                ingredients.add(new Ingredient("Basmati Rice", 250, "gm"));
                ingredients.add(new Ingredient("Moong Dal", 80, "gm"));
                ingredients.add(new Ingredient("Salt", 1, "tsp"));
                ingredients.add(new Ingredient("Mustard Oil", 25, "ml"));
                ingredients.add(new Ingredient("Onion (Medium)", 2, "pcs"));
                ingredients.add(new Ingredient("Green Chili", 3, "pcs"));
                ingredients.add(new Ingredient("Garlic", 5, "cloves"));
                break;
            case "Noodles":
                ingredients.add(new Ingredient("Noodle Cake", 1, "pack"));
                ingredients.add(new Ingredient("Carrot", 0.5, "pcs"));
                ingredients.add(new Ingredient("Capsicum", 0.5, "pcs"));
                ingredients.add(new Ingredient("Soy Sauce", 1, "tbsp"));
                ingredients.add(new Ingredient("Vegetable Oil", 2, "tbsp"));
                break;
            case "Vegetables":
                ingredients.add(new Ingredient("Mixed Veggies", 300, "gm"));
                ingredients.add(new Ingredient("Turmeric", 1, "tsp"));
                ingredients.add(new Ingredient("Cumin Powder", 1, "tsp"));
                ingredients.add(new Ingredient("Oil", 3, "tbsp"));
                break;
            case "Fried Rice":
                ingredients.add(new Ingredient("Cooked Rice", 2, "cup"));
                ingredients.add(new Ingredient("Chopped Carrots", 0.5, "cup"));
                ingredients.add(new Ingredient("Chopped Beans", 0.5, "cup"));
                ingredients.add(new Ingredient("Soy Sauce", 2, "tbsp"));
                ingredients.add(new Ingredient("Vinegar", 1, "tbsp"));
                break;
        }
        return ingredients;
    }

    private String getChefsTipForMenu(String menuTitle) {
        if (menuTitle == null) return "No tips available.";
        switch (menuTitle) {
            case "Hotchpotch":
                return "For better taste, roast the Moong Dal until a light golden brown before cooking.";
            case "Noodles":
                return "Don't overcook the noodles. They should be firm to the bite (al dente).";
            case "Vegetables":
                return "Stir-fry the vegetables on high heat to keep them crunchy and fresh.";
            case "Fried Rice":
                return "Day-old, refrigerated rice works best as it is drier and less likely to get mushy.";
            default:
                return "Always taste and adjust seasoning before serving!";
        }
    }
}
