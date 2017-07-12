package com.philong.danhsachmabaihat.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.philong.danhsachmabaihat.R;
import com.philong.danhsachmabaihat.fragments.YeuThichFragment;
import com.philong.danhsachmabaihat.models.BaiHat;
import com.philong.danhsachmabaihat.utils.SqliteDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 6/16/2017.
 */

public class BaiHatAdapter extends RecyclerView.Adapter<BaiHatAdapter.BaiHatViewHolder> implements Filterable{


    private List<BaiHat> mBaiHatList;
    private List<BaiHat> mBaiHatTimKiemList;
    private Context mContext;
    private SqliteDbHelper mSqliteDbHelper;


    public BaiHatAdapter(List<BaiHat> baiHatList, Context context) {
        mBaiHatList = baiHatList;
        mBaiHatTimKiemList = baiHatList;
        mContext = context;
        mSqliteDbHelper = new SqliteDbHelper(context);
    }


    @Override
    public BaiHatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bai_hat, parent, false);
        return new BaiHatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaiHatViewHolder holder, int position) {
        final BaiHat baiHat = mBaiHatTimKiemList.get(position);
        holder.txtTenBaiHat.setText(baiHat.getTenBaiHat());
        holder.txtLoiBaiHat.setText(baiHat.getLoiBaiHat());
        holder.txtMaBaiHat.setText(baiHat.getMaBaiHat());
        if(baiHat.getYeuThich() == 0){
            holder.imgYeuThich.setImageResource(R.drawable.ic_favorite_border);
        }else{
            holder.imgYeuThich.setImageResource(R.drawable.ic_favorite);
        }
        holder.imgYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(baiHat.getYeuThich() == 0){
                    baiHat.setYeuThich(1);
                    holder.imgYeuThich.setImageResource(R.drawable.ic_favorite);
                    Intent intent = new Intent(YeuThichFragment.BROAD_CAST_YEU_THICH);
                    intent.putExtra("BaiHat", baiHat);
                    mContext.sendBroadcast(intent);
                }else{
                    baiHat.setYeuThich(0);
                    holder.imgYeuThich.setImageResource(R.drawable.ic_favorite_border);
                    Intent intent = new Intent(YeuThichFragment.BROAD_CAST_BO_YEU_THICH);
                    intent.putExtra("BaiHat", baiHat);
                    mContext.sendBroadcast(intent);
                }
                mSqliteDbHelper.updateBaiHatYeuThich(baiHat.getMaBaiHat(), baiHat.getYeuThich());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBaiHatTimKiemList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                System.out.println(charString);
                if(charString.isEmpty()){
                    mBaiHatTimKiemList = mBaiHatList;
                }else{
                    List<BaiHat> filterList = new ArrayList<>();
                    for(BaiHat baiHat : mBaiHatList){
                        if(baiHat.getTenBaiHat().toLowerCase().contains(charString)){
                            filterList.add(baiHat);
                        }
                    }
                    mBaiHatTimKiemList = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mBaiHatTimKiemList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mBaiHatTimKiemList = (List<BaiHat>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class BaiHatViewHolder extends RecyclerView.ViewHolder{

        public TextView txtMaBaiHat;
        public TextView txtTenBaiHat;
        public TextView txtLoiBaiHat;
        public ImageView imgYeuThich;

        public BaiHatViewHolder(View itemView) {
            super(itemView);
            txtMaBaiHat = (TextView) itemView.findViewById(R.id.txtMaBaiHat);
            txtTenBaiHat = (TextView) itemView.findViewById(R.id.txtTenBaiHat);
            txtLoiBaiHat = (TextView) itemView.findViewById(R.id.txtLoiBatHat);
            imgYeuThich = (ImageView) itemView.findViewById(R.id.imgYeuThich);
        }
    }
}
