package com.example.directmanipulation;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inflate the view
        ViewGroup view_group = findViewById(R.id.canvas);
        view_group.addView(new CanvasView(this.getBaseContext()));
        final CanvasView cv = (CanvasView) view_group.getChildAt(0);

        // button to reset the matrix and figure
        ImageButton reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.reset();
                cv.invalidate();
            }
        });

        // button to choose the robot figure
        ImageButton robot = findViewById(R.id.robot);
        robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.clear();
                cv.initRobot();
                cv.invalidate();
            }
        });

        // button to choose the dog figure
        ImageButton dog = findViewById(R.id.dog);
        dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.clear();
                cv.initDog();
                cv.invalidate();
            }
        });

        ImageButton i = findViewById(R.id.info);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                final View pv = inflater.inflate(R.layout.info, null);
                final PopupWindow pw = new PopupWindow(pv, 350, 200);
                pw.showAtLocation(pv, Gravity.CENTER, 0, 0);

                final View p = pw.getContentView();
                Button dismiss = p.findViewById(R.id.dismiss);
                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });
            }
        });
    }
}

