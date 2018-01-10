package com.example.wiezenxi.final_project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
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

public class Group_ADD extends AppCompatActivity {
    private SearchView mSearchView;
    private ListView mListView;
    private List<Map<String, Object>> data = new ArrayList<>();
    private SimpleAdapter simpleAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);
        Bmob.initialize(this, "6deb17aa77a0611f459edc068b38389c");
        Bundle bundle = this.getIntent().getExtras();
        final String person_id = (String) bundle.getSerializable("person_id");
        simpleAdapter=new SimpleAdapter(this,data,R.layout.group_item,
                new String[]{"group_name"},
                new int[]{R.id.group_name});
        mListView = (ListView)findViewById(R.id.groups);
        mListView.setAdapter(simpleAdapter);
        mListView.setTextFilterEnabled(true);
        mSearchView = (SearchView) findViewById(R.id.searchView);

        //遍历group表
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

                        if(flag==0){
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
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    mListView.setFilterText(newText);
                } else {
                    mListView.clearTextFilter();
                }
                return false;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                Bmob.initialize(Group_ADD.this, "6deb17aa77a0611f459edc068b38389c");
//自定义布局
                LayoutInflater factor = LayoutInflater.from(Group_ADD.this);
                View view_in = factor.inflate(R.layout.dialoglayout_group,null);
                final AlertDialog.Builder alterDialog = new AlertDialog.Builder(Group_ADD.this);
                alterDialog.setView(view_in);
                alterDialog.setNegativeButton("确认加入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> map=(Map<String, Object>)parent.getItemAtPosition(position);
                        String name=map.get("group_name").toString();

                        String objectid=map.get("object_id").toString();
                        /*
                        for(int i = 0; i <data.size() ; i++){
                            Log.i("111dataname=", data.get(i).get("group_name").toString());
                            Log.i("111name=", name);
                            Log.i("111objectid=", data.get(i).get("object_id").toString());
                            if(data.get(i).get("group_name").equals(name)){
                                objectid=data.get(i).get("object_id").toString();
                                break;
                            }
                        }
*/
                        final String id = objectid;
                        BmobQuery<Group> bmobQuery = new BmobQuery<Group>();
                        bmobQuery.getObject(objectid, new QueryListener<Group>() {
                            @Override
                            public void done(Group object,BmobException e) {
                                if(e==null){
                                    String[] member_id = object.getMember_id();
                                    member_id= Arrays.copyOf(member_id,member_id.length+1);
                                    member_id[member_id.length-1]=person_id;
                                    object.setMember_id(member_id);
                                    object.update(id,new UpdateListener(){
                                        @Override
                                        public void done(BmobException e) {
                                            if(e==null){
                                                Toast.makeText(Group_ADD.this, "加入成功" , Toast.LENGTH_SHORT).show();
                                                setResult(1);
                                                finish();
                                            }else{
                                                Toast.makeText(Group_ADD.this, "加入失败" , Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(Group_ADD.this, "查找失败" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).create().show();
            }
        });

    }
}
