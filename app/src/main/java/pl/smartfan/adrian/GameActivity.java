package pl.smartfan.adrian;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    //declaring gameView
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);

        //Initializing game view object and passing screen size
        gameView = new GameView(this, size.x, size.y); // TODO: 03.10.2017 make buttons appear on SurfaceView 

        //adding it to contentView
        setContentView(gameView);
    }

    public void buttonTest(View view) {
        Log.i("buttonTest: ", "no i działa");
    }

    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}
