package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Class responsible for text bubble.
 */

public class TalkingBubble {

    private int screenX, screenY;
    private String text;

    //creating a rect object
    private RectF rectRoundBubble;

    public TalkingBubble(Context context, int maxX, int maxY, String text, Paint paint) {
        screenX = maxX;
        screenY = maxY;
        this.text = text;

        Rect bounds = new Rect();

        //initializing rect object
        rectRoundBubble = new RectF();
        rectRoundBubble.set(200, 150, 450, 350);

        paint.getTextBounds(text, 0, text.length(), bounds); // TODO: 21.10.2017 get rect and put text inside 
    }

    public RectF getBubble() {
        return rectRoundBubble;
    }
}
