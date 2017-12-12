package anastasia.draw.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View{

    //Listeners

    public void setPointListener(PointListener pointListener) {
        this.pointListener = pointListener;
    }

    private PointListener pointListener;

    public void setLineListener(LineListener lineListener) {
        this.lineListener = lineListener;
    }

    private LineListener lineListener;

    public void setCircleListener(CircleListener circleListener) {
        this.circleListener = circleListener;
    }

    private CircleListener circleListener;

    public void setRectListener(RectListener rectListener) {
        this.rectListener = rectListener;
    }

    private RectListener rectListener;

    private int currentAction = 0;

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    private float STX, STY;

    public void setXOY(float X,float Y) {
        STX=X;
        STY=Y;
    }

    public float getSTX() {return STX;}
    public float getSTY() {return STY;}

    public void setcurrentAction(int c) {
        currentAction = c;
    }

    public int getCurrentAction() {return currentAction;}

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }


    public void drawPoint(int x, int y, int color) {
        drawPaint.setColor(color);
        drawPath.moveTo(x, y);
        drawPath.lineTo(x+1, y+1);
        drawCanvas.drawPath(drawPath, drawPaint);
        drawPath.reset();
        invalidate();
    }
    public  void drawLine(int x1, int x2, int y1, int y2, int color) {
        drawPaint.setColor(color);
        drawPath.moveTo(x1, y1);
        drawPath.lineTo(x2, y2);
        drawCanvas.drawPath(drawPath, drawPaint);
        drawPath.reset();
        invalidate();
    }

    public void drawCircle(int x1, int x2, int y1, int y2, int color) {
        drawPaint.setColor(color);
        RectF oval = new RectF(x1,y1,x2,y2);
        drawPath.addOval(oval,Path.Direction.CW);
        drawCanvas.drawPath(drawPath, drawPaint);
        drawPath.reset();
        invalidate();
    }

    public void drawRect(int x1, int x2, int y1, int y2, int color) {
        drawPaint.setColor(color);
        drawPath.addRect(x1,y1, x2, y2, Path.Direction.CW);
        drawCanvas.drawPath(drawPath, drawPaint);
        drawPath.reset();
        invalidate();
    }

    public void drawClear(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }


    private void setupDrawing() {
//get drawing area setup for interaction
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);

//начальные свойства
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);



    }

    //просмотр в виде чертежа View
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw the canvas and the drawing path
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret=false;
        float touchX = event.getX();
        float touchY = event.getY();

        switch (getCurrentAction()) {
            case 1: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:  //установка новой точки
                        drawPath.moveTo(touchX, touchY);
                        if (pointListener != null )
                            pointListener.OnPointTouch((int)touchX, (int)touchY, paintColor);
                        setXOY(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_MOVE:   //установка линий
                        drawPath.lineTo(touchX, touchY);
                        if (pointListener != null )
                            pointListener.OnPointTouch((int)touchX, (int)touchY, paintColor);
                        break;
                    case MotionEvent.ACTION_UP:  //прекращение касания
                        drawCanvas.drawPath(drawPath, drawPaint);
                        drawPath.reset();
                        break;
                    default:
                        ret = false;
                }
                invalidate();
                ret = true;
                break;
            }
            case 2: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:  //установка новой точки
                        drawPath.moveTo(touchX, touchY);
                        setXOY(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_MOVE:   //установка линий
                        drawPath.lineTo(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_UP:  //прекращение касания
                        drawPath.reset();
                        drawPath = DrawLine(touchX, touchY);
                        drawCanvas.drawPath(drawPath, drawPaint);

                        drawPath.reset();
                        break;
                    default:
                        ret = false;
                }
                invalidate();
                ret = true;
                break;
            }
            case 3: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:  //установка новой точки
                        drawPath.moveTo(touchX, touchY);
                        setXOY(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_MOVE:   //установка линий
                        drawPath = DrawCircle(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_UP:  //прекращение касания
                        drawPath.reset();
                        drawPath = DrawCircle(touchX, touchY);
                        if (circleListener != null)
                            circleListener.CircleDraw((int)getSTX(), (int)getSTY(),(int)touchX, (int)touchY, paintColor);
                        drawCanvas.drawPath(drawPath, drawPaint);
                        drawPath.reset();
                        break;
                    default:
                        ret = false;
                }
                invalidate();
                ret = true;
                break;
            }
            case 4: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:  //установка новой точки
                        drawPath.moveTo(touchX, touchY);
                        setXOY(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_MOVE:   //установка линий
                        drawPath = DrawRect(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_UP:  //прекращение касания
                        drawPath.reset();
                        drawPath = DrawRect(touchX, touchY);
                        if (rectListener != null)
                            rectListener.RectDraw((int)getSTX(), (int)getSTY(),(int)touchX, (int)touchY, paintColor);
                        drawCanvas.drawPath(drawPath, drawPaint);
                        drawPath.reset();
                        break;
                    default:
                        ret = false;
                }
                invalidate();
                ret = true;
                break;
            }
            default:
                drawPath.moveTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
        }
        return ret;
    }



    public void setColor(String newColor){
//set color
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }


    public Path DrawLine(float x2, float y2) {
        Path line2d = new Path();
        line2d.moveTo(getSTX(), getSTY());
        line2d.lineTo(x2, y2);
        if (lineListener != null)
            lineListener.LineDraw((int)getSTX(), (int)getSTY(),(int)x2, (int)y2, paintColor);
        return line2d;
    }

    public Path DrawCircle( float x2, float y2) {
        Path circle2d = new Path();
        RectF oval = new RectF(getSTX(),getSTY(),x2,y2);
        circle2d.addOval(oval,Path.Direction.CW);
        return circle2d;
    }

    public Path DrawRect( float x2, float y2) {
        Path rect2d = new Path();
// just example for line could be complex shape
        rect2d.addRect(getSTX(),getSTY(), x2, y2, Path.Direction.CW);
        return rect2d;
    }

    public interface PointListener {
        public void OnPointTouch(int touchX, int touchY, int paintColor);
    }

    public interface LineListener {
        public void LineDraw(int touchX1, int touchX2, int touchY1, int touchY2, int paintColor);
    }

    public interface CircleListener {
        public void CircleDraw(int touchX1, int touchX2, int touchY1, int touchY2, int paintColor);
    }

    public interface RectListener {
        public void RectDraw(int touchX1, int touchX2, int touchY1, int touchY2, int paintColor);
    }

}


