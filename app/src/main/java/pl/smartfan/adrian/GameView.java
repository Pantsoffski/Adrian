package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    //is player playing and is game over
    volatile boolean playing, gameOver;
    //context
    Context context;
    private Thread gameThread = null;
    //adding the player to this class
    private President president;
    //adding papers to this class
    private Papers presidentPapers;
    //adding second character to this class
    //private SecondChar secondChar;
    //bitmap array number
    private int bitmapNumber = 0;
    //floor rect and desk (basket) rect
    private Rect floor, desk, deskTopLeft, deskTopRight, deskBottomLeft, deskBottomRight;
    //current number of papers
    private int currentPapersCount;
    //max number of papers
    private int maxPapersCount = 1;
    //These objects will be used for drawing
    private TextPaint paint;
    //private TextPaint textPaint;
    private SurfaceHolder surfaceHolder;
    //max display size values
    private int maxX, maxY;
    //score & level
    private int score = 0;
    private int level = 1;
    private int oldLevel = 0;
    private int levelTimer = 0;
    //number of lives
    private int lives = 3;

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

        //game over set to false
        gameOver = false;

        //initializing player object
        president = new President(context, maxX, maxY);

        //initializing second character object
        //secondChar = new SecondChar(context);

        //initializing array list
        papers = new ArrayList<>();

        //initializing papers object
        presidentPapers = new Papers(context, maxX, maxY);

        //add papers objects
        addPapers(true);

        //initializing floor and desks objects
        floor = new Rect(0, (maxY / 100) * 95, maxX, (maxY / 100) * 95);
        deskTopLeft = new Rect((maxX / 100) * 25, (maxY / 100) * 50, (maxX / 100) * 35, (maxY / 100) * 50); //top left basket
        deskTopRight = new Rect((maxX / 100) * 65, (maxY / 100) * 50, (maxX / 100) * 75, (maxY / 100) * 50); //top right basket
        deskBottomLeft = new Rect((maxX / 100) * 25, (maxY / 100) * 80, (maxX / 100) * 35, (maxY / 100) * 80); //bottom left basket
        deskBottomRight = new Rect((maxX / 100) * 65, (maxY / 100) * 80, (maxX / 100) * 75, (maxY / 100) * 80); //bottom right basket

        //set default basket
        desk = deskTopLeft;


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
        } else {
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
                float motionEventRawX = motionEvent.getRawX();
                float motionEventRawY = motionEvent.getRawY();

                //player movement to corners
                if (motionEventRawX < (maxX / 100) * 50 && motionEventRawY < (maxY / 100) * 50) {
                    bitmapNumber = 0;
                    desk = deskTopLeft; //top left basket
                } else if (motionEventRawX > (maxX / 100) * 50 && motionEventRawY < (maxY / 100) * 50) {
                    bitmapNumber = 1;
                    desk = deskTopRight; //top right basket
                } else if (motionEventRawX < (maxX / 100) * 50 && motionEventRawY > (maxY / 100) * 50) {
                    bitmapNumber = 2;
                    desk = deskBottomLeft; //bottom left basket
                } else if (motionEventRawX > (maxX / 100) * 50 && motionEventRawY > (maxY / 100) * 50) {
                    bitmapNumber = 3;
                    desk = deskBottomRight; //bottom right basket
                }
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
        for (int i = 0; i < currentPapersCount; i++) {
            papers.get(i).update();
            Rect papersCollision = papers.get(i).getDetectCollision();

            //add score if needed
            if (Rect.intersects(desk, papersCollision)) {
                //remove from ArrayList
                papers.remove(i);
                currentPapersCount--;
                score++;

                //add level if score grows
                if (score % 10 == 0) {
                    presidentPapers.setSpeed(2);
                    maxPapersCount += 1;
                    level++;
                }

                continue;
            }

            //if collision occurs with floor
            if (Rect.intersects(floor, papersCollision)) {
                //remove from ArrayList
                papers.remove(i);
                --currentPapersCount;
                --lives;

                //game over if no more lives left
                if (lives < 1) {
                    playing = false;
                    gameOver = true;
                }
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
            Canvas canvas = surfaceHolder.lockCanvas();
            //drawing a background image for canvas
            //canvas.drawBitmap(backgroundImage, 0, 0, null);
            canvas.drawColor(Color.WHITE);

            //Drawing the player
            canvas.drawBitmap(
                    president.getBitmap(bitmapNumber),
                    president.getX(),
                    president.getY(),
                    paint);

            //Drawing the second character // TODO: 20.10.2017 draw second character, put some funny sentences thet he talks to Adrian
            /*canvas.drawBitmap(
                    secondChar.getBitmap(bitmapNumber),
                    secondChar.getX(),
                    secondChar.getY(),
                    paint);*/

            TalkingBubble rect = new TalkingBubble(context, maxX, maxY, "Sample of text", paint);
            canvas.drawRoundRect(rect.getBubble(), 30, 30, paint);

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

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawRect(desk, paint);

            paint.setTextSize(90);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Score: " + score + " Lives: " + lives, 10, 100, paint);

            //draw game over text when the game is over
            if (gameOver) {
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over", canvas.getWidth() / 2, yPos, paint);
            }

            //show level number, use levelTimer variable to count time to show text
            if (oldLevel < level && levelTimer < 100 && !gameOver) {
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Level:" + level, canvas.getWidth() / 2, yPos, paint);
                levelTimer++;

                //reset levelTimer
                if (levelTimer == 100) {
                    oldLevel++;
                    levelTimer = 0;
                }
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
            e.printStackTrace();
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
