package pl.smartfan.adrian;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Class responsible for text bubble.
 */

public class TalkingBubble {

    private int screenX, screenY;
    private String text;

    //creating a rect object
    private Rect rectRoundBubble;

    public TalkingBubble(int maxX, int maxY, String text, Paint paint) {
        screenX = maxX;
        screenY = maxY;
        this.text = text;

        rectRoundBubble = new Rect();

        paint.getTextBounds(text, 0, text.length(), rectRoundBubble); // TODO: 21.10.2017 get rect and put text inside

        rectRoundBubble.offset((maxX / 100) * 15, (maxY / 100) * 20);
    }

    public Rect getBubble() {
        return rectRoundBubble;
    }
}
