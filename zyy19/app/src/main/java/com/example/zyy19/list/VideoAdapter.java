package com.example.zyy19.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zyy19.ImageLoader;
import com.example.zyy19.R;
import com.example.zyy19.bean.VideoInfo;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends BaseAdapter {
    private String TAG =" VideoAdapter";
    private List<VideoInfo> mDataList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public VideoAdapter(List<VideoInfo> data, Context context) {
        this.mDataList = data;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        int count = null ==mDataList ? 0 :mDataList.size();
        Log.i(TAG,"----getCount() "+count);
        return count;
    }

    @Override
    public VideoInfo getItem(int position) {
        Log.i(TAG,"----getItem() "+position);
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG,"----getItemId() "+position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG,"----getView() "+position+" convertView: "+convertView);
        ViewHolder holder;
        if(null==convertView) {
            convertView = mInflater.inflate(R.layout.item_news, null);
            holder = new ViewHolder();

            holder.iconTV = convertView.findViewById(R.id.iv_icon);
            holder.tltTV = convertView.findViewById(R.id.tv_title);
            holder.profileTV = convertView.findViewById(R.id.tv_profile);
            convertView.setTag(holder);
        }else {
            holder =(ViewHolder)convertView.getTag();
        }
        VideoInfo item = mDataList.get(position);
        holder.tltTV.setText(item.getTitle());
        holder.profileTV.setText(item.getProfile());
        ImageLoader.getInstance().load(holder.iconTV,item.getThumbPath());

        return convertView;
    }
    private class ViewHolder{
        ImageView iconTV;
        TextView tltTV;
        TextView profileTV;
    }
    public void setData(List<VideoInfo> list){
        mDataList.clear();
        if(null!=list){
            mDataList.addAll(list);
        }
    }
}