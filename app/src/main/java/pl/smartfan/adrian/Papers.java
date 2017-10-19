package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Piece of paper class.
 */

public class Papers {
    //private Matrix matrix = new Matrix();

    //coordinates & angle
    private int x, y;
    //private float angle = 0;

    //random shelf
    private int shelf;

    //speed level
    private int leveledSpeed = 1;

    //creating a rect object
    private Rect detectCollision;

    private Bitmap bitmap;
    private Random rand = new Random();
    private int screenX, screenY;

    public Papers(Context context, int maxX, int maxY) {
        screenX = maxX;
        screenY = maxY;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ball);

        //shelf = rand.nextInt(4);

        shelf = calculateShelf();

        //paper should appear on random shelf
        switch (shelf) { // TODO: 18.10.2017 duplicate papers on one shelf, fix that
            case 0:
                x = (screenX / 100) * 10;
                y = (screenY / 100) * 25;
                break;
            case 1:
                x = (screenX / 100) * 90;
                y = (screenY / 100) * 25;
                break;
            case 2:
                x = (screenX / 100) * 10;
                y = (screenY / 100) * 45;
                break;
            case 3:
                x = (screenX / 100) * 90;
                y = (screenY / 100) * 45;
                break;
        }

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update() {
        //update coordinates to make papers move
        int speed;
        int dropMultiplier = 3;
        int shelfSlopingDegree = 1;
        switch (shelf) {
            case 0:
                speed = calculateSpeed();
                //simulate shelf edge (paper drop)
                if (x > ((screenX / 100) * 30)) {
                    y += speed * dropMultiplier;
                } else {
                    x += speed;
                    y += shelfSlopingDegree;
                }
                break;
            case 1:
                speed = calculateSpeed();

                if (x < ((screenX / 100) * 70)) {
                    y += speed * dropMultiplier;
                } else {
                    x -= speed;
                    y += shelfSlopingDegree;
                }
                break;
            case 2:
                speed = calculateSpeed();

                if (x > ((screenX / 100) * 30)) {
                    y += speed * dropMultiplier;
                } else {
                    x += speed;
                    y += shelfSlopingDegree;
                }
                break;
            case 3:
                speed = calculateSpeed();

                if (x < ((screenX / 100) * 70)) {
                    y += speed * dropMultiplier;
                } else {
                    x -= speed;
                    y += shelfSlopingDegree;
                }
                break;
        }

        //angle += 0.001f;

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public int calculateSpeed() {
        int randSpeed = rand.nextInt(5) + leveledSpeed;
        return randSpeed;
    }

    private int calculateShelf() {
/*        int shelfsNumber = 4;
        Set<Integer> shelfGenerated = new LinkedHashSet<>();

        while (shelfGenerated.size() < shelfsNumber) {
            Integer next =
        }*/
/*        int shelfCalc = shelf;
        if (shelfCalc < 3) {
            shelfCalc++;
        } else {
            shelfCalc = 0;
        }*/

        int shelfCalc = rand.nextInt(4);

        return shelfCalc;
    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    //getters
    public Bitmap getBitmap() {
        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, false); //bitmap scaling
        //matrix.reset();
        //matrix.postRotate(angle);
        //bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public void setSpeed(int speed) {
        this.leveledSpeed = speed;
    }

    public int getY() {
        return y;
    }
}
