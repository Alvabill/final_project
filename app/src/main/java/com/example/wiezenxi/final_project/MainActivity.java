package com.example.wiezenxi.final_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //登录页面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "6deb17aa77a0611f459edc068b38389c");
        final Button ok = (Button) findViewById(R.id.main_ok);
        final Button New = (Button) findViewById(R.id.main_new);
        final EditText main_id = (EditText) findViewById(R.id.main_id);
        final EditText main_password = (EditText) findViewById(R.id.main_password);

        //登录按钮
        ok.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v)
            {
               //查
                final String id = main_id.getText().toString();
                final String password = main_password.getText().toString();
                BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
                bmobQuery.findObjects(new FindListener<Person>() {  //按行查询，查到的数据放到List<Goods>的集合
                    @Override
                    public void done(List<Person> list, BmobException e) {
                        //Log.i("tag", e.getMessage());
                        if (e == null){
                            int flag=0;
                            for(int i = 0 ; i < list.size() ; i++){

                                if(list.get(i).getId().equals(id)){
                                    if(list.get(i).getPassword().equals(password)){
                                        Intent intent = new Intent(MainActivity.this, First.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("person_id", id);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        Toast.makeText(MainActivity.this, "密码正确" , Toast.LENGTH_SHORT).show();
                                        flag=1;
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "密码错误" , Toast.LENGTH_SHORT).show();
                                        flag=1;
                                        break;
                                    }

                                }
                            }
                            if(flag==0){
                                Toast.makeText(MainActivity.this, "账号不存在" , Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });
            }
        });

        //跳转到注册页面
        New.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,Add_person.class);
                startActivity(intent);
            }
        });

    }
}
