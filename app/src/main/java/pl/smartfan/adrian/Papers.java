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

        //paper should appear on random shelf
        switch (shelf) { // TODO: 28.09.2017 put paper parts to corners
            case 0:
                x = maxX / 4;
                y = maxY / 4;
                break;
            case 1:
                x = maxX / 2 + maxX / 4;
                y = maxY / 4;
                break;
            case 2:
                x = maxX / 4;
                y = maxY / 2 + maxY / 4;
                break;
            case 3:
                x = maxX / 2 + maxX / 4;
                y = maxY / 2 + maxY / 4;
                break;
        }
    }

    public void update() {
        // TODO: 28.09.2017 add Case here too, change coordinates shelf dependant
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
