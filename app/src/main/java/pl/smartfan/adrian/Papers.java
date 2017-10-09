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

    public Papers(Context context, int maxX, int maxY) {

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
                x = (maxX / 100) * 10;
                y = (maxY / 100) * 25;
                break;
            case 1:
                x = (maxX / 100) * 90;
                y = (maxY / 100) * 25;
                break;
            case 2:
                x = (maxX / 100) * 10;
                y = (maxY / 100) * 45;
                break;
            case 3:
                x = (maxX / 100) * 90;
                y = (maxY / 100) * 45;
                break;
        }

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update() {
        //update coordinates to make papers move
        int speed;
        switch (shelf) {
            case 0:
                speed = calculateSpeed();
                x += speed;
                y += speed;
                break;
            case 1:
                speed = calculateSpeed();
                x -= speed;
                y += speed;
                break;
            case 2:
                speed = calculateSpeed();
                x += speed;
                y += speed;
                break;
            case 3:
                speed = calculateSpeed();
                x -= speed;
                y += speed;
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
