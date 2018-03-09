package com.example.android.harrypotterquizapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * Saving state before rotation.
     */
    @Override
    public void onSaveInstanceState(final Bundle bundle) {

        // Saving answer on the first question only
        // State of the checkboxes and radiogrups are handled by Android
        TextView questionOneInput = findViewById(R.id.questionOneInput);
        String questionOneUser = questionOneInput.getText().toString();
        bundle.putString("questionOneUser", questionOneUser);
        super.onSaveInstanceState(bundle);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Restoration of the first answer after scree rotation.
        if (savedInstanceState != null) {
            String questionOneNull = savedInstanceState.getString("questionOneUser");
            TextView questionOneInput = findViewById(R.id.questionOneInput);
            questionOneInput.setText(questionOneNull);
        }

        //Floating "Reset" button logic.
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reset = getString(R.string.reset);
                String action_message = getString(R.string.action_message);
                Snackbar.make(view, reset, Snackbar.LENGTH_LONG)
                        .setAction(action_message, null).show();

                // First question reset
                String questionOneNull = "";
                TextView questionOneInput = findViewById(R.id.questionOneInput);
                questionOneInput.setText(questionOneNull);

                // Second question reset
                CheckBox tom = findViewById(R.id.tom);
                tom.setChecked(false);
                CheckBox john = findViewById(R.id.john);
                john.setChecked(false);
                CheckBox marvolo = findViewById(R.id.marvolo);
                marvolo.setChecked(false);

                // Third question reset
                CheckBox lily = findViewById(R.id.lily);
                lily.setChecked(false);
                CheckBox liliana = findViewById(R.id.liliana);
                liliana.setChecked(false);
                CheckBox george = findViewById(R.id.george);
                george.setChecked(false);
                CheckBox james = findViewById(R.id.james);
                james.setChecked(false);

                // Fourth question reset
                RadioGroup fourthRadioGroup = findViewById(R.id.fourthQuestion);
                fourthRadioGroup.clearCheck();

                // Fifth question reset
                RadioGroup fifthRadioGroup = findViewById(R.id.fifthQuestion);
                fifthRadioGroup.clearCheck();
            }
        });
    }

    /**
     * Calculating and displaying result of the quiz via Toast message.
     */
    public void getResult(View view) {

        // Initial number of right answers.
        // Initializing here to prevent additional checks
        // in case of accidental double-clicks on "Get result" button.
        int showResult = 0;

        // Correct answer on the first question
        String questionOneCorrect = getString(R.string.first_q);

        // First question check
        EditText inputText = findViewById(R.id.questionOneInput);
        String questionOneUser = inputText.getText().toString();
        if (questionOneUser.equals(questionOneCorrect)) {
            showResult = showResult + 1;
        }

        // Second question
        // Correct answer: Tom and Marvolo
        CheckBox tom = findViewById(R.id.tom);
        CheckBox john = findViewById(R.id.john);
        CheckBox marvolo = findViewById(R.id.marvolo);
        if (tom.isChecked()) {
            if (marvolo.isChecked()) {
                showResult = showResult + 1;
                if (john.isChecked()) {
                    showResult = showResult - 1;
                }
            }
        }

        // Third question
        // Correct answer: Lily and James
        CheckBox lily = findViewById(R.id.lily);
        CheckBox liliana = findViewById(R.id.liliana);
        CheckBox george = findViewById(R.id.george);
        CheckBox james = findViewById(R.id.james);
        if (lily.isChecked()) {
            if (james.isChecked()) {
                showResult = showResult + 1;
                if (liliana.isChecked()) {
                    showResult = showResult - 1;
                } else if (george.isChecked()) {
                    showResult = showResult - 1;
                }
            }
        }

        // Fourth question
        // Correct answer: Minerva
        RadioButton minerva = findViewById(R.id.minerva);
        if (minerva.isChecked()) {
            showResult = showResult + 1;
        }
        // Fifth question
        // Correct answer: Bellatrix Lestrange
        RadioButton bellatrix = findViewById(R.id.bellatrix);
        if (bellatrix.isChecked()) {
            showResult = showResult + 1;
        }

        // Toast message with results
        // Getting text from strings
        String result_first = getString(R.string.result_first);
        String result_second_singular = getString(R.string.result_second_singular);
        String result_second_plural = getString(R.string.result_second_plural);

        // If only one correct answer, we use "answer" in singular form
        // else - in plural.
        // We may need more options here in the future in case of localization!
        if (showResult == 1) {
            Toast.makeText(this, result_first + " "
                    + showResult + " " + result_second_singular, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, result_first + " "
                    + showResult + " " + result_second_plural, Toast.LENGTH_LONG).show();
        }
    }
}
