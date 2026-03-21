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

public class setting extends AppCompatActivity
{
    SharedPreferences settings;
    EditText etUserName;
    Button btnSaveName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        etUserName = findViewById(R.id.et_userName);
        btnSaveName = findViewById(R.id.bt_save_userName);
        settings = getSharedPreferences("QuizMasterPrefs", MODE_PRIVATE);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
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
        else if (id == R.id.menu_credits) {
            Intent intent = new Intent(this, CreditsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

    public void deleteScore(View view)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("HighScore", 0);
        editor.commit();
        Toast.makeText(this, "שיא הנקודות אופס", Toast.LENGTH_SHORT).show();
    }
}