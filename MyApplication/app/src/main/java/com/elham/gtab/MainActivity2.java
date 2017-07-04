package com.elham.gtab;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elham.seconffilemanager.CompleteActionLib;
import com.elham.seconffilemanager.CustomActivity;
import com.elham.seconffilemanager.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
     Button btn1, btn4;
    EditText txtview;
    ArrayList<String> stock_list;
    private static final int REQUEST_CODE_EXAMPLE = 0x9345;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn1 =(Button) findViewById(R.id.button);

        txtview = (EditText) findViewById(R.id.textView);
        btn4 = (Button) findViewById(R.id.button4);

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity2.this, CustomActivity.class);
                startActivityForResult(intent2, REQUEST_CODE_EXAMPLE);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomActivity customActivity = new CustomActivity();
                customActivity.setOnSelectFiles(new CompleteActionLib() {
                    @Override
                    public void onSelectFiles(ArrayList<String> files) {
                        txtview.setText("");
                        for(int i=0;i<files.size();i++)
                            txtview.append(files.get(i)+ "\n");
                    }
                });

                Intent intent = null;
                try {

                    intent = new Intent(MainActivity2.this, CustomActivity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(intent);

            }
        });


    }


    // on Activity Result ba btn4 kar mikonad
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // First we need to check if the requestCode matches the one we used.
        if (requestCode == REQUEST_CODE_EXAMPLE) {

            // The resultCode is set by the DetailActivity
            // By convention RESULT_OK means that whatever
            // DetailActivity did was executed successfully
            if (resultCode == Activity.RESULT_OK) {
                // Get the result from the returned Intent
                final String result = data.getStringExtra(CustomActivity.EXTRA_DATA);

                stock_list = data.getStringArrayListExtra("key");
                txtview.setText("");
                for(int i=0;i<stock_list.size();i++)
                {

                    txtview.append(stock_list.get(i)+ "\n");

                }


                // Use the data - in this case, display it in a Toast.
                Toast.makeText(this, "Result: " + result, Toast.LENGTH_LONG).show();
            } else {
                // setResult wasn't successfully executed by DetailActivity
                // Due to some error or flow of control. No data to retrieve.
            }
        }
    }

}
