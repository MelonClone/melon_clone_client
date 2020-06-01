package com.devgd.melonclone;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button mainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_player);

//        mainBtn = findViewById(R.id.main_btn);
        /*mainBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("TEST", event.getActionMasked()+"");
                switch (event.getActionMasked()) {
                    case ACTION_UP:
                        mainBtn.setShapeType(ShapeType.FLAT);
                        mainBtn.setShadowColorDark(getColor(R.color.colorDark));
                        mainBtn.setShadowColorLight(getColor(R.color.colorLight));
                        v.performClick();
                        break;
                    case ACTION_DOWN:
                    default:
                        mainBtn.setShapeType(ShapeType.PRESSED);
                        mainBtn.setTranslationZ(0);
                        mainBtn.setShadowColorDark(getColor(R.color.colorMain));
                        mainBtn.setShadowColorLight(getColor(R.color.colorMain));
                        break;
                }
                return true;
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
