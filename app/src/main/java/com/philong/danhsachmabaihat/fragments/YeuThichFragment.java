package com.philong.danhsachmabaihat.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philong.danhsachmabaihat.R;
import com.philong.danhsachmabaihat.adapters.BaiHatAdapter;
import com.philong.danhsachmabaihat.models.BaiHat;
import com.philong.danhsachmabaihat.utils.SqliteDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 6/16/2017.
 */

public class YeuThichFragment extends Fragment{

    public static final String BROAD_CAST_YEU_THICH = "com.philong.broad_cast_yeu_thich";
    public static final String BROAD_CAST_BO_YEU_THICH = "com.philong.broad_cast_bo_yeu_thich";

    private RecyclerView mRecyclerViewYeuThich;
    private BaiHatAdapter mAdapter;
    private List<BaiHat> mBaiHatYeuThichList;
    private SqliteDbHelper mSqliteDbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcastYeuThich();
        registerBoYeuThich();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mBroadcastReceiverYeuThich);
        getContext().unregisterReceiver(mBroadcastReceiverBoYeuThich);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yeu_thich, container, false);
        mSqliteDbHelper = new SqliteDbHelper(getContext());
        mBaiHatYeuThichList = new ArrayList<>();
        mBaiHatYeuThichList = mSqliteDbHelper.getBaiHatYeuThich();
        mRecyclerViewYeuThich = (RecyclerView) view.findViewById(R.id.recyclerViewYeuThich);
        mRecyclerViewYeuThich.setHasFixedSize(true);
        mRecyclerViewYeuThich.setNestedScrollingEnabled(false);
        mRecyclerViewYeuThich.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewYeuThich.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new BaiHatAdapter(mBaiHatYeuThichList, getContext());
        mRecyclerViewYeuThich.setAdapter(mAdapter);
        return view;
    }

    private BroadcastReceiver mBroadcastReceiverYeuThich  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                BaiHat baiHat = intent.getParcelableExtra("BaiHat");
                mBaiHatYeuThichList.add(baiHat);
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    private void registerBroadcastYeuThich(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(YeuThichFragment.BROAD_CAST_YEU_THICH);
        getContext().registerReceiver(mBroadcastReceiverYeuThich, intentFilter);
    }

    private BroadcastReceiver mBroadcastReceiverBoYeuThich = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                BaiHat baiHat = intent.getParcelableExtra("BaiHat");
                for(BaiHat baiHat1 : mBaiHatYeuThichList){
                    if(baiHat1.getMaBaiHat().equals(baiHat.getMaBaiHat())){
                        mBaiHatYeuThichList.remove(baiHat1);
                        mAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    };

    private void registerBoYeuThich(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(YeuThichFragment.BROAD_CAST_BO_YEU_THICH);
        getContext().registerReceiver(mBroadcastReceiverBoYeuThich, intentFilter);
    }
}
