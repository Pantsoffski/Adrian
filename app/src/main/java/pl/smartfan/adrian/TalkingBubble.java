package pl.smartfan.adrian;

import android.content.Context;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.util.Random;

/**
 * Class responsible for text bubble.
 */

public class TalkingBubble {

    //creating a rect object
    private Rect rectRoundBubble;
    //sentence
    private String[] sentences;
    //text to push
    private StaticLayout sl;
    //random sentence
    private Random r = new Random();

    public TalkingBubble(Context context, int maxX, int maxY) {

        sentences = context.getResources().getStringArray(R.array.funny_sentences);

        int randomSentence = r.nextInt(sentences.length);

        TextPaint textPaint = new TextPaint();

        textPaint.setTextSize(40);

        sl = new StaticLayout(sentences[randomSentence], textPaint, (maxX / 100) * 20, Layout.Alignment.ALIGN_CENTER, 1, 1, true);

        rectRoundBubble = new Rect((maxX / 100) * 15, (maxY / 100) * 20, (maxX / 100) * 15 + sl.getWidth(), (maxY / 100) * 20 + sl.getHeight());
    }

    public Rect getBubble() {
        return rectRoundBubble;
    }

    public StaticLayout getText() {
        return sl;
    }

}
