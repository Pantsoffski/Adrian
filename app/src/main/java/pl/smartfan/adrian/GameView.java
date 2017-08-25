package pl.smartfan.adrian;

import android.content.Context;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    //Class constructor
    public GameView(Context context) {
        super(context);

        //set background image
        this.setBackgroundResource(R.mipmap.splash_screen);

    }

    @Override
    public void run() {

    }
}
