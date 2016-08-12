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
import android.os.Build;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_NAME = "bolabali";

    private static final String TABLE_USERS = "users";

    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private static DatabaseHandler handlerInstance;

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        Log.i("instance","getinstance");
        Log.i("instance","dbPath: "+context.getDatabasePath(DATABASE_NAME));
        if (handlerInstance == null) {
            handlerInstance = new DatabaseHandler(context.getApplicationContext());

        }
        return handlerInstance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBHandler","con create");

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
        Log.i("DBHandler","con create");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion!=newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }


    private void executeQuery(){

    }

    public void save(User user){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values=new ContentValues();
        Log.i(getDatabaseName(),"save values");
//        db.beginTransaction();
        try{
            values.put(NAME,user.getName());
            values.put(EMAIL, user.getEmail());
//            db.insert(TABLE_USERS,null,values);
            Log.i("save"," success insert:"+ String.valueOf(db.insertOrThrow(TABLE_USERS, null, values)));
        }catch (Exception e){
            Log.d("sqlite", "Error while trying to add post to database");
        }finally {
//            db.endTransaction();
            db.close();
        }

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

    //todo harji : finish the find all Query

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