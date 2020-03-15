package com.example.directmanipulation;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.snatik.polygon.Line;
import com.snatik.polygon.Point;
import com.snatik.polygon.Polygon;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private String ID;
    private Polygon polygon;
    private Point orgRotationPoint; // to reset
    private Point rotationPoint; // -1 if cannot be rotated
    private float degree = 0;
    private float maxDegree; // 0 if cannot be rotated
    private Matrix matrix = new Matrix();
    private ArrayList<Model> children = new ArrayList<>();

    public Model(String ID, Point rotationPoint, float max, Polygon poly) {
        this.ID = ID;
        this.rotationPoint = rotationPoint;
        this.orgRotationPoint = new Point(rotationPoint.x, rotationPoint.y);
        this.maxDegree = max;
        this.polygon = poly;
    }

    //
    // Returns the matrix to it's starting size and position.
    //
    public void reset() {
        degree = 0;
        rotationPoint = new Point(orgRotationPoint.x, orgRotationPoint.y);
        matrix = new Matrix();
        for(Model child: children) {
            child.reset();
        }
    }

    //
    // Returns the ID of the figure.
    //
    public String getID() {
        return ID;
    }

    //
    // Adds children to the parent shape.
    //
    public void addChildren(Model child) {
        children.add(child);
    }

    //
    // Checks if the point (x, y) is contained within the
    // the figure.
    //
    public boolean contains(float x, float y) {
        // updates the child's rotation point using parents matrix
        Matrix inverse = new Matrix();
        inverse.set(matrix);
        inverse.invert(inverse);

        float[] a = {x, y};
        inverse.mapPoints(a);

        return polygon.contains(new Point(a[0], a[1]));
    }

    //
    // Scales the matrix by the distance dy-y.
    // Appends to the current matrix, so operations are cumulative.
    //
    void scale(float dy, float y) {
        // calculate the delta distance
        double scaleY = dy - y;
        matrix.postScale(0, (float)scaleY);
    }

    //
    // Translates the matrix by dx, dy.
    // Appends to the current matrix, so operations are cumulative.
    //
    void translate(float dx, float dy, float x, float y) {
        // calculate the delta distance
        double translateX = dx - x;
        double translateY = dy - y;
        matrix.postTranslate((float)translateX, (float)translateY);

        for(Model child: children) {
            child.translateChild(matrix, (float)translateX, (float)translateY);
        }
    }

    //
    // Translates the child's matrix, based upon the parent's matrix m.
    //
    void translateChild(Matrix m, float translateX, float translateY) {
        matrix.postTranslate(translateX, translateY);

        // update the child's rotation point using parents matrix
        float[] newContactPoint = {(float)orgRotationPoint.x, (float)orgRotationPoint.y,};
        m.mapPoints(newContactPoint);
        rotationPoint.x = newContactPoint[0];
        rotationPoint.y = newContactPoint[1];

        // translate the children
        for(Model child: children) {
            child.translateChild(m, translateX, translateY);
        }
    }

    //
    // Rotates the matrix.
    // Appends to the current matrix, so operations are cumulative.
    //
    void rotate(float rx, float ry, float x, float y) {
        double a = Math.atan2(ry - rotationPoint.y, rx - rotationPoint.x);
        double b = Math.atan2(y - rotationPoint.y, x - rotationPoint.x);
        double c = Math.toDegrees(a - b);

        if(!ID.equals("upperArm")) {
            if(Math.abs(degree + c) > maxDegree) {
                return;
            }
        }
        degree += c;

        matrix.postTranslate((float)-rotationPoint.x, (float)-rotationPoint.y );
        matrix.postRotate((float)c);
        matrix.postTranslate((float)rotationPoint.x, (float)rotationPoint.y );

        for(Model child: children) {
            child.rotateChildren(matrix, rotationPoint, c);
        }
    }

    //
    // Rotates the child's matrix, based upon the parent's matrix m.
    //
    void rotateChildren(Matrix m, Point rotate, double degrees) {
        // rotate the child relative to parent rotation point
        matrix.postTranslate((float)-rotate.x, (float)-rotate.y );
        matrix.postRotate((float)degrees);
        matrix.postTranslate((float)rotate.x, (float)rotate.y );

        // update the child's rotation point using parents matrix
        float[] newContactPoint = {(float)orgRotationPoint.x, (float)orgRotationPoint.y,};
        m.mapPoints(newContactPoint);
        rotationPoint.x = newContactPoint[0];
        rotationPoint.y = newContactPoint[1];

        for(Model child: children) {
            child.rotateChildren(m, rotate, degrees);
        }
    }

    //
    // Draw using the current matrix.
    //
    void draw(Canvas canvas, Paint paint) {
        Matrix oldMatrix = canvas.getMatrix();
        canvas.setMatrix(matrix);

        // draws a single body part
        List<Line> poly  = polygon.getSides();
        for(Line side: poly) {
            canvas.drawLine((float)side.getStart().x, (float)side.getStart().y,
                    (float)side.getEnd().x, (float)side.getEnd().y, paint);
        }

        // draws the children
        for(Model child: children) {
            child.draw(canvas, paint);
        }
        // resets to previous matrix
        canvas.setMatrix(oldMatrix);
    }
}