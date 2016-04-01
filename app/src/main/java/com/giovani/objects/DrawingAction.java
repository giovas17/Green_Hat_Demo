package com.giovani.objects;

import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;

import com.giovani.enums.TypeDraw;

/**
 * Created by DarkGeat on 3/31/2016.
 */
public class DrawingAction {
    private Path path;
    private Paint paint;
    private TypeDraw typeDraw;
    private int cx,cy;

    public DrawingAction(@Nullable Path path, Paint paint, TypeDraw typeDraw, @Nullable int cx, @Nullable int cy) {
        this.path = path;
        this.paint = paint;
        this.typeDraw = typeDraw;
        this.cx = cx;
        this.cy = cy;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public TypeDraw getTypeDraw() {
        return typeDraw;
    }

    public void setTypeDraw(TypeDraw typeDraw) {
        this.typeDraw = typeDraw;
    }

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public int getCy() {
        return cy;
    }

    public void setCy(int cy) {
        this.cy = cy;
    }
}
