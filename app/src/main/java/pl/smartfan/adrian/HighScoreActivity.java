package pl.smartfan.adrian;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Class responsible for displaying High Score.
 */

public class HighScoreActivity extends AppCompatActivity { // TODO: 07.11.2017 create high score

    TextView highScoreText1, highScoreText2, highScoreText3, highScoreText4, highScoreText5, highScoreText6, highScoreText7, highScoreText8;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        //initialize textViews
        highScoreText1 = (TextView) findViewById(R.id.highScoreText1);
        highScoreText2 = (TextView) findViewById(R.id.highScoreText2);
        highScoreText3 = (TextView) findViewById(R.id.highScoreText3);
        highScoreText4 = (TextView) findViewById(R.id.highScoreText4);
        highScoreText5 = (TextView) findViewById(R.id.highScoreText5);
        highScoreText6 = (TextView) findViewById(R.id.highScoreText6);
        highScoreText7 = (TextView) findViewById(R.id.highScoreText7);
        highScoreText8 = (TextView) findViewById(R.id.highScoreText8);

        //initializing high score array & shared preferences
        sharedPreferences = this.getSharedPreferences("ADRIAN_HIGH_SCORE", Context.MODE_PRIVATE);

        //set high scores values to textViews
        highScoreText1.setText(String.valueOf(sharedPreferences.getInt("score1", 0)));
        highScoreText2.setText(String.valueOf(sharedPreferences.getInt("score2", 0)));
        highScoreText3.setText(String.valueOf(sharedPreferences.getInt("score3", 0)));
        highScoreText4.setText(String.valueOf(sharedPreferences.getInt("score4", 0)));
        highScoreText5.setText(String.valueOf(sharedPreferences.getInt("score5", 0)));
        highScoreText6.setText(String.valueOf(sharedPreferences.getInt("score6", 0)));
        highScoreText7.setText(String.valueOf(sharedPreferences.getInt("score7", 0)));
        highScoreText8.setText(String.valueOf(sharedPreferences.getInt("score8", 0)));

    }
}
