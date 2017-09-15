package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import static pl.smartfan.adrian.GameView.paperVisibility;
import static pl.smartfan.adrian.GameView.signedLawPaper;

/**
 * Piece of paper class.
 */

public class LawPaper {
    private Matrix matrix = new Matrix();
    //up and down & right to left paper values
    private boolean up, left = true;
    private int ceiling, leftLimit;
    //Bitmap, get character from image
    private Bitmap bitmap, rotatingBitmap;
    //coordinates & angle
    private int x, y;
    private float angle;

    //floor (for detect collision)
    //private Rect floor;

    //constructor
    public LawPaper(Context context) {
        x = 1300;
        y = 450;
        angle = 0;
        ceiling = 250;
        leftLimit = 750;

        //initializing rect floor
        //floor = new Rect(0, 500, 1500, 505);

        //Getting bitmap frames and put to array
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.law_paper);
    }

    //movement animation
    public void update() {
        if (paperVisibility == true) {
            //up and down movement
/*            if (up == true && y >= ceiling && left == true && signedLawPaper == false) { // TODO: 13.09.2017 clean up code
                y -= 10;

                if (y == ceiling) {
                    up = false;
                }
            } else if (x != leftLimit && x >= leftLimit) {
                //up = false;
                y += 10;

                if (y == 500) {
                    up = true;
                }
            }*/

            //right to left movement
            if (left && x >= leftLimit) {
                x -= 10;

                if (x == leftLimit) {
                    left = false;
                }
            } else if (signedLawPaper) { //when law is signed, move left to briefcase
                x -= 15;

                if (x < 550) {
                    signedLawPaper = false;
                    paperVisibility = false;
                    left = true;
                }
            }

            //keep rotating paper
            /*if (angle < 360) {
                angle += 0.01f;
            } else {
                angle = 0;
            }*/
        } else {
            x = 1300;
            y = 450;
            angle = 0;
        }

        /*matrix.postRotate(angle);
        rotatingBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        //scaling bitmap
        rotatingBitmap = Bitmap.createScaledBitmap(rotatingBitmap, 160, 220, false);*/
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
