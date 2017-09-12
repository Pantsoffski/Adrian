package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Piece of paper class.
 */

public class LawPaper {
    Matrix matrix = new Matrix();
    //up and down paper values
    boolean up = true;
    int ceiling = 300;
    //Bitmap, get character from image
    private Bitmap bitmap, rotatingBitmap;
    //coordinates & angle
    private int x, y;
    private float angle;

    //constructor
    public LawPaper(Context context) {
        x = 950;
        y = 500;
        angle = 0;

        //Getting bitmap frames and put to array
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.law_paper);
    }

    //movement animation
    public void update() {

        //up and down movement
        if (up == true && y >= ceiling) {
            y -= 5;

            if (y == ceiling) {
                up = false;
            }
        } else {
            //up = false;
            y += 5;

            if (y == 500) {
                up = true;
            }
        }

        //keep rotating paper
        if (angle < 360) {
            angle += 0.1f;
        } else {
            angle = 0;
        }

        matrix.postRotate(angle);
        rotatingBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        //x--;
    }

    //getters
    public Bitmap getBitmap() {
        return rotatingBitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
