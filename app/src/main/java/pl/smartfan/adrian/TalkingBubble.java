package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Class responsible for text bubble.
 */

public class TalkingBubble {

    //creating a rect object
    private Rect rectRoundBubble;
    //sentence
    private String sentence;

    public TalkingBubble(Context context, int maxX, int maxY, Paint paint) {

        sentence = context.getString(R.string.funny_Sentence_1);

        rectRoundBubble = new Rect();

        paint.getTextBounds(sentence, 0, sentence.length(), rectRoundBubble); // TODO: 21.10.2017 get rect and put text inside

        rectRoundBubble.offset((maxX / 100) * 15, (maxY / 100) * 20);
    }

    public Rect getBubble() {
        return rectRoundBubble;
    }

    public String getSentence() {
        return sentence;
    }
}
