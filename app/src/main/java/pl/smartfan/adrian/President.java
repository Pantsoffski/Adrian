package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Main character (player) class.
 */

public class President {
    //Bitmap, get character from image
    private Bitmap[] bitmap = new Bitmap[2];

    //coordinates
    private int x;
    private int y;

    //creating a rect object
    private Rect detectCollision;

    //constructor
    public President(Context context) {
        x = 500;
        y = 500;

        //Getting bitmap frames and put to array
        bitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.president);
        bitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.president2);

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap[0].getWidth(), bitmap[0].getHeight());
    }

    //Method to update frame animation
    public void update() {
        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap[0].getWidth();
        detectCollision.bottom = y + bitmap[0].getHeight();
    }

    //getters
    public Bitmap getBitmap(int number) {
        return bitmap[number];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }
}
