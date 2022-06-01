package co.mjc.capstoneasap.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Stack;

public class DrawingService extends View {
    private Stack<Bitmap> beforeForwardStack;
    final String TAG = "DrawingService";
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;

    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Path> undonePaths = new ArrayList<Path>();



    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public Paint getmPaint() {
        return mPaint;
    }


    public DrawingService(Context context) {
        super(context);
        // 스택활용해서 기록 push pop;
        beforeForwardStack = new Stack<>();
        // 발자취를 만드는 객체
        mPath = new Path();
        // 화면에 그려줄 도구 객체
        mPaint = new Paint();
        // default 색
        mPaint.setColor(Color.BLACK);
        // 색상차가 뚜렷한 경계 부근에 중간색을 삽입하여 도형이나
        // 글꼴이 주변 배경과 부드럽게 잘 어울리도록 하는 기법이라고 하네요.
        mPaint.setAntiAlias(true);
        // 굵기
        mPaint.setStrokeWidth(5);
        // 채우기 없이 체두리만 그려주도록 설정
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(TAG, "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw");
        for (Path p : paths) {
            canvas.drawPath(p, mPaint);
        }
        canvas.drawPath(mPath,mPaint);
    }

    private float mX, mY;
    public static final float TOUCH_TOLERANCE = 4;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        // 이벤트가 발생하는 그 시점의 (x,y) 좌표
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 처음 눌렸을 때
                Log.d(TAG,"MotionEvent.ACTION_DOWN");
                undonePaths.clear();
                mPath.reset();
                mPath.moveTo(x, y);
                mX = x;
                mY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE: // 누르고 움직였을 때
                Log.d(TAG,"MotionEvent.ACTION_MOVE");
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                    mX = x;
                    mY = y;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP: // 누른 것을 떼면
                Log.d(TAG,"MotionEvent.ACTION_UP");
                mPath.lineTo(mX,mY);
                mCanvas.drawPath(mPath, mPaint);
                paths.add(mPath);
                mPath = new Path();
                invalidate();
                break;
        }
        return true;
    }
    public void onClickUndo () {
        if (paths.size()>0) {
            undonePaths.add(paths.remove(paths.size()-1));
            invalidate();
        }
        else {
        }
    }

    public void onClickRedo (){
        if (undonePaths.size()>0) {
            paths.add(undonePaths.remove(undonePaths.size()-1));
            invalidate();
        }
        else {
        }
    }


}
