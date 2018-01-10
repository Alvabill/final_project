package com.example.wiezenxi.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by wiezenxi on 2018/1/2.
 */

public class First extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        Bundle bundle =  this.getIntent().getExtras();
        final String id = (String) bundle.getSerializable("person_id");
        final Button ddl = (Button) findViewById(R.id.first_ddl);
        final Button group = (Button) findViewById(R.id.first_group);
        ddl.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v)
            {
                Intent intent = new Intent(First.this, DDL_DO.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("person_id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        group.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v)
            {
                Intent intent = new Intent(First.this, Group_DO.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("person_id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
