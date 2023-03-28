package com.example.gamestate;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Griselda
 * @author Katherine
 * @author Ruth
 * @author Nick
 * @author Ethan
 * @version 3.21.2023
 */

public class MainActivity extends AppCompatActivity {

    private TextView textView; // text displayed in xml
    private Button runTest; // button runs test
    private int clicks; // clicks made on button


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckerView cv = findViewById(R.id.board);

    }


}
