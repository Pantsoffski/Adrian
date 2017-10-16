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
    private int shelf; // TODO: 09.10.2017 disable duplicate shelf/paper effect 

    //creating a rect object
    private Rect detectCollision;

    private Bitmap bitmap;
    private Random rand = new Random();
    private int screenX, screenY;

    public Papers(Context context, int maxX, int maxY) {
        screenX = maxX;
        screenY = maxY;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ball);

        shelf = rand.nextInt(4);
/*        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }
        Collections.shuffle(list);*/

        /*if (shelf < 3) {
            shelf++;
        } else {
            shelf = 0;
        }*/

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
        int speed = rand.nextInt(10) + 1;
        return speed;
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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
}
