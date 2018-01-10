package com.example.wiezenxi.final_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.qqtheme.framework.picker.DateTimePicker;

/**
 * Created by wiezenxi on 2018/1/4.
 */

public class DDL_Send extends AppCompatActivity{
    private String s_year;
    private String s_month;
    private String s_day;
    private String s_hour;
    private String s_min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ddl);
        Bundle bundle = this.getIntent().getExtras();
        final String[] member_id = (String[]) bundle.getSerializable("member_id");
        Bmob.initialize(this, "6deb17aa77a0611f459edc068b38389c");

        final Button ok = (Button) findViewById(R.id.add_ok);
        //final Button back = (Button) findViewById(R.id.add_back);
        final EditText add_title = (EditText) findViewById(R.id.add_title);
        final EditText add_content = (EditText) findViewById(R.id.add_content);

        final TextView time_pick = (TextView) findViewById(R.id.time_pick);

        final DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);//24小时值
        time_pick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                picker.setDateRangeStart(2018, 1, 1);//日期起点
                picker.setDateRangeEnd(2020, 1, 1);//日期终点
                picker.setTimeRangeStart(0, 0);//时间范围起点
                picker.setTimeRangeEnd(23, 59);//时间范围终点
                picker.setTextColor(getResources().getColor(R.color.colorAccent));
                picker.setCanceledOnTouchOutside(true);
                picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                    @Override
                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                        //year:年，month:月，day:日，hour:时，minute:分
                        s_year = year;
                        s_month = month;
                        s_day = day;
                        s_hour = hour;
                        s_min = minute;
                        time_pick.setText(year + "/" + month + "/" + day + " " + hour + ":" + minute);
                        /*
                        Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day + " "
                                + hour + ":" + minute, Toast.LENGTH_LONG).show();
                                */
                    }
                });
                picker.show();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String title = add_title.getText().toString();
                final String content = add_content.getText().toString();

                final int year = Integer.parseInt(s_year);
                final int month = Integer.parseInt(s_month);
                final int day = Integer.parseInt(s_day);
                final int hour = Integer.parseInt(s_hour);
                final int min = Integer.parseInt(s_min);
                //增
                for (int i = 0; i < member_id.length; i++) {
                    DDL ddl = new DDL();
                    ddl.setId(member_id[i]);
                    ddl.setTitle(title);
                    ddl.setContent(content);
                    ddl.setYear(year);
                    ddl.setMonth(month);
                    ddl.setDay(day);
                    ddl.setHour(hour);
                    ddl.setMin(min);
                    ddl.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(DDL_Send.this, "发送成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(DDL_Send.this, "发送失败", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                finish();
            }
        });
        /*
        back.setOnClickListener(new View.OnClickListener( ){
            public void  onClick(View v)
            {
                finish();
            }
        });
        */
    }
}
