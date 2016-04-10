package com.giovani.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.giovani.enums.TypeDraw;
import com.giovani.fragments.Menu;
import com.giovani.objects.DrawingAction;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by darkgeat on 3/31/16.
 */
public class Lienzo extends View {

    private static final float STROKE_BIG_WIDTH = 20f;
    private static final float STROKE_WIDTH = 5f;
    private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    private int colorBrush;
    private TypeDraw typeDraw = TypeDraw.NONE;
    private Paint paint = new Paint();
    private Paint paintEraser = new Paint();
    private Paint paintLine = new Paint();
    private Paint paintCircle = new Paint();
    private Paint paintSquare = new Paint();
    private Paint paintEraseAll = new Paint();
    private Path path = new Path();
    private Path pathEraser = new Path();
    private Bitmap bitmap;
    private Canvas mCanvas = new Canvas();
    private boolean up_reached = false, clearAll = false;
    private float lastTouchX;
    private float lastTouchY;
    private float startX,startY,endX,endY;
    private double radius;
    private final RectF dirtyRect = new RectF();
    private List<DrawingAction> drawings = new ArrayList<>();

    public Lienzo(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        configurePaintBrush();
        paintEraseAll.setAlpha(0xFF);
        paintEraseAll.setColor(Color.TRANSPARENT);
        paintEraseAll.setStyle(Paint.Style.FILL_AND_STROKE);
        paintEraseAll.setMaskFilter(null);
        paintEraseAll.setStrokeJoin(Paint.Join.ROUND);
        paintEraseAll.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paintEraseAll.setStrokeWidth(STROKE_BIG_WIDTH);
    }

    public void setColorBrush(int colorBrush) {
        this.colorBrush = colorBrush;
        configurePaintBrush();
    }

    public void save(View v)
    {
            try
            {
                v.setDrawingCacheEnabled(true);
                Bitmap mBitmap = v.getDrawingCache();
                String folder = Environment.getExternalStorageDirectory().getPath() + "/PaginasEditadas/";
                File myFolder = new File(folder);
                if(!myFolder.exists()){
                    myFolder.mkdirs();
                }
                String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HHmm").format(new Date());
                String fileName = "Pagina_" + timeStamp + ".png";
                File image = new File(folder + fileName);
                FileOutputStream guarda = new FileOutputStream(image);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] data = bos.toByteArray();
                guarda.write(data);
                guarda.close();
                Log.v("Images", "Se ha guardado con Exito la Imagen en " + folder + fileName);
                Menu.setImagesEdited(getContext(),folder + fileName);
            }
            catch(Exception e)
            {
                Log.v("log_tag", e.toString());
            }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void clear()
    {
        clearAll = true;
        path.reset();
        pathEraser.reset();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(bitmap);
        }
        mCanvas.drawColor(Color.TRANSPARENT);
        /*if (typeDraw == TypeDraw.PENCIL){
            drawings.add(new DrawingAction(path,paint,TypeDraw.PENCIL,0,0));
        }else if (typeDraw == TypeDraw.ERASE){
            drawings.add(new DrawingAction(pathEraser,paintEraser,TypeDraw.ERASE,0,0));
        }
        for (DrawingAction action : drawings){
            mCanvas.drawPath(action.getPath(),action.getPaint());
        }*/
        mCanvas.drawPath(path, paint);
        if (up_reached && typeDraw == TypeDraw.LINE){
            mCanvas.drawLine(startX,startY,endX,endY,paintLine);
        }else if (!up_reached && typeDraw == TypeDraw.LINE){
            canvas.drawLine(startX, startY, endX, endY, paintLine);
        }else if (up_reached && typeDraw == TypeDraw.CIRCLE){
            mCanvas.drawCircle(endX,endY,(float)radius,paintCircle);
        }else if (!up_reached && typeDraw == TypeDraw.CIRCLE){
            canvas.drawCircle(endX,endY,(float)radius,paintCircle);
        }else if (up_reached && typeDraw == TypeDraw.SQUARE){
            mCanvas.drawRect(startX,startY,endX,endY,paintSquare);
        }else if (!up_reached && typeDraw == TypeDraw.SQUARE){
            canvas.drawRect(startX,startY,endX,endY,paintSquare);
        }
        mCanvas.drawPath(pathEraser, paintEraser);
        if (clearAll){
            mCanvas.drawRect(0,0,getWidth(),getHeight(),paintEraseAll);
            clearAll = false;
        }
        canvas.drawBitmap(bitmap, 0, 0, null);
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
                    up_reached = false;
                    startX = eventX;
                    startY = eventY;
                    if (typeDraw == TypeDraw.PENCIL) {
                        path.moveTo(eventX, eventY);
                    }else if (typeDraw == TypeDraw.ERASE){
                        pathEraser.moveTo(eventX, eventY);
                    }
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    Log.d("OnTouchEvent","ACTION_DOWN");
                    return true;

                case MotionEvent.ACTION_MOVE:
                    Log.d("OnTouchEvent","ACTION_MOVE");
                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    endX = eventX;
                    endY = eventY;
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
                    }else if (typeDraw == TypeDraw.LINE){
                        invalidate();
                    }else if (typeDraw == TypeDraw.CIRCLE){
                        radius = Math.sqrt((Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)));
                        invalidate();
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    up_reached = true;
                    if (typeDraw == TypeDraw.LINE || typeDraw == TypeDraw.SQUARE){
                        endX = eventX;
                        endY = eventY;
                        invalidate();
                    }
                    //mCanvas.drawPath(pathEraser,paintEraser);
                    Log.d("OnTouchEvent","ACTION_UP");
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
            paint.setColor(colorBrush);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }else if (typeDraw == TypeDraw.ERASE){
            paintEraser.setAlpha(0xFF);
            paintEraser.setColor(Color.TRANSPARENT);
            paintEraser.setStyle(Paint.Style.STROKE);
            paintEraser.setMaskFilter(null);
            paintEraser.setStrokeJoin(Paint.Join.ROUND);
            paintEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            paintEraser.setStrokeWidth(STROKE_BIG_WIDTH);
        }else if (typeDraw == TypeDraw.LINE){
            paintLine.setXfermode(null);
            paintLine.setAntiAlias(true);
            paintLine.setColor(colorBrush);
            paintLine.setStyle(Paint.Style.STROKE);
            paintLine.setStrokeJoin(Paint.Join.ROUND);
            paintLine.setStrokeWidth(STROKE_WIDTH);
        }else if (typeDraw == TypeDraw.CIRCLE){
            paintCircle.setXfermode(null);
            paintCircle.setAntiAlias(true);
            paintCircle.setColor(colorBrush);
            paintCircle.setStyle(Paint.Style.STROKE);
            paintCircle.setStrokeJoin(Paint.Join.ROUND);
            paintCircle.setStrokeWidth(STROKE_WIDTH);
        }else if (typeDraw == TypeDraw.SQUARE){
            paintSquare.setXfermode(null);
            paintSquare.setAntiAlias(true);
            paintSquare.setColor(colorBrush);
            paintSquare.setStyle(Paint.Style.STROKE);
            paintSquare.setStrokeJoin(Paint.Join.ROUND);
            paintSquare.setStrokeWidth(STROKE_WIDTH);
        }
    }
}
