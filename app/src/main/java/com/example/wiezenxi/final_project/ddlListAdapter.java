package com.example.wiezenxi.final_project;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/9.
 */

public class ddlListAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list;

    public ddlListAdapter(Context context, List<Map<String,Object>> data) {
        this.context = context;
        this.list = data;
    }

    public void updateData(List<Map<String,Object>> data) {
        this.list = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        if(list == null) {
            return null;
        }
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        ViewHolder viewHolder;

        if(view == null) {  //当view为空时加载布局，创建ViewHolder并获取布局中的3个子View
            convertView = LayoutInflater.from(context).inflate(R.layout.ddl_item, null);
            viewHolder = new ViewHolder();
            viewHolder.ddlTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.ddlTime = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder); //setTag把处理好的holder配给convertView
        }
        else {  //view不空时直接获取布局，并得到对应的ViewHolder
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //从holder中取出对应的子View并进行赋值
        viewHolder.ddlTitle.setText(list.get(i).get("title").toString());
        viewHolder.ddlTime.setText(list.get(i).get("time").toString());

        return convertView;
    }

    private class ViewHolder {
        public TextView ddlTitle;
        public TextView ddlTime;
    }
}
