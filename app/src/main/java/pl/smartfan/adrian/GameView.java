package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;

    //adding the player to this class
    private President president;

    //adding second character to this class
    private SecondChar secondChar;

    //adding law paper to this class
    private LawPaper lawPaper;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //background image
    private Bitmap backgroundImage = BitmapFactory.decodeResource(getResources(), R.mipmap.splash_screen);

    //Class constructor
    public GameView(Context context) {
        super(context);

        //initializing player object
        president = new President(context);

        //initializing second character object
        secondChar = new SecondChar(context);

        //initializing law paper object
        lawPaper = new LawPaper(context);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                //When the user presses on the screen
                draw(0);
                break;
            case MotionEvent.ACTION_DOWN:
                //When the user releases the screen
                draw(1);
                break;
        }
        return true;
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw(3);
            control();
        }
    }

    private void update() {
        //updating player position
        //president.update();
        lawPaper.update();
    }

    private void draw(int bitmapNumber) {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background image for canvas
            //canvas.drawBitmap(backgroundImage, 0, 0, null);
            canvas.drawColor(Color.WHITE);
            if (bitmapNumber == 3) {
                //Drawing the law paper
                canvas.drawBitmap(
                        lawPaper.getBitmap(),
                        lawPaper.getX(),
                        lawPaper.getY(),
                        paint);
            } else {
                //Drawing the player
                canvas.drawBitmap(
                        president.getBitmap(bitmapNumber),
                        president.getX(),
                        president.getY(),
                        paint);
                //Drawing the second character
                canvas.drawBitmap(
                        secondChar.getBitmap(bitmapNumber),
                        secondChar.getX(),
                        secondChar.getY(),
                        paint);
            }
            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(17); //animation speed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
