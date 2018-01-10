package com.example.wiezenxi.final_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by wiezenxi on 2018/1/2.
 */

public class Group_DO extends AppCompatActivity {
    private ListView mListView;
    private List<Map<String, Object>> data = new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    private String person_id;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data1) {
        if(requestCode == 1){
            data.clear();
            //遍历ddl表
            BmobQuery<Group> bmobQuery = new BmobQuery<Group>();
            bmobQuery.findObjects(new FindListener<Group>() {  //按行查询，查到的数据放到List<Goods>的集合
                @Override
                public void done(List<Group> list, BmobException e) {
                    if (e == null){
                        for(int i = 0 ; i < list.size() ; i++){
                            int flag=0;
                            if(list.get(i).getManager_id().equals(person_id)){//是管理员
                                flag=1;
                            }
                            for(int j = 0;j < list.get(i).getMember_id().length && flag==0; j++)
                                if(list.get(i).getMember_id()[j].equals(person_id)){//是成员
                                    flag=1;
                                }

                            if(flag==1){
                                Map<String, Object> temp = new LinkedHashMap<>();
                                temp.put("group_name",list.get(i).getGroup_name());
                                temp.put("manager_id",list.get(i).getManager_id());
                                temp.put("member_id",list.get(i).getMember_id());
                                temp.put("object_id",list.get(i).getObjectId());
                                data.add(temp);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                }
            });
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);
        Bmob.initialize(this, "6deb17aa77a0611f459edc068b38389c");
        Bundle bundle =  this.getIntent().getExtras();
        person_id = (String) bundle.getSerializable("person_id");
        final Button add = (Button) findViewById(R.id.add_group);
        final Button create = (Button) findViewById(R.id.create_group);
        simpleAdapter=new SimpleAdapter(this,data,R.layout.group_item,
                new String[]{"group_name"},
                new int[]{R.id.group_name});
        mListView = (ListView)findViewById(R.id.groups);
        mListView.setAdapter(simpleAdapter);
        //遍历group表
        BmobQuery<Group> bmobQuery = new BmobQuery<Group>();
        bmobQuery.findObjects(new FindListener<Group>() {  //按行查询，查到的数据放到List<Goods>的集合
            @Override
            public void done(List<Group> list, BmobException e) {
                if (e == null){
                    Log.i("taglistsize=", String.valueOf(list.size()));
                    for(int i = 0 ; i < list.size() ; i++){
                        int flag=0;
                        Log.i("tagperson_id=", String.valueOf(person_id));
                        Log.i("tagmangerid=", list.get(i).getManager_id());
                        if(list.get(i).getManager_id().equals(person_id)){//是管理员
                            flag=1;
                        }
                        for(int j = 0;j < list.get(i).getMember_id().length && flag==0; j++)
                            if(list.get(i).getMember_id()[j].equals(person_id)){//是成员
                                Log.i("tagmemberid=", list.get(i).getMember_id()[j]);
                                flag=1;
                            }
                        Log.i("tagflag=", String.valueOf(flag));
                        if(flag==1){
                            Log.i("tagput","");
                            Map<String, Object> temp = new LinkedHashMap<>();
                            temp.put("group_name",list.get(i).getGroup_name());
                            temp.put("manager_id",list.get(i).getManager_id());
                            temp.put("member_id",list.get(i).getMember_id());
                            temp.put("object_id",list.get(i).getObjectId());
                            data.add(temp);
                            simpleAdapter.notifyDataSetChanged();
                        }
                    }
                }

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Bmob.initialize(Group_DO.this, "6deb17aa77a0611f459edc068b38389c");


                Map<String, Object> map=(Map<String, Object>)parent.getItemAtPosition(position);
                String[] member_id=(String[]) map.get("member_id");

                Intent intent = new Intent(Group_DO.this, Group_Check.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("person_id", person_id);
                bundle.putSerializable("group_name", map.get("group_name").toString());
                bundle.putSerializable("manager_id", map.get("manager_id").toString());
                bundle.putSerializable("member_id", member_id);
                bundle.putSerializable("object_id", map.get("object_id").toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                Bmob.initialize(Group_DO.this, "6deb17aa77a0611f459edc068b38389c");


                LayoutInflater factor = LayoutInflater.from(Group_DO.this);
                View view_in = factor.inflate(R.layout.dialoglayout_deletegroup,null);
                final AlertDialog.Builder alterDialog = new AlertDialog.Builder(Group_DO.this);

                alterDialog.setView(view_in);
                alterDialog.setNegativeButton("确认退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("objectid=", data.get(position).get("object_id").toString());
                        BmobQuery<Group> bmobQuery = new BmobQuery<Group>();
                        bmobQuery.getObject(data.get(position).get("object_id").toString(), new QueryListener<Group>() {
                            @Override
                            public void done(Group object,BmobException e) {
                                if(e==null){
                                    if(person_id.equals(object.getManager_id())){
                                        Group group = new Group();
                                        group.setObjectId(data.get(position).get("object_id").toString());
                                        group.delete(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if(e==null){
                                                    Toast.makeText(Group_DO.this, "解散成功" , Toast.LENGTH_SHORT).show();
                                                    data.remove(position);
                                                    simpleAdapter.notifyDataSetChanged();
                                                }else{
                                                    Toast.makeText(Group_DO.this, "解散失败" , Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        String[] member_id = object.getMember_id();
                                        String[] new_member_id = new String[member_id.length-1];
                                        int j=0;
                                        for(int i=0;i<member_id.length;i++){
                                            if(!member_id[i].equals(person_id)){
                                                new_member_id[j]=member_id[i];
                                            }

                                        }
                                        object.setMember_id(new_member_id);
                                        object.update(data.get(position).get("object_id").toString(),new UpdateListener(){
                                            @Override
                                            public void done(BmobException e) {
                                                if(e==null){
                                                    Toast.makeText(Group_DO.this, "退出成功" , Toast.LENGTH_SHORT).show();
                                                    data.remove(position);
                                                    simpleAdapter.notifyDataSetChanged();
                                                }else{
                                                    Log.i("tag=", e.getMessage());
                                                    Toast.makeText(Group_DO.this, "退出失败" , Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }

                                }else{
                                    Log.i("tag=", e.getMessage());
                                    Toast.makeText(Group_DO.this, "查找失败"+e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }
                })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).create().show();





                simpleAdapter.notifyDataSetChanged();

                return true;
            }
        });


        add.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v)
            {
                Intent intent = new Intent(Group_DO.this, Group_ADD.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("person_id", person_id);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
        create.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v)
            {
                Intent intent = new Intent(Group_DO.this, Group_Create.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("person_id", person_id);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
    }
}
