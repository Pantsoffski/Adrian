package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Main character (player) class.
 */

public class President {
    //Bitmap, get character from image
    private Bitmap[] bitmap = new Bitmap[2];

    //coordinates
    private int x;
    private int y;

    //animation frame
    private int frame;

    //constructor
    public President(Context context) {
        x = 750;
        y = 500;
        frame = 0;

        //Getting bitmap frames and put to array
        bitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.president);
        bitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.president2);
    }

    //Method to update frame animation
/*    public void update() {
        //update current frame
        if (frame == 0) {
            frame = 1;
        } else {
            frame = 0;
        }
    }*/

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
}
