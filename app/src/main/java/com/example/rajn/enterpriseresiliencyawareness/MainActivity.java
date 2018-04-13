package com.example.rajn.enterpriseresiliencyawareness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView smile = (ImageView) findViewById(R.id.smile);
        smile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MainActivity.this,description.class);
                intent.putExtra("feel","excellent");
                startActivity(intent);
            }
        });

        ImageView hector = (ImageView) findViewById(R.id.hector);
        hector.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MainActivity.this,description.class);
                intent.putExtra("feel","good");
                startActivity(intent);
            }
        });

        ImageView crying = (ImageView) findViewById(R.id.crying);
        crying.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MainActivity.this,description.class);
                intent.putExtra("feel","bad");
                startActivity(intent);
            }
        });
    }
}