package com.example.task19_ya;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Darya 
 * @version 1.0
 * @since 23/03/2026
 * The main activity of the quiz game, handling question loading, display, and scoring.
 */
public class MainActivity extends AppCompatActivity
{
    ArrayList<String> questionsList = new ArrayList<>();
    TextView tvQuestion, tvDetails;
    Button btnA1, btnA2, btnA3, btnA4;
    int currentQuestionIndex = 0;
    String currentCorrectAnswer;
    int currentScore = 0;
    int highScore = 0;
    String userName = "";

    /**
     * Initializes the activity, loads questions, and sets up the UI components.
     * <p>
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadQuestionsFromRaw();
        loadPersonalQuestions();
        tvQuestion = findViewById(R.id.tV_question);
        tvDetails = findViewById(R.id.details);
        btnA1 = findViewById(R.id.bT_answer1);
        btnA2 = findViewById(R.id.bT_answer2);
        btnA3 = findViewById(R.id.bT_answer3);
        btnA4 = findViewById(R.id.bT_answer4);
        displayQuestion();
        android.content.SharedPreferences sp = getSharedPreferences("QuizMasterPrefs", MODE_PRIVATE);
        highScore = sp.getInt("HighScore", 0);
        userName = sp.getString("UserName", "אורח");
        tvDetails.setText("שחקן: " + userName + " | ניקוד: " + currentScore + " | שיא: " + highScore);
    }

    /**
     * Displays the current question and its answers in shuffled order.
     * <p>
     *
     */
    public void displayQuestion()
    {
        if (questionsList.size() > 0)
        {
            String fullLine = questionsList.get(currentQuestionIndex);
            String[] parts = fullLine.split(";");
            if (parts.length >= 5) {
                tvQuestion.setText(parts[0]);
                currentCorrectAnswer = parts[1];
                String[] answers = new String[4];
                answers[0] = parts[1];
                answers[1] = parts[2];
                answers[2] = parts[3];
                answers[3] = parts[4];
                Random rnd = new Random();
                for (int i = 0; i < answers.length; i++) {
                    int randomIndex = rnd.nextInt(answers.length);
                    String temp = answers[i];
                    answers[i] = answers[randomIndex];
                    answers[randomIndex] = temp;
                }
                btnA1.setText(answers[0]);
                btnA2.setText(answers[1]);
                btnA3.setText(answers[2]);
                btnA4.setText(answers[3]);
            }
        }
    }

    /**
     * Loads the initial questions from the raw resource text file.
     * <p>
     *
     */
    public void loadQuestionsFromRaw()
    {
        try
        {
            int resourceId = this.getResources().getIdentifier("questions", "raw", this.getPackageName());
            InputStream iS = this.getResources().openRawResource(resourceId);
            InputStreamReader iSR = new InputStreamReader(iS);
            BufferedReader bR = new BufferedReader(iSR);
            String line = bR.readLine();
            while (line != null)
            {
                if (!line.trim().isEmpty())
                {
                    questionsList.add(line);
                }
                line = bR.readLine();
            }
            bR.close();
            iSR.close();
            iS.close();

        }
        catch (Exception e)
        {
            Toast.makeText(this, "שגיאה בטעינת שאלות המערכת", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Loads personal questions added by the user from internal storage.
     * <p>
     *
     */
    public void loadPersonalQuestions()
    {
        try
        {
            java.io.FileInputStream fIS = openFileInput("personal_questions.txt");
            java.io.InputStreamReader iSR = new java.io.InputStreamReader(fIS);
            java.io.BufferedReader bR = new java.io.BufferedReader(iSR);
            String line = bR.readLine();
            while (line != null)
            {
                if (!line.trim().isEmpty())
                {
                    questionsList.add(line);
                }
                line = bR.readLine();
            }
            bR.close();
            iSR.close();
            fIS.close();

        } catch (Exception e)
        {
        }
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
            return true;
        }
        else if (id == R.id.menu_settings) {
            Intent intent = new Intent(this, setting.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.menu_credits) {
            Intent intent = new Intent(this, CreditsActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Validates the user's selected answer, updates the score and progress.
     * <p>
     *
     * @param view The View component (button) that was clicked.
     */
    public void checkAnswer(View view)
    {
        if (currentCorrectAnswer == null)
        {
            Toast.makeText(this, "אין שאלות במאגר!", Toast.LENGTH_SHORT).show();
            return;
        }
        Button clickedButton = (Button) view;
        String answerSelected = clickedButton.getText().toString();
        if (answerSelected.equals(currentCorrectAnswer))
        {
            currentScore++;
            if (currentScore > highScore)
            {
                highScore = currentScore;
                android.content.SharedPreferences sp = getSharedPreferences("QuizMasterPrefs", MODE_PRIVATE);
                android.content.SharedPreferences.Editor editor = sp.edit();
                editor.putInt("HighScore", highScore);
                editor.commit();
            }
            Toast.makeText(this, "כל הכבוד! תשובה נכונה", Toast.LENGTH_SHORT).show();
        } else
        {
            Toast.makeText(this, "טעות! התשובה היא: " + currentCorrectAnswer, Toast.LENGTH_SHORT).show();
        }
        tvDetails.setText("שחקן: " + userName + " | ניקוד: " + currentScore + " | שיא: " + highScore);
        currentQuestionIndex++;
        if (currentQuestionIndex < questionsList.size())
        {
            displayQuestion();
        }
        else
        {
            Toast.makeText(this, "המשחק הסתיים!", Toast.LENGTH_LONG).show();
            btnA1.setEnabled(false);
            btnA2.setEnabled(false);
            btnA3.setEnabled(false);
            btnA4.setEnabled(false);
        }
    }

    /**
     * Navigates the user to the activity where they can add personal questions.
     * <p>
     *
     * @param view The View component that triggered the action.
     */
    public void addPersonalQuestion(View view)
    {
        Intent intent = new Intent(this, AddQuestionActivity.class);
        startActivity(intent);
        finish();
    }
}
