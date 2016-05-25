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
    private Paint brush;
    private String text;
    private int color;
    private TypeDraw typeDraw;
    private float cx,cy,fx,fy;

    public DrawingAction(@Nullable Path path, Paint paint, TypeDraw typeDraw, int color, String text,
                         @Nullable float cx, @Nullable float cy, @Nullable float fx, @Nullable float fy) {
        this.path = path;
        this.brush = paint;
        this.typeDraw = typeDraw;
        this.color = color;
        this.text = text;
        this.cx = cx;
        this.cy = cy;
        this.fx = fx;
        this.fy = fy;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Paint getBrush() {
        return brush;
    }

    public void setBrush(Paint paint) {
        this.brush = paint;
    }

    public TypeDraw getTypeDraw() {
        return typeDraw;
    }

    public void setTypeDraw(TypeDraw typeDraw) {
        this.typeDraw = typeDraw;
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getFx() {
        return fx;
    }

    public void setFx(float fx) {
        this.fx = fx;
    }

    public float getFy() {
        return fy;
    }

    public void setFy(float fy) {
        this.fy = fy;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
