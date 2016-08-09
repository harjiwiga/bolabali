package com.example.harjiwigaasmoko.irabukatoko.handler;

/**
 * Created by harjiwigaasmoko on 8/6/16.
 */
import java.util.ArrayList;
import java.util.List;
import com.example.harjiwigaasmoko.irabukatoko.entity.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "bolabali";

    private static final String TABLE_USERS = "user_parties";

    private static final String KEY_ID = "id";
    private static final String NAME = "judul";
    private static final String EMAIL = "penulis";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BUKU_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_BUKU_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void executeQuery(){

    }

    public void save(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NAME,user.getName());
        values.put(EMAIL,user.getName());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public User findOne(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_USERS, new String[]{KEY_ID, NAME, EMAIL},
                KEY_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }


        return new User("1");
    }

    public List<User> findAll(){
        List<User> listBuku=new ArrayList<User>();
        String query="SELECT * FROM "+ TABLE_USERS;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                User buku=new User();
//                buku.setId(Integer.valueOf(cursor.getString(0)));
//                buku.setJudul(cursor.getString(1));
//                buku.setPenulis(cursor.getString(2));
                listBuku.add(buku);
            }while(cursor.moveToNext());
        }

        return listBuku;
    }

    public void update(User buku){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
//        values.put(NAME, buku.getJudul());
//        values.put(EMAIL, buku.getPenulis());

//        db.update(TABLE_USERS, values, KEY_ID+"=?", new String[]{String.valueOf(buku.getId())});
        db.close();
    }

    public void delete(User buku){
        SQLiteDatabase db=this.getWritableDatabase();
//        db.delete(TABLE_USERS, KEY_ID+"=?", new String[]{String.valueOf(buku.getId())});
        db.close();
    }
}