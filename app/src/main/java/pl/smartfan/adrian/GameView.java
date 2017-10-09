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
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    public static boolean signedLawPaper, paperVisibility, userStarted = false;
    //adding paper to this class
    public ArrayList<Papers> papers;
    volatile boolean playing;
    //context
    Context context;
    private Thread gameThread = null;
    //adding the player to this class
    private President president;
    //adding second character to this class
    //private SecondChar secondChar;
    //bitmap array number
    private int bitmapNumber = 0;
    //floor rect
    private Rect floor;
    //current number of papers
    private int currentPapersCount;
    //max number of papers
    private int maxPapersCount = 4;
    //These objects will be used for drawing
    private TextPaint paint;
    private Canvas canvas;
    //private TextPaint textPaint;
    private SurfaceHolder surfaceHolder;
    //max display values
    private int maxX, maxY;
    //for random numbers
    private Random rand = new Random();

    //background image
    private Bitmap backgroundImage = BitmapFactory.decodeResource(getResources(), R.mipmap.splash_screen);

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        //set context
        this.context = context;

        //max screen dimensions
        maxX = screenX;
        maxY = screenY;

        //initializing player object
        president = new President(context, screenX, screenY);

        //initializing second character object
        //secondChar = new SecondChar(context);

        //initializing array list
        papers = new ArrayList<>();

        //add papers objects
        addPapers(true);

        //initializing law paper object
        //lawPaper = new LawPaper(context);

        //initializing floor object
        floor = new Rect(0, (maxY / 100) * 85, maxX, (maxY / 100) * 85 + 1);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new TextPaint();
        paint.setTextSize(100); //set text size
        paint.setColor(Color.RED);
    }

    //add papers objects method
    public void addPapers(boolean ignition) {
        //put papers objects to array list (app just started or not dependant)
        if (ignition) {
            for (int i = 0; i < maxPapersCount; i++) {
                papers.add(new Papers(context, maxX, maxY));
                currentPapersCount++;
            }
        } else if (!ignition) {
            for (int i = 0; i < currentPapersCount; i++) {
                papers.add(new Papers(context, maxX, maxY));
            }
        }

    }

    //Reacts for touch press and release
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                //When the user touch the screen
                userStarted = true;
                paperVisibility = true;
                //signedLawPaper = lawPaper.getX() <= 750;
                //player movement to corners
                if (motionEvent.getRawX() < (maxX / 100) * 50 && motionEvent.getRawY() < (maxY / 100) * 50) {
                    bitmapNumber = 0;
                } else if (motionEvent.getRawX() > (maxX / 100) * 50 && motionEvent.getRawY() < (maxY / 100) * 50) {
                    bitmapNumber = 1;
                } else if (motionEvent.getRawX() < (maxX / 100) * 50 && motionEvent.getRawY() > (maxY / 100) * 50) {
                    bitmapNumber = 2;
                } else if (motionEvent.getRawX() > (maxX / 100) * 50 && motionEvent.getRawY() > (maxY / 100) * 50) {
                    bitmapNumber = 3;
                } // TODO: 04.10.2017 make above code simpler 
                draw();
                break;
            /*case MotionEvent.ACTION_DOWN:
                //When the user releases the touch
                bitmapNumber = 0;
                draw();
                break;*/
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
        for (int i = 0; i < currentPapersCount; i++) {
            papers.get(i).update();

            //if collision occurs with player or floor
            if (Rect.intersects(president.getDetectCollision(), papers.get(i).getDetectCollision()) || Rect.intersects(floor, papers.get(i).getDetectCollision())) {
                //remove from ArrayList
                papers.remove(i);
                --currentPapersCount;
            }
        }

        //if there is no papers, add new, rand number of papers
        if (currentPapersCount < maxPapersCount) {
            currentPapersCount++;
            addPapers(false);
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
            /*canvas.drawBitmap(
                    secondChar.getBitmap(bitmapNumber),
                    secondChar.getX(),
                    secondChar.getY(),
                    paint);*/
            for (int i = 0; i < currentPapersCount; i++) {
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
                    --currentPapersCount;

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
