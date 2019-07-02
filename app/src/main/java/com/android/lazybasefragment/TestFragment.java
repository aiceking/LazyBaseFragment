package com.android.lazybasefragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.lazyfragmentlibrary.LazyBaseFragment;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by radio on 2017/10/30.
 */

public class TestFragment extends LazyBaseFragment {


    @BindView(R.id.tv_name)
    TextView tvName;
    private String title;
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
    protected int getLayoutRes() {
        return R.layout.lazy_fragment_main;
    }

    @Override
    protected void initView(View rootView) {
        title=getArguments().getString("title");
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        Log.v("onFragmentResume: ",title);
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        Log.v("onFragmentPause: ",title);

    }

    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        tvName.setText(title+"加载中...");
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

    @OnClick(R.id.tv_name)
    public void onViewClicked() {
    }
}
