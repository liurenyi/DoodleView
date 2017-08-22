package com.lry.doodleview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * http://blog.csdn.net/coderinchina/article/details/53690218
 */

public class DoodleViewActivity extends AppCompatActivity implements View.OnClickListener{

    private DoodleView doodleView;
    private Button undo;
    private Button backUndo;
    private Button resetRedColor;
    private Button resetPaintWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle_view);
        init();
    }

    private void init() {
        doodleView = (DoodleView) findViewById(R.id.doodle_view);

        undo = (Button) findViewById(R.id.undo);
        undo.setOnClickListener(this);

        backUndo = (Button) findViewById(R.id.back_undo);
        backUndo.setOnClickListener(this);

        resetRedColor = (Button) findViewById(R.id.reset_color);
        resetRedColor.setOnClickListener(this);

        resetPaintWidth = (Button) findViewById(R.id.reset_paint_width);
        resetPaintWidth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.undo:
                doodleView.undo();
                break;
            case R.id.back_undo:
                doodleView.backUndo();
                break;
            case R.id.reset_color:
                doodleView.resetColor(Color.RED);
                break;
            case R.id.reset_paint_width:
                doodleView.resetPaintWidth(10);
                break;
        }
    }
}
