package com.example.wiezenxi.final_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wiezenxi on 2018/1/2.
 */

public class Group_Create extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);
        Bundle bundle =  this.getIntent().getExtras();
        final String id = (String) bundle.getSerializable("person_id");
        Bmob.initialize(this, "6deb17aa77a0611f459edc068b38389c");
        final Button ok = (Button) findViewById(R.id.new_ok);
        final Button back = (Button) findViewById(R.id.new_back);
        final EditText new_group_name = (EditText) findViewById(R.id.new_group_name);
        ok.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v) {
                final String group_name =new_group_name.getText().toString();
                String[] member_id = new String[0];//空数组
                //增
                Group group = new Group();
                group.setGroup_name(group_name);
                group.setManager_id(id);
                group.setMember_id(member_id);
                group.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(Group_Create.this, "新建group成功" , Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Group_Create.this, "新建group失败？不存在的" , Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                setResult(1);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v)
            {
                finish();
            }
        });
    }
}
