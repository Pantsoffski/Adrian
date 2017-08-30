package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class President {
    //Bitmap, get character from image
    private Bitmap[] bitmap = new Bitmap[2];

    //coordinates
    private int x;
    private int y;

    //animation frame
    private int frame;

    //motion speed of the character
    private int speed = 0;

    //constructor
    public President(Context context) {
        x = 75;
        y = 50;
        speed = 1;
        frame = 0;

        //Getting bitmap from drawable resource
        bitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.president);
        bitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.president2);
    }

    //Method to update coordinate of character
    public void update() {
        //updating x coordinate
        x++;

        //update current frame
        if (frame == 0) {
            frame = 1;
        } else {
            frame = 0;
        }
    }

    //getters
    public Bitmap getBitmap() {
        return bitmap[frame];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
