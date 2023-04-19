package com.suhun.signproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedList;

public class SignView extends View {
    public String tag = SignView.class.getSimpleName();
    private LinkedList<LinkedList<HashMap<String, Float>>> lines;
    public SignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        lines = new LinkedList<LinkedList<HashMap<String, Float>>>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.GREEN);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(4);
        for(LinkedList<HashMap<String, Float>> line:lines){
            for(int i=1; i<line.size(); i++){
                HashMap<String, Float> po = line.get(i-1);
                HashMap<String, Float> p1 = line.get(i);
                canvas.drawLine(po.get("x"), po.get("y"), p1.get("x"), p1.get("y"), paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        HashMap<String, Float> point = new HashMap<>();
        float x = event.getX(), y = event.getY();
        point.put("x", x);
        point.put("y", y);
        LinkedList<HashMap<String, Float>> line;
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d(tag, "Draw new line in x:" + x + "y:" + y);
            line = new LinkedList<HashMap<String, Float>>();
            line.add(point);
            lines.add(line);
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            Log.d(tag, "Draw old line in x:" + x + "y:" + y);
            lines.getLast().add(point);
        }
        invalidate(); //update onDraw
        return true;
    }
}
