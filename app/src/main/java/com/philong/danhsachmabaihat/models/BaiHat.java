package com.philong.danhsachmabaihat.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Long on 6/16/2017.
 */

public class BaiHat implements Parcelable{

    private String mMaBaiHat;
    private String mTenBaiHat;
    private String mLoiBaiHat;
    private String mTacGia;
    private String mTheLoai;
    private int mYeuThich;

    public BaiHat(String maBaiHat, String tenBaiHat, String loiBaiHat, String tacGia, String theLoai, int yeuThich) {
        mMaBaiHat = maBaiHat;
        mTenBaiHat = tenBaiHat;
        mLoiBaiHat = loiBaiHat;
        mTacGia = tacGia;
        mTheLoai = theLoai;
        mYeuThich = yeuThich;
    }

    protected BaiHat(Parcel in) {
        mMaBaiHat = in.readString();
        mTenBaiHat = in.readString();
        mLoiBaiHat = in.readString();
        mTacGia = in.readString();
        mTheLoai = in.readString();
        mYeuThich = in.readInt();
    }

    public static final Creator<BaiHat> CREATOR = new Creator<BaiHat>() {
        @Override
        public BaiHat createFromParcel(Parcel in) {
            return new BaiHat(in);
        }

        @Override
        public BaiHat[] newArray(int size) {
            return new BaiHat[size];
        }
    };

    public String getMaBaiHat() {
        return mMaBaiHat;
    }

    public void setMaBaiHat(String maBaiHat) {
        mMaBaiHat = maBaiHat;
    }

    public String getTenBaiHat() {
        return mTenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        mTenBaiHat = tenBaiHat;
    }

    public String getLoiBaiHat() {
        return mLoiBaiHat;
    }

    public void setLoiBaiHat(String loiBaiHat) {
        mLoiBaiHat = loiBaiHat;
    }

    public String getTacGia() {
        return mTacGia;
    }

    public void setTacGia(String tacGia) {
        mTacGia = tacGia;
    }

    public String getTheLoai() {
        return mTheLoai;
    }

    public void setTheLoai(String theLoai) {
        mTheLoai = theLoai;
    }

    public int getYeuThich() {
        return mYeuThich;
    }

    public void setYeuThich(int yeuThich) {
        mYeuThich = yeuThich;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMaBaiHat);
        dest.writeString(mTenBaiHat);
        dest.writeString(mLoiBaiHat);
        dest.writeString(mTacGia);
        dest.writeString(mTheLoai);
        dest.writeInt(mYeuThich);
    }
}
