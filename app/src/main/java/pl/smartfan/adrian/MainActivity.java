package pl.smartfan.adrian;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer backgroundMusicMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fade in game title
        ImageView titleImage = (ImageView) findViewById(R.id.titleImage);
        Animation titleFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        titleImage.startAnimation(titleFadeInAnimation);

        //play background music
        backgroundMusicMain = MediaPlayer.create(this, R.raw.splash_music);
        backgroundMusicMain.start();
    }

    public void startButton(View v) {
        //starting game activity
        startActivity(new Intent(this, GameActivity.class));
    }

    public void highScoreButton(View v) {
        //starting high score activity
        startActivity(new Intent(this, HighScoreActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusicMain.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundMusicMain.start();
    }
}
