package com.android.lazybasefragment;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.lazyfragmentlibrary.LazyBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by radio on 2017/10/30.
 */

public class TestFragment extends LazyBaseFragment {
    private String title;

    @BindView(R.id.tv_name)
    TextView tvName;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==10){
                tvName.setText(title+"数据加载完了！");
            }
        }
    };
    @Override
    protected View setFragmentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
         ButterKnife.bind(this, view);
        title=getArguments().getString("title");
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvName.setText("加载中...");
                loadData();
            }
        });
        return view;
    }

    @Override
    protected void loadData() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                    handler.sendEmptyMessage(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    protected void stopLoadData() {

    }


}
