package eldad.corem.exercises;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 Created by Eldad Corem on 07/12/2016.
 */

public class lessonSixViewB extends View {
    Paint paint = new Paint();
    Paint paintB = new Paint();

    public lessonSixViewB(Context context) {
        super(context);
        init();
    }

    public lessonSixViewB(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public lessonSixViewB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setColor(R.color.indigo);
        paint.setAntiAlias(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas){
        canvas.rotate(-15,400,0);
        canvas.drawLine(50,0,50,100, paint);
        canvas.drawLine(50,50,100,50, paint);
        canvas.drawLine(100,0,100,100, paint);
        canvas.drawLine(120,0,170,0, paint);
        canvas.drawLine(120,50,170,50, paint);
        canvas.drawLine(120,100,170,100, paint);
        canvas.drawLine(120,0,120,100, paint);
        canvas.drawLine(190,0,190,100, paint);
        canvas.drawLine(190,100,240,100, paint);
        canvas.drawLine(260,0,260,100, paint);
        canvas.drawLine(260,100,310,100, paint);
        canvas.drawOval(320,0,370,100, paint);
        paintB.setColor(Color.YELLOW);
        paintB.setAntiAlias(true);
        canvas.drawOval(322,2,368,98, paintB);

    }
}
