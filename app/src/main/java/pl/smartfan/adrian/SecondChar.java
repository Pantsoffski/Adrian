package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Second character class.
 */

public class SecondChar {
    //Bitmap, get character from image
    private Bitmap[] bitmap = new Bitmap[2];

    //coordinates
    private int x;
    private int y;

    //animation frame
    private int frame;

    //constructor
    public SecondChar(Context context) {
        x = 1050;
        y = 500;
        frame = 0;

        //Getting bitmap frames and put to array
        bitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.second);
        bitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.second2);
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
}
