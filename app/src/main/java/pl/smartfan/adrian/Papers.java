package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Piece of paper class.
 */

public class Papers {
    //last shelf int
    private static int lastShelf;
    //speed level
    private static int leveledSpeed = 1; // TODO: 10.11.2017 make it faster with new levels (modified, test it)
    //initiate matrix
    private Matrix matrix = new Matrix();
    //coordinates & angle
    private float angle = 0;
    private int x, y;
    //random shelf
    private int shelf;
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
        switch (shelf) {
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
                angle += 5f + speed;
                break;
            case 1:
                speed = calculateSpeed();

                if (x < ((screenX / 100) * 70)) {
                    y += speed * dropMultiplier;
                } else {
                    x -= speed;
                    y += shelfSlopingDegree;
                }
                angle -= 5f + speed;
                break;
            case 2:
                speed = calculateSpeed();

                if (x > ((screenX / 100) * 30)) {
                    y += speed * dropMultiplier;
                } else {
                    x += speed;
                    y += shelfSlopingDegree;
                }
                angle += 5f + speed;
                break;
            case 3:
                speed = calculateSpeed();

                if (x < ((screenX / 100) * 70)) {
                    y += speed * dropMultiplier;
                } else {
                    x -= speed;
                    y += shelfSlopingDegree;
                }
                angle -= 5f + speed;
                break;
        }

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public int calculateSpeed() {
        return rand.nextInt(5) + leveledSpeed;
    }

    //get random shelf number and not duplicate last shelf
    private int calculateShelf() {
        List<Integer> shelfCollection = new ArrayList<>();
        if (shelfCollection.size() == 0) {
            shelfCollection.addAll(Arrays.asList(0, 1, 2, 3));
        }
        Collections.shuffle(shelfCollection);

        while (shelfCollection.get(0) == lastShelf) {
            Collections.shuffle(shelfCollection);
        }

        int shelfCalc = shelfCollection.get(0);
        shelfCollection.remove(0);

        lastShelf = shelfCalc;

        return shelfCalc;
    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    //getters

    public Bitmap getBitmap() {
        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, false); //bitmap scaling
        matrix.reset();
        //matrix.setTranslate();
        matrix.postRotate(angle);
        // TODO: 10.11.2017 research setSinCos method
        //matrix.setSinCos(angle, angle / 2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public int getX() {
        return x;
    }

    public void setSpeed(int speed) {
        leveledSpeed = speed;
    }

    public int getY() {
        return y;
    }
}
