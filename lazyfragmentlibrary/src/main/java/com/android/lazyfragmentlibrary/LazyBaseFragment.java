package com.android.lazyfragmentlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = setFragmentView(inflater,container);
            isInit=true;
            isCanLoad();
        }
        return rootView;
    }
    protected abstract View setFragmentView(LayoutInflater inflater,@Nullable ViewGroup container);

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
                    isLoad=true;
                }
            }else{
                if (isLoad){
                    stopLoadData();
                }
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
    }
    protected abstract void loadData();
    protected abstract void stopLoadData();

}
