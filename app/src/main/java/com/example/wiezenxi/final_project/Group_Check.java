package com.example.wiezenxi.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.bmob.v3.Bmob;

/**
 * Created by wiezenxi on 2018/1/4.
 */

public class Group_Check extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_group);
        Bmob.initialize(this, "6deb17aa77a0611f459edc068b38389c");
        Bundle bundle =  this.getIntent().getExtras();
        final String person_id = (String) bundle.getSerializable("person_id");
        final String group_name = (String) bundle.getSerializable("group_name");
        final String manager_id = (String) bundle.getSerializable("manager_id");
        final String[] member_id = (String[]) bundle.getSerializable("member_id");
        final TextView  name = (TextView) findViewById(R.id.group_name);
        final TextView id = (TextView) findViewById(R.id.manager_id);
        final Button ddl = (Button) findViewById(R.id.ddl);
        name.setText("CLASS: "+group_name);
        id.setText("USER ID: "+manager_id);
        if(!manager_id.equals(person_id)){
            ddl.setVisibility(View.GONE);
        }
        ddl.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v)
            {
                Intent intent = new Intent(Group_Check.this, DDL_Send.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("member_id", member_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
