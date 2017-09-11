package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Piece of paper class.
 */

public class LawPaper {
    //Bitmap, get character from image
    private Bitmap bitmap;

    //coordinates
    private int x;
    private int y;

    //speed
    private int speed = 0;

    //constructor
    public LawPaper(Context context) {
        x = 950;
        y = 500;
        speed = 1;

        //Getting bitmap frames and put to array
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.law_paper);
    }

    //getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
