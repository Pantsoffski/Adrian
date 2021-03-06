package pl.smartfan.adrian;

import android.content.Context;
import android.content.SharedPreferences;
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

    public static boolean paperVisibility, userStarted = false;
    //adding paper to this class
    public ArrayList<Papers> papers;
    //is player playing and is game over
    volatile boolean playing, gameOver;
    //context
    Context context;
    //shared preferences for high score
    SharedPreferences sharedPreferences;
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
    //talking bubble
    private int talkingBubbleTimer = 1;
    private boolean talkingBubbleIgnition = false;
    private TalkingBubble talkingBubbleRect;
    //number of lives
    private int lives = 3;
    //high score
    private int[] highScore = new int[8];
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

        //initializing high score array & shared preferences
        sharedPreferences = context.getSharedPreferences("ADRIAN_HIGH_SCORE", Context.MODE_PRIVATE);

        highScore[0] = sharedPreferences.getInt("score1", 0);
        highScore[1] = sharedPreferences.getInt("score2", 0);
        highScore[2] = sharedPreferences.getInt("score3", 0);
        highScore[3] = sharedPreferences.getInt("score4", 0);
        highScore[4] = sharedPreferences.getInt("score4", 0);
        highScore[5] = sharedPreferences.getInt("score5", 0);
        highScore[6] = sharedPreferences.getInt("score6", 0);
        highScore[7] = sharedPreferences.getInt("score7", 0);
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
            talkingBubbleTimer++;
        }
    }

    private void update() {
        //updating player position
        president.update();

        if (!gameOver) {
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
                        level++;
                        presidentPapers.setSpeed(level); //use level value to increase speed
                        maxPapersCount += 1;
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

                        //put high score to array (if it's  greater)
                        for (int it = 0; it < 8; it++) {
                            if (highScore[it] < score) {
                                highScore[it] = score;
                                break;
                            }
                        }

                        //storing the scores through shared Preferences
                        SharedPreferences.Editor e = sharedPreferences.edit();
                        for (int it = 0; it < 8; it++) {
                            int j = it + 1;
                            e.putInt("score" + j, highScore[it]);
                        }
                        e.apply();
                    }
                }

                //talking bubble ignition
                if (talkingBubbleTimer % 400 == 0) {
                    talkingBubbleRect = new TalkingBubble(context, maxX, maxY);
                    talkingBubbleIgnition = true;
                } else if (talkingBubbleTimer % 200 == 0) {
                    talkingBubbleIgnition = false;
                }
            }

            //if there is no papers, add new, rand number of papers
            if (currentPapersCount < maxPapersCount) {
                currentPapersCount++;
                addPapers(false);
            }
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

            //Drawing the second character // TODO: 20.10.2017 draw second character, put some funny sentences that he talks to Adrian
            /*canvas.drawBitmap(
                    secondChar.getBitmap(bitmapNumber),
                    secondChar.getX(),
                    secondChar.getY(),
                    paint);*/

            //drawing talking bubble
            if (talkingBubbleIgnition) {
                canvas.drawRect(talkingBubbleRect.getBubble(), paint);
                canvas.save();
                canvas.translate(talkingBubbleRect.getBubble().left, talkingBubbleRect.getBubble().top);
                talkingBubbleRect.getText().draw(canvas);
                canvas.restore();
            }

            //draw papers
            for (int i = 0; i < currentPapersCount; i++) {
                //make sure coordinates are not larger than screen dimension and is not negative
                if (maxX > papers.get(i).getX() && maxY > papers.get(i).getY() && papers.get(i).getX() >= 0) {
                    //Drawing papers
                    canvas.drawBitmap(
                            papers.get(i).getBitmap(),
                            //get bitmap coordinates not with top let corner but with center of bitmap, to avoid papers jumps
                            papers.get(i).getX() - papers.get(i).getBitmap().getWidth() / 2,
                            papers.get(i).getY() - papers.get(i).getBitmap().getHeight() / 2,
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
            canvas.drawText(context.getString(R.string.score) + " " + score + " " + context.getString(R.string.lives) + " " + lives, 10, 100, paint);

            //draw game over text when the game is over
            if (gameOver) {
                paint.setTextSize(150);

                int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                int xPos = (int) ((canvas.getWidth() / 2) - (paint.measureText(context.getString(R.string.game_over)) / 2));
                canvas.drawText(context.getString(R.string.game_over), xPos, yPos, paint);
            }

            //show level number, use levelTimer variable to count time to show text
            if (oldLevel < level && levelTimer < 100 && !gameOver) {
                paint.setTextSize(150);

                int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                int xPos = (int) ((canvas.getWidth() / 2) - (paint.measureText(context.getString(R.string.level)) / 2));
                canvas.drawText(context.getString(R.string.level) + " " + level, xPos, yPos, paint);
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
