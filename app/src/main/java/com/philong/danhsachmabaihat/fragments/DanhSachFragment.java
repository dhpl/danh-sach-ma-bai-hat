package com.philong.danhsachmabaihat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class DanhSachFragment extends Fragment {

    private RecyclerView mRecyclerViewDanhSach;
    private BaiHatAdapter mAdapter;
    private List<BaiHat> mBaiHatList;
    private Toolbar mToolbar;
    private SqliteDbHelper mSqliteDbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach, container, false);
        setHasOptionsMenu(true);
        //setup toolbar
        mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        if(((AppCompatActivity)getActivity()).getSupportActionBar() == null){
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        }

        //setup recyclerview
        mSqliteDbHelper = new SqliteDbHelper(getContext());
        mBaiHatList = new ArrayList<>();
        mBaiHatList = mSqliteDbHelper.getDanhSachBaiHat();
        mRecyclerViewDanhSach = (RecyclerView) view.findViewById(R.id.recyclerViewDanhSach);
        mRecyclerViewDanhSach.setHasFixedSize(true);
        mRecyclerViewDanhSach.setNestedScrollingEnabled(false);
        mRecyclerViewDanhSach.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewDanhSach.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new BaiHatAdapter(mBaiHatList, getContext());
        mRecyclerViewDanhSach.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
