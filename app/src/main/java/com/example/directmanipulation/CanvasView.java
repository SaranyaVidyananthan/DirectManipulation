package com.example.directmanipulation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import androidx.annotation.RequiresApi;
import com.snatik.polygon.Point;
import com.snatik.polygon.Polygon;
import java.util.ArrayList;


public class CanvasView extends View {
    ArrayList<Model> Parts = new ArrayList<>();
    private Model currSelectedPolygon = null;
    private Model secondSelectedPolygon = null;
    private ScaleGestureDetector scaleDetector;
    private float startX;
    private float startY;
    private Paint brush;

    public CanvasView(Context context) {
        super(context);

        brush = new Paint(Paint.ANTI_ALIAS_FLAG);
        brush.setColor(Color.BLACK);
        brush.setStrokeWidth(10);

        initListener(context);
        initRobot();
    }

    private void initListener(Context context) {
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    //
    // Initialize each body part of the robot.
    //
    public void initRobot() {
        Polygon torsoPoly = Polygon.Builder()
                .addVertex(new Point(800, 350))
                .addVertex(new Point(1200, 350))
                .addVertex(new Point(1200, 900))
                .addVertex(new Point(800, 900)).build();
        Polygon headPoly = Polygon.Builder()
                .addVertex(new Point(900, 200))
                .addVertex(new Point(1100, 200))
                .addVertex(new Point(1100, 340))
                .addVertex(new Point(900, 340)).build();
        Polygon upperLeftArmPoly = Polygon.Builder()
                .addVertex(new Point(700, 380))
                .addVertex(new Point(790, 380))
                .addVertex(new Point(790, 600))
                .addVertex(new Point(700, 600)).build();
        Polygon lowerLeftArmPoly = Polygon.Builder()
                .addVertex(new Point(700, 600))
                .addVertex(new Point(790, 600))
                .addVertex(new Point(790, 800))
                .addVertex(new Point(700, 800)).build();
        Polygon leftHandPoly = Polygon.Builder()
                .addVertex(new Point(700, 800))
                .addVertex(new Point(790, 800))
                .addVertex(new Point(790, 850))
                .addVertex(new Point(700, 850)).build();
        Polygon upperRightArmPoly = Polygon.Builder()
                .addVertex(new Point(1210, 380))
                .addVertex(new Point(1300, 380))
                .addVertex(new Point(1300, 600))
                .addVertex(new Point(1210, 600)).build();
        Polygon lowerRightArmPoly = Polygon.Builder()
                .addVertex(new Point(1210, 600))
                .addVertex(new Point(1300, 600))
                .addVertex(new Point(1300, 800))
                .addVertex(new Point(1210, 800)).build();
        Polygon rightHandPoly = Polygon.Builder()
                .addVertex(new Point(1210, 800))
                .addVertex(new Point(1300, 800))
                .addVertex(new Point(1300, 850))
                .addVertex(new Point(1210, 850)).build();
        Polygon upperLeftLegPoly = Polygon.Builder()
                .addVertex(new Point(850, 910))
                .addVertex(new Point(950, 910))
                .addVertex(new Point(950, 1100))
                .addVertex(new Point(850, 1100)).build();
        Polygon lowerLeftLegPoly = Polygon.Builder()
                .addVertex(new Point(850, 1100))
                .addVertex(new Point(950, 1100))
                .addVertex(new Point(950, 1200))
                .addVertex(new Point(850, 1200)).build();
        Polygon leftFeetPoly = Polygon.Builder()
                .addVertex(new Point(850, 1200))
                .addVertex(new Point(950, 1200))
                .addVertex(new Point(950, 1250))
                .addVertex(new Point(850, 1250)).build();
        Polygon upperRightLegPoly = Polygon.Builder()
                .addVertex(new Point(1050, 910))
                .addVertex(new Point(1150, 910))
                .addVertex(new Point(1150, 1100))
                .addVertex(new Point(1050, 1100)).build();
        Polygon lowerRightLegPoly = Polygon.Builder()
                .addVertex(new Point(1050, 1100))
                .addVertex(new Point(1150, 1100))
                .addVertex(new Point(1150, 1200))
                .addVertex(new Point(1050, 1200)).build();
        Polygon rightFeetPoly = Polygon.Builder()
                .addVertex(new Point(1050, 1200))
                .addVertex(new Point(1150, 1200))
                .addVertex(new Point(1150, 1250))
                .addVertex(new Point(1050, 1250)).build();

        Model torso = new Model("torso", new Point(0, 0), 0, torsoPoly);
        Parts.add(torso);
        Model head = new Model("head", new Point(1000, 275), 25, headPoly);
        Parts.add(head);
        Model upperLeftArm = new Model("upperArm", new Point(745, 380), 360, upperLeftArmPoly);
        Parts.add(upperLeftArm);
        Model lowerLeftArm = new Model("lowerArm", new Point(745, 600), 135, lowerLeftArmPoly);
        Parts.add(lowerLeftArm);
        Model leftHand = new Model("hand", new Point(745, 800), 35, leftHandPoly);
        Parts.add(leftHand);
        Model upperRightArm = new Model("upperArm", new Point(1255, 380), 360, upperRightArmPoly);
        Parts.add(upperRightArm);
        Model lowerRightArm = new Model("lowerArm", new Point(1255, 600), 30, lowerRightArmPoly);
        Parts.add(lowerRightArm);
        Model rightHand = new Model("hand", new Point(1255, 800), 35, rightHandPoly);
        Parts.add(rightHand);
        Model upperLeftLeg = new Model("upperLeg", new Point(900, 910), 90, upperLeftLegPoly);
        Parts.add(upperLeftLeg);
        Model lowerLeftLeg = new Model("lowerLeg", new Point(900, 1100), 90, lowerLeftLegPoly);
        Parts.add(lowerLeftLeg);
        Model leftFeet = new Model("feet", new Point(900, 1200), 35, leftFeetPoly);
        Parts.add(leftFeet);
        Model upperRightLeg = new Model("upperLeg", new Point(1100, 910), 90, upperRightLegPoly);
        Parts.add(upperRightLeg);
        Model lowerRightLeg = new Model("lowerLeg", new Point(1100, 1100), 90, lowerRightLegPoly);
        Parts.add(lowerRightLeg);
        Model rightFeet = new Model("feet", new Point(1100, 1200), 35, rightFeetPoly);
        Parts.add(rightFeet);

        // set up the children
        torso.addChildren(head);
        torso.addChildren(upperLeftArm);
            upperLeftArm.addChildren(lowerLeftArm);
                lowerLeftArm.addChildren(leftHand);
        torso.addChildren(upperRightArm);
            upperRightArm.addChildren(lowerRightArm);
                lowerRightArm.addChildren(rightHand);
        torso.addChildren(upperLeftLeg);
            upperLeftLeg.addChildren(lowerLeftLeg);
                lowerLeftLeg.addChildren(leftFeet);
        torso.addChildren(upperRightLeg);
            upperRightLeg.addChildren(lowerRightLeg);
                lowerRightLeg.addChildren(rightFeet);
    }

    //
    // Initialize each body part of the dog.
    //
    public void initDog() {
        Polygon torsoPoly = Polygon.Builder()
                .addVertex(new Point(1100, 400))
                .addVertex(new Point(1700, 400))
                .addVertex(new Point(1700, 740))
                .addVertex(new Point(1100, 740)).build();
        Polygon headPoly = Polygon.Builder()
                .addVertex(new Point(950, 430))
                .addVertex(new Point(1100, 430))
                .addVertex(new Point(1100, 670))
                .addVertex(new Point(950, 670)).build();
        Polygon upperLeftArmPoly = Polygon.Builder()
                .addVertex(new Point(1150, 745))
                .addVertex(new Point(1200, 745))
                .addVertex(new Point(1200, 900))
                .addVertex(new Point(1150, 900)).build();
        Polygon lowerLeftArmPoly = Polygon.Builder()
                .addVertex(new Point(1150, 900))
                .addVertex(new Point(1200, 900))
                .addVertex(new Point(1200, 1000))
                .addVertex(new Point(1150, 1000)).build();
        Polygon leftHandPoly = Polygon.Builder()
                .addVertex(new Point(1150, 1000))
                .addVertex(new Point(1200, 1000))
                .addVertex(new Point(1200, 1050))
                .addVertex(new Point(1150, 1050)).build();
        Polygon upperRightArmPoly = Polygon.Builder()
                .addVertex(new Point(1250, 745))
                .addVertex(new Point(1300, 745))
                .addVertex(new Point(1300, 900))
                .addVertex(new Point(1250, 900)).build();
        Polygon lowerRightArmPoly = Polygon.Builder()
                .addVertex(new Point(1250, 900))
                .addVertex(new Point(1300, 900))
                .addVertex(new Point(1300, 1000))
                .addVertex(new Point(1250, 1000)).build();
        Polygon rightHandPoly = Polygon.Builder()
                .addVertex(new Point(1250, 1000))
                .addVertex(new Point(1300, 1000))
                .addVertex(new Point(1300, 1050))
                .addVertex(new Point(1250, 1050)).build();
        Polygon upperLeftLegPoly = Polygon.Builder()
                .addVertex(new Point(1500, 745))
                .addVertex(new Point(1550, 745))
                .addVertex(new Point(1550, 900))
                .addVertex(new Point(1500, 900)).build();
        Polygon lowerLeftLegPoly = Polygon.Builder()
                .addVertex(new Point(1500, 900))
                .addVertex(new Point(1550, 900))
                .addVertex(new Point(1550, 1000))
                .addVertex(new Point(1500, 1000)).build();
        Polygon leftFeetPoly = Polygon.Builder()
                .addVertex(new Point(1500, 1000))
                .addVertex(new Point(1550, 1000))
                .addVertex(new Point(1550, 1050))
                .addVertex(new Point(1500, 1050)).build();
        Polygon upperRightLegPoly = Polygon.Builder()
                .addVertex(new Point(1600, 745))
                .addVertex(new Point(1650, 745))
                .addVertex(new Point(1650, 900))
                .addVertex(new Point(1600, 900)).build();
        Polygon lowerRightLegPoly = Polygon.Builder()
                .addVertex(new Point(1600, 900))
                .addVertex(new Point(1650, 900))
                .addVertex(new Point(1650, 1000))
                .addVertex(new Point(1600, 1000)).build();
        Polygon rightFeetPoly = Polygon.Builder()
                .addVertex(new Point(1600, 1000))
                .addVertex(new Point(1650, 1000))
                .addVertex(new Point(1650, 1050))
                .addVertex(new Point(1600, 1050)).build();

        Model torso = new Model("torso", new Point(0, 0), 0, torsoPoly);
        Parts.add(torso);
        Model head = new Model("head", new Point(1075, 550), 25, headPoly);
        Parts.add(head);
        Model upperLeftArm = new Model("upperArm", new Point(1175, 745), 360, upperLeftArmPoly);
        Parts.add(upperLeftArm);
        Model lowerLeftArm = new Model("lowerArm", new Point(1175, 900), 135, lowerLeftArmPoly);
        Parts.add(lowerLeftArm);
        Model leftHand = new Model("hand", new Point(1175, 1025), 35, leftHandPoly);
        Parts.add(leftHand);
        Model upperRightArm = new Model("upperArm", new Point(1275, 745), 360, upperRightArmPoly);
        Parts.add(upperRightArm);
        Model lowerRightArm = new Model("lowerArm", new Point(1275, 900), 30, lowerRightArmPoly);
        Parts.add(lowerRightArm);
        Model rightHand = new Model("hand", new Point(1275, 1000), 35, rightHandPoly);
        Parts.add(rightHand);
        Model upperLeftLeg = new Model("upperLeg", new Point(1525, 745), 90, upperLeftLegPoly);
        Parts.add(upperLeftLeg);
        Model lowerLeftLeg = new Model("lowerLeg", new Point(1525, 900), 90, lowerLeftLegPoly);
        Parts.add(lowerLeftLeg);
        Model leftFeet = new Model("feet", new Point(1525, 1000), 35, leftFeetPoly);
        Parts.add(leftFeet);
        Model upperRightLeg = new Model("upperLeg", new Point(1625, 745), 90, upperRightLegPoly);
        Parts.add(upperRightLeg);
        Model lowerRightLeg = new Model("lowerLeg", new Point(1625, 900), 90, lowerRightLegPoly);
        Parts.add(lowerRightLeg);
        Model rightFeet = new Model("feet", new Point(1625, 1000), 35, rightFeetPoly);
        Parts.add(rightFeet);

        // set up the children
        torso.addChildren(head);
        torso.addChildren(upperLeftArm);
        upperLeftArm.addChildren(lowerLeftArm);
        lowerLeftArm.addChildren(leftHand);
        torso.addChildren(upperRightArm);
        upperRightArm.addChildren(lowerRightArm);
        lowerRightArm.addChildren(rightHand);
        torso.addChildren(upperLeftLeg);
        upperLeftLeg.addChildren(lowerLeftLeg);
        lowerLeftLeg.addChildren(leftFeet);
        torso.addChildren(upperRightLeg);
        upperRightLeg.addChildren(lowerRightLeg);
        lowerRightLeg.addChildren(rightFeet);
    }

    //
    // Reset the current figure.
    //
    public void reset() {
        for (Model m: Parts) {
            m.reset();
        }
    }

    //
    // Clear the selected body part.
    //
    public void clear() {
        Parts.clear();
        currSelectedPolygon = null;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // could handle input here
        float x = event.getX(0);
        float y = event.getY(0);
        float x2;
        float y2;


        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                for(Model model: Parts) {
                    if(model.contains(x, y)) {
                        currSelectedPolygon = model;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(currSelectedPolygon != null && currSelectedPolygon.getID()  == "torso") {
                    currSelectedPolygon.translate(x, y, startX, startY);
                }
                else if(currSelectedPolygon != null) {
                   currSelectedPolygon.rotate(x, y, startX, startY);
                }
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_UP:
                currSelectedPolygon = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                x2 = event.getX(1);
                y2 = event.getY(1);
                for(Model model: Parts) {
                    if(model.contains(x2,y2)) {
                        secondSelectedPolygon = model;
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                secondSelectedPolygon = null;
                break;
        }
        scaleDetector.onTouchEvent(event);

        postInvalidate();
        return true;
    }

    // https://developer.android.com/training/gestures/scale
    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        float scaleY = 0;

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            scaleY = detector.getCurrentSpanY();
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if(currSelectedPolygon!=null && secondSelectedPolygon!=null
                    && currSelectedPolygon == secondSelectedPolygon) {
                float y = detector.getCurrentSpanY();
                currSelectedPolygon.scale(y, scaleY);
                scaleY = y;
            }
            invalidate();
            return true;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // tell shapes to draw themselves
        Parts.get(0).draw(canvas, brush);
    }
}
