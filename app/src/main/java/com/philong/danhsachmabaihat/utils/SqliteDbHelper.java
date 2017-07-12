package com.philong.danhsachmabaihat.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.philong.danhsachmabaihat.models.BaiHat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 6/16/2017.
 */

public class SqliteDbHelper {


    private static final String DB_NAME = "Arirang.sqlite";

    private Context context;

    public SqliteDbHelper(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath(DB_NAME);

        if (!dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(dbFile);
        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }


    public List<BaiHat> getDanhSachBaiHat(){
        SQLiteDatabase db = openDatabase();
        List<BaiHat> baiHats = new ArrayList<>();
        String sql = "SELECT * FROM ArirangSongList";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String maBaiHat = cursor.getString(cursor.getColumnIndex("MABH"));
            String tenBaiHat = cursor.getString(cursor.getColumnIndex("TENBH"));
            String loiBaiHat = cursor.getString(cursor.getColumnIndex("LOIBH"));
            String tacGia = cursor.getString(cursor.getColumnIndex("TACGIA"));
            String theLoai = cursor.getString(cursor.getColumnIndex("THELOAI"));
            int yeuThich = cursor.getInt(cursor.getColumnIndex("YEUTHICH"));
            baiHats.add(new BaiHat(maBaiHat, tenBaiHat, loiBaiHat, tacGia, theLoai, yeuThich));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return baiHats;
    }

    public void updateBaiHatYeuThich(String maBH, int yeuThich){
        SQLiteDatabase db = openDatabase();
        ContentValues values = new ContentValues();
        values.put("YEUTHICH", yeuThich);
        db.update("ArirangSongList", values, "MABH = ?", new String[]{maBH});
        db.close();
    }

    public List<BaiHat> getBaiHatYeuThich(){
        List<BaiHat> listBaiHat = new ArrayList<>();
        SQLiteDatabase db = openDatabase();
        String sql = "SELECT * FROM ArirangSongList WHERE YEUTHICH = 1";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while((!cursor.isAfterLast())){
            String maBaiHat = cursor.getString(cursor.getColumnIndex("MABH"));
            String tenBaiHat = cursor.getString(cursor.getColumnIndex("TENBH"));
            String loiBaiHat = cursor.getString(cursor.getColumnIndex("LOIBH"));
            String tacGia = cursor.getString(cursor.getColumnIndex("TACGIA"));
            String theLoai = cursor.getString(cursor.getColumnIndex("THELOAI"));
            int yeuThich = cursor.getInt(cursor.getColumnIndex("YEUTHICH"));
            listBaiHat.add(new BaiHat(maBaiHat, tenBaiHat, loiBaiHat, tacGia, theLoai, yeuThich));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return listBaiHat;
    }
}
