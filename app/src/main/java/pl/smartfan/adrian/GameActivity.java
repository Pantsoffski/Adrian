package pl.smartfan.adrian;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    //declaring gameView
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initializing game view object
        gameView = new GameView(this);

        //adding it to contentView
        setContentView(gameView);
    }
}
