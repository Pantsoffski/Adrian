package pl.smartfan.adrian;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Class responsible for displaying High Score.
 */

public class HighScoreActivity extends AppCompatActivity {

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
        highScoreText1.setText(getString(R.string.high_score_number, 1, sharedPreferences.getInt("score1", 0)));
        highScoreText2.setText(getString(R.string.high_score_number, 2, sharedPreferences.getInt("score2", 0)));
        highScoreText3.setText(getString(R.string.high_score_number, 3, sharedPreferences.getInt("score3", 0)));
        highScoreText4.setText(getString(R.string.high_score_number, 4, sharedPreferences.getInt("score4", 0)));
        highScoreText5.setText(getString(R.string.high_score_number, 5, sharedPreferences.getInt("score5", 0)));
        highScoreText6.setText(getString(R.string.high_score_number, 6, sharedPreferences.getInt("score6", 0)));
        highScoreText7.setText(getString(R.string.high_score_number, 7, sharedPreferences.getInt("score7", 0)));
        highScoreText8.setText(getString(R.string.high_score_number, 8, sharedPreferences.getInt("score8", 0)));
    }
}
