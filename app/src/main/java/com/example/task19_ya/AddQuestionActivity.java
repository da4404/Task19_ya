package com.example.task19_ya;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * @author Darya 
 * @version 1.0
 * @since 23/03/2026
 * This activity allows users to add their own custom questions to the quiz game.
 */
public class AddQuestionActivity extends AppCompatActivity
{
    EditText etQ, etA1, etA2, etA3, etA4;


    /**
     * Initializes the activity and its input fields for question and answers.
     * <p>
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        etQ = findViewById(R.id.ed_typ_question);
        etA1 = findViewById(R.id.et_answer1);
        etA2 = findViewById(R.id.et_answer2);
        etA3 = findViewById(R.id.et_answer3);
        etA4 = findViewById(R.id.et_answer4);
    }



    /**
     * Inflates the main menu items from the resource file.
     * <p>
     *
     * @param menu The options menu in which you place your items.
     * @return true to display the menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Handles selection of items from the options menu.
     * <p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_game) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_settings) {
            Intent intent = new Intent(this, setting.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_credits)
        {
            Intent intent = new Intent(this, CreditsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Saves the user-provided question and its answers to a local file.
     * <p>
     *
     * @param view The View component that was clicked.
     */
    public void saveQuestion(View view)
    {
        String q = etQ.getText().toString().replace(";", ",");
        String a1 = etA1.getText().toString().replace(";", ",");
        String a2 = etA2.getText().toString().replace(";", ",");
        String a3 = etA3.getText().toString().replace(";", ",");
        String a4 = etA4.getText().toString().replace(";", ",");

        if (q.isEmpty() || a1.isEmpty() || a2.isEmpty() || a3.isEmpty() || a4.isEmpty())
        {
            Toast.makeText(this, "שגיאה: חובה למלא את השאלה ואת כל התשובות!", Toast.LENGTH_LONG).show();
        }
        else
        {
            String fullLine = q + ";" + a1 + ";" + a2 + ";" + a3 + ";" + a4 + "\n";

            try
            {
                java.io.FileOutputStream fOS = openFileOutput("personal_questions.txt", MODE_APPEND);
                java.io.OutputStreamWriter oSW = new java.io.OutputStreamWriter(fOS);
                java.io.BufferedWriter bW = new java.io.BufferedWriter(oSW);
                bW.write(fullLine);
                bW.close();
                oSW.close();
                fOS.close();

                Toast.makeText(this, "השאלה האישית נשמרה במאגר!", Toast.LENGTH_SHORT).show();
                etQ.setText("");
                etA1.setText("");
                etA2.setText("");
                etA3.setText("");
                etA4.setText("");

            }
            catch (Exception e)
            {
                Toast.makeText(this, "שגיאה בשמירת הקובץ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
