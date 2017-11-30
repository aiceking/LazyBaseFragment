package com.android.lazyfragmentlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by radio on 2017/10/30.
 */

public abstract class LazyBaseFragment extends Fragment {
    protected boolean isInit;
    protected boolean isLoad;
    private View rootView;
    private String title;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        title=getArguments().getString("title");
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = setFragmentView(inflater,container);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInit=true;
        Log.v(title,"onViewCreated");
        isCanLoad();
    }

    protected abstract View setFragmentView(LayoutInflater inflater, @Nullable ViewGroup container);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoad();
    }

    private void isCanLoad() {
        if (isInit){
            if (getUserVisibleHint()){
                if (!isLoad){
                    loadData();
                    Log.v(title,"loading");
                    isLoad=true;
                }
            }else{
                if (isLoad){
                    stopLoadData();
                    Log.v(title,"stoploading");
                }
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
        Log.v(title,"onDestroyView");
    }
    protected abstract void loadData();
    protected abstract void stopLoadData();

}
