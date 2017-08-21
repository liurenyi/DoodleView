package com.lry.doodleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DoodleViewActivity extends AppCompatActivity implements View.OnClickListener{

    private DoodleView doodleView;
    private Button undo;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.undo:
                doodleView.undo();
                break;
        }
    }
}
