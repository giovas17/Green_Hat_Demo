package com.giovani.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.giovani.enums.TypeDraw;

/**
 * Created by darkgeat on 3/31/16.
 */
public class Lienzo extends View {

    private static final float STROKE_BIG_WIDTH = 20f;
    private static final float STROKE_WIDTH = 5f;
    private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    private TypeDraw typeDraw = TypeDraw.NONE;
    private Paint paint = new Paint();
    private Paint paintEraser = new Paint();
    private Path path = new Path();
    private Path pathEraser = new Path();

    private float lastTouchX;
    private float lastTouchY;
    private final RectF dirtyRect = new RectF();

    public Lienzo(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);
    }

       /* public void save(View v)
        {
            Log.e("log_tag", "Width: " + v.getWidth());
            Log.e("log_tag", "Height: " + v.getHeight());
            //This is the last size of the image
            int lastWidht = v.getWidth();
            int lastHeight = v.getHeight();
            mContent.setBackgroundColor(Color.WHITE);
            if(mBitmap == null)
            {
                mBitmap =  Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(mBitmap);
            try
            {
                String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HHmm").format(new Date());
                String fileName = "transactionID_" + timeStamp + ".png";
                File image = new File(Paths.BASE_PATH_SIGNS + fileName);
                String date = new SimpleDateFormat("dd/MMMM/yyyy").format(new Date());
                String time = new SimpleDateFormat("HH:mm").format(new Date());
                FileOutputStream mFileOutStream = new FileOutputStream(image);
                v.draw(canvas);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                mBitmap = BitmapFactory.decodeFile(image.getPath());
                mBitmap = Bitmap.createScaledBitmap(mBitmap, lastWidht, lastHeight, true);
                mFileOutStream = new FileOutputStream(image);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
            }
            catch(Exception e)
            {
                Log.v("log_tag", e.toString());
            }
        }

        public void clear()
        {
            path.reset();
            invalidate();
        }*/

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawPath(path, paint);
        canvas.drawPath(pathEraser,paintEraser);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (typeDraw != TypeDraw.NONE) {
            float eventX = event.getX();
            float eventY = event.getY();
            //mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (typeDraw == TypeDraw.PENCIL) {
                        path.moveTo(eventX, eventY);
                    }else if (typeDraw == TypeDraw.ERASE){
                        pathEraser.moveTo(eventX, eventY);
                    }
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        if (typeDraw == TypeDraw.PENCIL) {
                            path.lineTo(historicalX, historicalY);
                        }else if (typeDraw == TypeDraw.ERASE){
                            pathEraser.lineTo(historicalX, historicalY);
                        }
                    }
                    if (typeDraw == TypeDraw.PENCIL) {
                        path.lineTo(eventX, eventY);
                    }else if (typeDraw == TypeDraw.ERASE){
                        pathEraser.lineTo(eventX, eventY);
                    }
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }else {
            return false;
        }
    }

    private void debug(String string){
    }

    private void expandDirtyRect(float historicalX, float historicalY)
    {
        if (historicalX < dirtyRect.left)
        {
            dirtyRect.left = historicalX;
        }
        else if (historicalX > dirtyRect.right)
        {
            dirtyRect.right = historicalX;
        }

        if (historicalY < dirtyRect.top)
        {
            dirtyRect.top = historicalY;
        }
        else if (historicalY > dirtyRect.bottom)
        {
            dirtyRect.bottom = historicalY;
        }
    }

    private void resetDirtyRect(float eventX, float eventY)
    {
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }

    public TypeDraw getTypeDraw() {
        return typeDraw;
    }

    public void setTypeDraw(TypeDraw typeDraw) {
        this.typeDraw = typeDraw;
        configurePaintBrush();
    }

    private void configurePaintBrush() {
        if (typeDraw == TypeDraw.PENCIL){
            paint.setXfermode(null);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }else if (typeDraw == TypeDraw.ERASE){
            paintEraser.setAlpha(0xFF);
            paintEraser.setStyle(Paint.Style.STROKE);
            paintEraser.setStrokeJoin(Paint.Join.ROUND);
            paintEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            paintEraser.setStrokeWidth(STROKE_BIG_WIDTH);
        }
    }
}
