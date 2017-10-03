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

    //creating a rect object
    private Rect detectCollision;

    private Bitmap bitmap;
    private Random rand = new Random();

    public Papers(Context context, int maxX, int maxY) {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ball);

        shelf = rand.nextInt(4);

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
        switch (shelf) {
            case 0:
                x += 5;
                y += 5;
                break;
            case 1:
                x -= 5;
                y += 5;
                break;
            case 2:
                x += 5;
                y += 5;
                break;
            case 3:
                x -= 5;
                y += 5;
                break;
        }

        //angle += 0.001f;

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
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
