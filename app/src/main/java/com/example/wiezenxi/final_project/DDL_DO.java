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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.qqtheme.framework.picker.DateTimePicker;

/**
 * Created by wiezenxi on 2018/1/2.
 */

public class DDL_DO extends AppCompatActivity {
    private ListView mListView;
    private List<Map<String, Object>> data = new ArrayList<>();
    private ddlListAdapter mAdapter;
    private EditText title,content;
    private String person_id;
    private TextView time;
    private ImageView edit_time;
    private TextView day_diff;

    private String s_year;
    private String s_month;
    private String s_day;
    private String s_hour;
    private String s_min;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data1) {
        if(requestCode == 1){
            data.clear();
            //遍历ddl表
            BmobQuery<DDL> bmobQuery = new BmobQuery<DDL>();
            bmobQuery.findObjects(new FindListener<DDL>() {  //按行查询，查到的数据放到List<Goods>的集合
                @Override
                public void done(List<DDL> list, BmobException e) {
                    if (e == null){
                        Collections.sort(list);
                        for(int i = 0 ; i < list.size() ; i++){
                            if(list.get(i).getId().equals(person_id)){
                                Map<String, Object> temp = new LinkedHashMap<>();
                                temp.put("title",list.get(i).getTitle());
                                //日期格式处理
                                final int month = list.get(i).getMonth();
                                final String t_month = month < 10 ? "0"+ String.valueOf(month) : String.valueOf(month);
                                final int day = list.get(i).getDay();
                                final String t_day = day < 10 ? "0"+ String.valueOf(day) : String.valueOf(day);
                                final int hour = list.get(i).getHour();
                                final String t_hour = hour < 10 ? "0"+ String.valueOf(hour) : String.valueOf(hour);
                                final int min = list.get(i).getMin();
                                final String t_min = min < 10 ? "0"+ String.valueOf(min) : String.valueOf(min);
                                temp.put("time",list.get(i).getYear()+"/"+t_month+"/"+t_day+" "+t_hour+":"+t_min);
                                temp.put("content",list.get(i).getContent());
                                temp.put("object_id",list.get(i).getObjectId());
                                data.add(temp);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                }
            });
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddl);
        Bmob.initialize(this, "6deb17aa77a0611f459edc068b38389c");
        Bundle bundle =  this.getIntent().getExtras();
        person_id = (String) bundle.getSerializable("person_id");
        final Button add = (Button) findViewById(R.id.ddl_add);
        mAdapter = new ddlListAdapter(this, data);
        mListView = (ListView)findViewById(R.id.ddls);
        mListView.setAdapter(mAdapter);
        //遍历ddl表
        BmobQuery<DDL> bmobQuery = new BmobQuery<DDL>();
        bmobQuery.findObjects(new FindListener<DDL>() {  //按行查询，查到的数据放到List<Goods>的集合
            @Override
            public void done(List<DDL> list, BmobException e) {
                if (e == null){
                    Collections.sort(list);
                    for(int i = 0 ; i < list.size() ; i++){
                        if(list.get(i).getId().equals(person_id)){
                            Map<String, Object> temp = new LinkedHashMap<>();
                            temp.put("title",list.get(i).getTitle());
                            //日期格式处理
                            final int month = list.get(i).getMonth();
                            final String t_month = month < 10 ? "0"+ String.valueOf(month) : String.valueOf(month);
                            final int day = list.get(i).getDay();
                            final String t_day = day < 10 ? "0"+ String.valueOf(day) : String.valueOf(day);
                            final int hour = list.get(i).getHour();
                            final String t_hour = hour < 10 ? "0"+ String.valueOf(hour) : String.valueOf(hour);
                            final int min = list.get(i).getMin();
                            final String t_min = min < 10 ? "0"+ String.valueOf(min) : String.valueOf(min);
                            temp.put("time",list.get(i).getYear()+"/"+t_month+"/"+t_day+" "+t_hour+":"+t_min);

                            temp.put("content",list.get(i).getContent());
                            temp.put("object_id",list.get(i).getObjectId());
                            data.add(temp);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }

            }
        });

        final DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);//24小时值
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Bmob.initialize(DDL_DO.this, "6deb17aa77a0611f459edc068b38389c");
                //自定义布局
                LayoutInflater factor = LayoutInflater.from(DDL_DO.this);
                View view_in = factor.inflate(R.layout.dialoglayout,null);
                final AlertDialog.Builder alterDialog = new AlertDialog.Builder(DDL_DO.this);
                title =(EditText)view_in.findViewById(R.id.title);
                content =(EditText)view_in.findViewById(R.id.content);
                time = (TextView)view_in.findViewById(R.id.time);
                edit_time = (ImageView) view_in.findViewById(R.id.edit_time);
                day_diff = (TextView) view_in.findViewById(R.id.num);



                if(title!=null) title.setText(data.get(position).get("title").toString());
                if(content!=null) content.setText(data.get(position).get("content").toString());
                final String temp_time = data.get(position).get("time").toString();
                if(time!=null) time.setText(temp_time);
                if(day_diff!=null) day_diff.setText(String.valueOf(compareDataToNow(temp_time)));


                /*
                final int year = Integer.parseInt(temp_time.substring(0,4));
                final int month = Integer.parseInt(temp_time.substring(5,7));
                final int day = Integer.parseInt(temp_time.substring(8,10));
                final int hour = Integer.parseInt(temp_time.substring(12,14));
                final int min = Integer.parseInt(temp_time.substring(15,17));
                */

                edit_time.setColorFilter(R.color.colorAccent);
                edit_time.setOnClickListener(new View.OnClickListener() {
                    public void  onClick(View v) {

                        picker.setDateRangeStart(2018, 1, 1);//日期起点
                        picker.setDateRangeEnd(2020, 1,1);//日期终点
                        picker.setTimeRangeStart(0, 0);//时间范围起点
                        picker.setTimeRangeEnd(23, 59);//时间范围终点
                        //picker.setSelectedItem(year,month,day,hour,min);
                        picker.setTextColor(getResources().getColor(R.color.colorAccent));
                        picker.setCanceledOnTouchOutside(true);
                        picker.setCycleDisable(false);

                        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                            @Override
                            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                                //year:年，month:月，day:日，hour:时，minute:分
                                s_year = year;
                                s_month = month;
                                s_day = day;
                                s_hour = hour;
                                s_min = minute;
                                time.setText(year + "/" + month + "/" + day + " " + hour + ":" + minute);
                            }
                        });
                        picker.show();
                    }
                });

                alterDialog.setView(view_in);
                alterDialog.setNegativeButton("确认修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String s_title = title.getText().toString();
                        final String s_content = content.getText().toString();
                        data.get(position).remove("title");
                        data.get(position).remove("content");
                        data.get(position).remove("time");
                        data.get(position).put("title",s_title);
                        data.get(position).put("content",s_content);
                        data.get(position).put("time",time.getText().toString());
                        mAdapter.notifyDataSetChanged();

                        BmobQuery<DDL> bmobQuery = new BmobQuery<DDL>();
                        bmobQuery.findObjects(new FindListener<DDL>() {  //按行查询，查到的数据放到List<Goods>的集合
                            @Override
                            public void done(List<DDL> list, BmobException e) {
                                if (e == null){

                                    for(int i = 0 ; i < list.size() ; i=i+1){

                                        if(list.get(i).getObjectId().equals(data.get(position).get("object_id"))){
                                            DDL ddl = new DDL();
                                            ddl.setTitle(s_title);
                                            ddl.setContent(s_content);
                                            ddl.setYear(Integer.parseInt(s_year));
                                            ddl.setMonth(Integer.parseInt(s_month));
                                            ddl.setDay(Integer.parseInt(s_day));
                                            ddl.setHour(Integer.parseInt(s_hour));
                                            ddl.setDay(Integer.parseInt(s_min));
                                            ddl.update(data.get(position).get("object_id").toString(), new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        Toast.makeText(DDL_DO.this, "修改成功" , Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{
                                                        Toast.makeText(DDL_DO.this, "修改失败" , Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                            break;
                                        }

                                    }
                                }

                            }
                        });



                    }
                })
                        .setPositiveButton("放弃修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).create().show();
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//长按删除ddl
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Bmob.initialize(DDL_DO.this, "6deb17aa77a0611f459edc068b38389c");


                BmobQuery<DDL> bmobQuery = new BmobQuery<DDL>();
                bmobQuery.findObjects(new FindListener<DDL>() {  //按行查询，查到的数据放到List<Goods>的集合
                    @Override
                    public void done(List<DDL> list, BmobException e) {
                        if (e == null){
                            String size = String.valueOf(list.size());

                            for(int i = 0 ; i < list.size() ; i=i+1){

                                if(list.get(i).getObjectId().equals(data.get(position).get("object_id"))){
                                        DDL ddl = new DDL();
                                        ddl.setObjectId(list.get(i).getObjectId());
                                        ddl.delete(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if(e==null){
                                                    Toast.makeText(DDL_DO.this, "删除成功" , Toast.LENGTH_SHORT).show();
                                                    data.remove(position);
                                                    mAdapter.notifyDataSetChanged();
                                                }else{
                                                    Toast.makeText(DDL_DO.this, "删除失败" , Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        break;

                                }

                            }
                        }

                    }
                });



                return true;
            }

        });


        add.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v)
            {
                Intent intent = new Intent(DDL_DO.this, DDL_ADD.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("person_id", person_id);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);

            }
        });
    }

    private long compareDataToNow(String ddldate){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date ddlDate,nowDate;
        long diff=-100l,days=-100l;

        try {
            ddlDate = sdf.parse(ddldate);
            //now time
            String nowStr=sdf.format(new Date());
            nowDate=sdf.parse(nowStr);

            diff = ddlDate.getTime() - nowDate.getTime();
            days = diff / (1000 * 60 * 60 * 24);
            //System.out.println( "相隔："+days+"天");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

}
