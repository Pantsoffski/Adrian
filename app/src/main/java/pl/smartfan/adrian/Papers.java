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

    //coordinates & angle
    private int x, y;
    private float angle = 0;

    //random shelf
    private int shelf;

    private Bitmap bitmap;
    private Random rand = new Random();

    public Papers(Context context, int maxX, int maxY) {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.law_paper);

        shelf = rand.nextInt(3);

        switch (shelf) {
            // TODO: 28.09.2017 why only two appears? 
            case 0:
                x = maxX - 500;
                y = maxY / 2;
                break;
            case 1:
                x = maxX - 800;
                y = maxY / 2;
                break;
            case 2:
                x = maxX - 1100;
                y = maxY / 2;
                break;
            case 3:
                x = maxX - 1400;
                y = maxY / 2;
                break;
        }
    }

    public void update() {
        // TODO: 28.09.2017 add Case here too, shelf dependant 
        //update coordinates to make them move
        x += 5;
        y += 5;
        angle += 0.001f;
    }

    //getters
    public Bitmap getBitmap() {
        bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false); //bitmap scaling
        matrix.postRotate(angle);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
