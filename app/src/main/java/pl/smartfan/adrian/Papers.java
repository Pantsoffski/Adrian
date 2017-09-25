package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Piece of paper class.
 */

public class Papers {
    private Matrix matrix = new Matrix();
    private int x, y;
    private Bitmap bitmap;

    public Papers(Context context) {
        x = 1300;
        y = 450;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.law_paper);
    }

    public void update() {

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
