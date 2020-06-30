package com.example.zyy19.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zyy19.EdtActivity;
import com.example.zyy19.HttpProxy;
import com.example.zyy19.NetInputUtils;
import com.example.zyy19.R;
import com.example.zyy19.WebActivity;
import com.example.zyy19.bean.VideoInfo;
import com.example.zyy19.bean.VideoListResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NetListActivity extends AppCompatActivity {
    private static final String TAG = "NetListActivity";
    private VideoAdapter mAdapter;
    private Handler mHandler = new Handler();
    private List<VideoInfo> mDataList;
    private TextView mTextName;
    private TextView mSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_list);

        ListView mListView = findViewById(R.id.lv);
        View headLayout = buildListHeader();
        mTextName = headLayout.findViewById(R.id.txt_name);
        mSign = headLayout.findViewById(R.id.txt_sign);
        mListView.addHeaderView(headLayout);
        mDataList = new ArrayList<>();
        mAdapter = new VideoAdapter(mDataList,this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    VideoInfo videoInfo = mDataList.get(position - 1);
                    Intent intent = new Intent(NetListActivity.this, WebActivity.class);
                    intent.putExtra(WebActivity.WEB_URL, videoInfo.getFilePath());
                    startActivity(intent);
                }
            }
        });
        initData();
    }
    private View buildListHeader() {
        View headLayout = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        TextView signTV = headLayout.findViewById(R.id.txt_sign);
        signTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NetListActivity.this,"去设置签名",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NetListActivity.this, EdtActivity.class);
                startActivityForResult(intent,11);
            }
        });
        return headLayout;
    }

    private void initData() {
        String movieUrl = "http://ramedia.sinaapp.com/res/DouBanMovie2.json";
        HttpProxy.getInstance().load(movieUrl,new HttpProxy.NetInputCallback(){
            @Override
            public void onSuccess(InputStream inputStream){
                String respJson = null;
                try{
                    respJson = NetInputUtils.readString(inputStream);
                    Log.i(TAG,"----response json:\n" +respJson);
                    VideoListResponse videoListResponse = convertJsonToBean(respJson);
                    final   List<VideoInfo> list = videoListResponse.getList();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Intent intent = getIntent();
        mTextName.setText(intent.getStringExtra("loginName"));
    }
    private VideoListResponse convertJsonToBean(String json){
        Gson gson = new Gson();
        VideoListResponse response = gson.fromJson(json,VideoListResponse.class);
        return response;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 11: {
                if (RESULT_OK == resultCode) {
                    String sign = data.getStringExtra("mySign");
                    mSign.setText(sign);
                } else {
                    Toast.makeText(this, "取消了设置", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}
