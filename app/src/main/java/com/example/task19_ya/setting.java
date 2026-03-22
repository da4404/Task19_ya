package com.example.task19_ya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
 * This class handles the settings of the application, such as updating the user's name and resetting the high score.
 */
public class setting extends AppCompatActivity
{
    SharedPreferences settings;
    EditText etUserName;
    Button btnSaveName;

    /**
     * Initializes the settings activity and its UI components.
     * <p>
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        etUserName = findViewById(R.id.et_userName);
        btnSaveName = findViewById(R.id.bt_save_userName);
        settings = getSharedPreferences("QuizMasterPrefs", MODE_PRIVATE);
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
            return true;
        }
        else if (id == R.id.menu_credits) {
            Intent intent = new Intent(this, CreditsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Saves the username entered by the user into SharedPreferences.
     * <p>
     *
     * @param view The View component that was clicked.
     */
    public void saveName(View view)
    {
        String name = etUserName.getText().toString();
        if (name.isEmpty())
        {
            Toast.makeText(this, "חובה להקליד שם משתמש", Toast.LENGTH_LONG).show();
        }
        else
        {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("UserName", name);
            editor.commit();
            Toast.makeText(this, "השם נשמר", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Resets the high score stored in SharedPreferences to zero.
     * <p>
     *
     * @param view The View component that was clicked.
     */
    public void deleteScore(View view)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("HighScore", 0);
        editor.commit();
        Toast.makeText(this, "שיא הנקודות אופס", Toast.LENGTH_SHORT).show();
    }
}