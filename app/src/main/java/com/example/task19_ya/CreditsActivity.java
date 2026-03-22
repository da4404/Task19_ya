package com.example.task19_ya;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * @author Darya 
 * @version 1.0
 * @since 23/03/2026
 * This activity displays the credits information for the application.
 */
public class CreditsActivity extends AppCompatActivity {

    /**
     * Initializes the credits activity.
     * <p>
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

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
            finish();
            return true;
        }
        else if (id == R.id.menu_settings) {
            Intent intent = new Intent(this, setting.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.menu_credits) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}