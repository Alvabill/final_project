package com.example.wiezenxi.final_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wiezenxi on 2018/1/2.
 */

public class Add_person extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_person);
        final Button ok = (Button) findViewById(R.id.new_ok);
        final Button back = (Button) findViewById(R.id.new_back);
        final EditText new_id = (EditText) findViewById(R.id.new_id);
        final EditText new_password = (EditText) findViewById(R.id.new_password);
        ok.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v) {
                //增
                final String newid = new_id.getText().toString();
                final String newpassword = new_password.getText().toString();
                Person person = new Person();
                person.setId(newid);
                person.setPassword(newpassword);
                person.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(Add_person.this, "创建用户成功，新ID为" + objectId, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Add_person.this, "存在相同用户名，创建用户失败：" , Toast.LENGTH_SHORT).show();

                        }
                    }
                });
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
