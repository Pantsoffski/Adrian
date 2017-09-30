package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    public static boolean signedLawPaper, paperVisibility, userStarted = false;
    //adding paper to this class
    public ArrayList<Papers> papers;
    volatile boolean playing;
    private Thread gameThread = null;
    //adding the player to this class
    private President president;
    //adding second character to this class
    private SecondChar secondChar;
    //bitmap array number
    private int bitmapNumber = 0;

    //number of papers
    private int papersCount = 4;

    //These objects will be used for drawing
    private TextPaint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    //private TextPaint textPaint;

    //max display values
    private int maxX, maxY;

    //background image
    private Bitmap backgroundImage = BitmapFactory.decodeResource(getResources(), R.mipmap.splash_screen);

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        //initializing player object
        president = new President(context);

        //initializing second character object
        secondChar = new SecondChar(context);

        //initializing array list
        papers = new ArrayList<>();

        //put papers objects to array list
        for (int i = 0; i < papersCount; i++) {
            papers.add(new Papers(context, screenX, screenY));
        }

        //max screen dimensions
        maxX = screenX;
        maxY = screenY;

        //initializing law paper object
        //lawPaper = new LawPaper(context);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new TextPaint();
        paint.setTextSize(100); //set text size
        paint.setColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                //When the user presses on the screen
                bitmapNumber = 0;
                userStarted = true;
                paperVisibility = true;
                //signedLawPaper = lawPaper.getX() <= 750;
                draw();
                break;
            case MotionEvent.ACTION_DOWN:
                //When the user releases the screen
                bitmapNumber = 1;
                draw();
                break;
        }
        return true;
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        //updating player position
        president.update();
        //papers movement
        for (int i = 0; i < papersCount; i++) {
            papers.get(i).update();
        }

        for (int i = 0; i < papersCount; i++) {
            //if collision occurs with player
            if (Rect.intersects(president.getDetectCollision(), papers.get(i).getDetectCollision())) {
                //moving enemy outside the left edge
                papers.get(i).setX(-250);
            }
        }
    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background image for canvas
            //canvas.drawBitmap(backgroundImage, 0, 0, null);
            canvas.drawColor(Color.WHITE);

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
            for (int i = 0; i < papersCount; i++) {
                //make sure coordinates are not larger than screen dimension and is not negative
                if (maxX > papers.get(i).getX() && maxY > papers.get(i).getY() && papers.get(i).getX() >= 0) {
                    //Drawing papers
                    canvas.drawBitmap(
                            papers.get(i).getBitmap(),
                            papers.get(i).getX(),
                            papers.get(i).getY(),
                            paint);
                } else {
                    papers.remove(i);
                    --papersCount;

                }
            }

            canvas.drawText("Score: " + "25", 10, 100, paint);
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
