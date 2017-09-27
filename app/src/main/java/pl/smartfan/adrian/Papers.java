package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.Random;

/**
 * Piece of paper class.
 */

public class Papers {
    private Matrix matrix = new Matrix();
    private int x, y;
    private Bitmap bitmap;
    private Random rand = new Random();

    public Papers(Context context) {
        x = 1300;
        y = 450;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.law_paper);

        x = rand.nextInt(1500 - 800) + 800;
        y = rand.nextInt(800 - 250) + 250;
    }

    public void update() {
        //update coordinates to make them move
        x += 5;
        y += 5;
    }

    //getters
    public Bitmap getBitmap() {
        bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false); //bitmap scaling
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
