package com.giovani.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.giovani.dialogs.ColorPickerDialog;
import com.giovani.dialogs.SimpleDialog;
import com.giovani.dialogs.TextDialog;
import com.giovani.enums.TypeDraw;
import com.giovani.greenhat.R;
import com.giovani.listeners.OnActionDrawingListener;
import com.giovani.listeners.OnColorListener;
import com.giovani.listeners.OnFinishedTextListener;
import com.giovani.listeners.OnTextListener;
import com.giovani.views.CustomScroll;
import com.giovani.views.Lienzo;
/**
 * Created by DarkGeat on 3/31/2016.
 */
public class DrawingPage extends Fragment implements com.giovani.greenhat.DrawingPage.onBackListener,
        View.OnTouchListener, OnColorListener, OnTextListener, OnFinishedTextListener {

    private CustomScroll scroll;
    private TypeDraw typeDraw = TypeDraw.NONE;
    private ImageView trazo;
    private ImageView erase;
    private ImageView line;
    private ImageView circle;
    private ImageView square;
    private ImageView lienzoImage;
    private ImageView page;
    private ImageView customText;
    private CheckBox zooming;
    private FrameLayout completeImage;
    private Lienzo lienzo;
    private int colorSelected = Color.RED, positionColor = 0;
    private FloatingActionButton fab;
    private OnActionDrawingListener listener;

    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawing_page, container, false);
        int image = getActivity().getIntent().getExtras().getInt("image");
        page = (ImageView)v.findViewById(R.id.imagePaginaDrawing);
        Glide.with(this).load(image).into(page);

        fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog dialog = new ColorPickerDialog(getContext(), colorSelected, DrawingPage.this, positionColor);
                dialog.show();
            }
        });

        completeImage = (FrameLayout)v.findViewById(R.id.containerDraw);

        lienzo = (Lienzo)v.findViewById(R.id.drawImage);
        lienzo.setBackgroundColor(Color.TRANSPARENT);
        lienzo.setColorBrush(colorSelected);
        listener = (OnActionDrawingListener)lienzo;
        lienzo.setListener(this);

        lienzoImage = (ImageView)v.findViewById(R.id.imageLienzo);

        zooming = (CheckBox) v.findViewById(R.id.zoomControl);
        zooming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lienzoImage.setImageBitmap(lienzo.getBitmap());
                    lienzoImage.setVisibility(View.VISIBLE);
                    lienzo.setVisibility(View.GONE);
                    lienzoImage.setOnTouchListener(DrawingPage.this);
                } else {
                    lienzoImage.setVisibility(View.GONE);
                    lienzo.setVisibility(View.VISIBLE);
                    typeDraw = TypeDraw.NONE;
                    updateViews(typeDraw);
                    page.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }
        });

        trazo = (ImageView)v.findViewById(R.id.trazoLibre);
        trazo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDraw == TypeDraw.PENCIL) {
                    typeDraw = TypeDraw.NONE;
                } else {
                    typeDraw = TypeDraw.PENCIL;
                }
                lienzo.setTypeDraw(typeDraw);
                updateViews(typeDraw);
            }
        });

        erase = (ImageView)v.findViewById(R.id.goma);
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDraw == TypeDraw.ERASE){
                    typeDraw = TypeDraw.NONE;
                }else {
                    typeDraw = TypeDraw.ERASE;
                }
                lienzo.setTypeDraw(typeDraw);
                updateViews(typeDraw);
            }
        });

        line = (ImageView)v.findViewById(R.id.lineDrawing);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDraw == TypeDraw.LINE) {
                    typeDraw = TypeDraw.NONE;
                } else {
                    typeDraw = TypeDraw.LINE;
                }
                lienzo.setTypeDraw(typeDraw);
                updateViews(typeDraw);
            }
        });

        circle = (ImageView)v.findViewById(R.id.circleDrawing);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDraw == TypeDraw.CIRCLE) {
                    typeDraw = TypeDraw.NONE;
                } else {
                    typeDraw = TypeDraw.CIRCLE;
                }
                lienzo.setTypeDraw(typeDraw);
                updateViews(typeDraw);
            }
        });

        square = (ImageView)v.findViewById(R.id.squareDrawing);
        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDraw == TypeDraw.SQUARE) {
                    typeDraw = TypeDraw.NONE;
                } else {
                    typeDraw = TypeDraw.SQUARE;
                }
                lienzo.setTypeDraw(typeDraw);
                updateViews(typeDraw);
            }
        });

        customText = (ImageView)v.findViewById(R.id.customText);
        customText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDraw == TypeDraw.TEXT) {
                    typeDraw = TypeDraw.NONE;
                } else {
                    typeDraw = TypeDraw.TEXT;
                }
                lienzo.setTypeDraw(typeDraw);
                updateViews(typeDraw);
            }
        });

        ImageView clearAll = (ImageView) v.findViewById(R.id.imageClearAll);
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lienzo.clear();
            }
        });

        ImageView back = (ImageView) v.findViewById(R.id.imageBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView undo = (ImageView) v.findViewById(R.id.undoButton);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnUndo();
            }
        });

        ImageView forward = (ImageView) v.findViewById(R.id.forwardButton);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnForward();
            }
        });

        scroll = (CustomScroll)v.findViewById(R.id.scroll);
        return v;
    }

    private void updateViews(TypeDraw type){
        Log.v("UpdatesViews",type.name());
        zooming.setChecked(false);
        switch (type){
            case NONE:{
                scroll.setmScrollable(true);
                trazo.setBackgroundColor(Color.TRANSPARENT);
                erase.setBackgroundColor(Color.TRANSPARENT);
                line.setBackgroundColor(Color.TRANSPARENT);
                circle.setBackgroundColor(Color.TRANSPARENT);
                square.setBackgroundColor(Color.TRANSPARENT);
                customText.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            case PENCIL:{
                scroll.setmScrollable(false);
                trazo.setBackgroundColor(Color.YELLOW);
                erase.setBackgroundColor(Color.TRANSPARENT);
                line.setBackgroundColor(Color.TRANSPARENT);
                circle.setBackgroundColor(Color.TRANSPARENT);
                square.setBackgroundColor(Color.TRANSPARENT);
                customText.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            case ERASE:{
                scroll.setmScrollable(false);
                erase.setBackgroundColor(Color.YELLOW);
                trazo.setBackgroundColor(Color.TRANSPARENT);
                line.setBackgroundColor(Color.TRANSPARENT);
                circle.setBackgroundColor(Color.TRANSPARENT);
                square.setBackgroundColor(Color.TRANSPARENT);
                customText.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            case LINE:{
                scroll.setmScrollable(false);
                line.setBackgroundColor(Color.YELLOW);
                trazo.setBackgroundColor(Color.TRANSPARENT);
                erase.setBackgroundColor(Color.TRANSPARENT);
                circle.setBackgroundColor(Color.TRANSPARENT);
                square.setBackgroundColor(Color.TRANSPARENT);
                customText.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            case CIRCLE:{
                scroll.setmScrollable(false);
                circle.setBackgroundColor(Color.YELLOW);
                line.setBackgroundColor(Color.TRANSPARENT);
                trazo.setBackgroundColor(Color.TRANSPARENT);
                erase.setBackgroundColor(Color.TRANSPARENT);
                square.setBackgroundColor(Color.TRANSPARENT);
                customText.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            case SQUARE:{
                scroll.setmScrollable(false);
                square.setBackgroundColor(Color.YELLOW);
                circle.setBackgroundColor(Color.TRANSPARENT);
                line.setBackgroundColor(Color.TRANSPARENT);
                trazo.setBackgroundColor(Color.TRANSPARENT);
                erase.setBackgroundColor(Color.TRANSPARENT);
                customText.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            case TEXT:{
                scroll.setmScrollable(false);
                customText.setBackgroundColor(Color.YELLOW);
                square.setBackgroundColor(Color.TRANSPARENT);
                circle.setBackgroundColor(Color.TRANSPARENT);
                line.setBackgroundColor(Color.TRANSPARENT);
                trazo.setBackgroundColor(Color.TRANSPARENT);
                erase.setBackgroundColor(Color.TRANSPARENT);
                lienzo.setTextoIngresado("");
                TextDialog dialog = new TextDialog(getContext(), this);
                dialog.show();
                break;
            }
            default:{
                scroll.setmScrollable(false);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        SimpleDialog confirmacion = new SimpleDialog(getContext(), "Guardado Automático", "¿Desea guardar los cambios que hizo en la página?", new SimpleDialog.okListener() {
            @Override
            public void OnOkSelected() {
                lienzo.save(completeImage);
                getActivity().finish();
            }

            @Override
            public void OnCancelSelected() {getActivity().finish();}
        });
        confirmacion.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        page.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG)
                {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                }
                else if (mode == ZOOM)
                {
                    // pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f)
                    {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen
        page.setImageMatrix(matrix);

        return true; // indicate event was handled
    }

    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float)Math.sqrt(x*x+y*y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event)
    {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }

    @Override
    public void OnColorSelected(int selectedColor, int positionColor) {
        colorSelected = selectedColor;
        this.positionColor = positionColor;
        lienzo.setColorBrush(colorSelected);
        fab.setBackgroundTintList(ColorStateList.valueOf(colorSelected));
    }

    @Override
    public void OnOkPressed(String ingresado) {
        lienzo.setTextoIngresado(ingresado);
    }

    @Override
    public void OnCancelPressed() {
        typeDraw = TypeDraw.NONE;
        updateViews(typeDraw);
    }

    @Override
    public void OnDrawTextAdded() {
        typeDraw = TypeDraw.NONE;
        updateViews(typeDraw);
    }
}
