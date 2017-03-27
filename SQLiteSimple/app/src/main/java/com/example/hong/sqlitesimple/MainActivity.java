package com.example.hong.sqlitesimple;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//import static com.example.hong.sqlitesimple.R.id.tv;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqlDB;
    myDBHelper myHelper;
    private ArrayList<String> datas = new ArrayList<String>();


    private String name[] = new String[10000];
    private int age[] = new int[10000];
    Button dbSelect, dbInsert, dbInit, dbWhere, dbMultiple;
    TextView tvSelect, tvInsert, timeInsert, timeSelect, tvWhere, timeWhere, timeMultiple, tvMultiple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHelper = new myDBHelper(this);
        dbSelect = (Button) findViewById(R.id.dbSelect);
        dbInsert = (Button) findViewById(R.id.dbInsert);
        dbInit = (Button) findViewById(R.id.dbInit);
        dbWhere = (Button) findViewById(R.id.dbWhere);
        dbMultiple = (Button) findViewById(R.id.dbMultiple);
        tvSelect = (TextView) findViewById(R.id.tvSelect);
        tvInsert = (TextView) findViewById(R.id.tvInsert);
        tvWhere = (TextView) findViewById(R.id.tvWhere);
        tvMultiple = (TextView) findViewById(R.id.tvMultiple);
        timeInsert = (TextView) findViewById(R.id.timeInsert);
        timeSelect = (TextView) findViewById(R.id.timeSelect);
        timeWhere = (TextView) findViewById(R.id.timeWhere);
        timeMultiple = (TextView) findViewById(R.id.timeMultiple);



        dbSelect.setOnClickListener(onClickListener);
        dbInsert.setOnClickListener(onClickListener);
        dbInit.setOnClickListener(onClickListener);
        dbWhere.setOnClickListener(onClickListener);
        dbMultiple.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            if(view.getId()==R.id.dbInsert){
                //long nStart = 0;
                //long nEnd = 0;
                //nStart = System.currentTimeMillis();
                //Log.d("nStart", String.valueOf(nStart));


                InputStream in = getResources().openRawResource(R.raw.abcd30000);
                inputJson(in);
                //nEnd = System.currentTimeMillis();
                //Log.d("nEnd", String.valueOf(nEnd));
                //Log.d("실행시간:", String.valueOf (nEnd - nStart));
                //timeInsert.setText(String.valueOf (nEnd - nStart) + "ms");

            }else if(view.getId()==R.id.dbSelect){
                long nStart = 0;
                long nEnd = 0;
                nStart = System.currentTimeMillis();
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM testTBL;", null);
                String strIndex = "Index" + "\r\n" + "--------" + "\r\n";
                String strContent = "Content" + "\r\n" + "--------" + "\r\n";
                while (cursor.moveToNext()){
                    strIndex += cursor.getInt(0) + "\r\n";
                    strContent += cursor.getString(1) + "\r\n";
                }
                nEnd = System.currentTimeMillis();
                //tvSelect.setText(strIndex + "      " + strContent);
                timeSelect.setText(String.valueOf (nEnd - nStart) + "ms");


            }else if(view.getId()==R.id.dbInit) {
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "DB초기화 완료.", Toast.LENGTH_SHORT).show();
            }else if(view.getId()==R.id.dbWhere){
                long nStart = 0;
                long nEnd = 0;
                nStart = System.currentTimeMillis();

                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM testTBL WHERE id % 3=0;", null);

                String strIndex = "Index" + "\r\n" + "--------" + "\r\n";
                String strContent = "Content" + "\r\n" + "--------" + "\r\n";
                while (cursor.moveToNext()){
                    strIndex += cursor.getInt(0) + "\r\n";
                    strContent += cursor.getString(1) + "\r\n";
                }

                //tvWhere.setText(strIndex + "      " + strContent);
                nEnd = System.currentTimeMillis();
                timeWhere.setText(String.valueOf (nEnd - nStart) + "ms");
            } else if(view.getId() == R.id.dbMultiple) {
                long nStart = 0;
                long nEnd = 0;
                nStart = System.currentTimeMillis();
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("select * from testTBL where id between 600 AND 23100 AND content ='{\"Name\":\"Minwoo\",\"Age\":25}';", null);
                String strIndex = "Index" + "\r\n" + "--------" + "\r\n";
                String strContent = "Content" + "\r\n" + "--------" + "\r\n";
                while (cursor.moveToNext()){
                    strIndex += cursor.getInt(0) + "\r\n";
                    strContent += cursor.getString(1) + "\r\n";
                }
                nEnd = System.currentTimeMillis();
                tvMultiple.setText(strIndex + "      "+ strContent);
                timeMultiple.setText(String.valueOf (nEnd - nStart) + "ms");

            } else {
                System.out.println("hi");
            }

        }
    };

    public void inputJson(InputStream in){
        try{
            //getResources().openRawResource()로 raw 폴더의 원본 파일을 가져온다.

            //InputStream에 넣기 (open)


            //byte
            if(in != null){
                //char
                InputStreamReader stream = new InputStreamReader(in, "utf-8");

                //String
                BufferedReader buffer = new BufferedReader(stream);

                String read;
                StringBuilder sb = new StringBuilder();
                while((read=buffer.readLine())!= null){
                    sb.append(read);
                }

                in.close();

                String json = sb.toString();
                Log.d("JSON", json);

                JSONArray jsonArray = new JSONArray(json);

                String temp_name = "";
                int temp_age = 0;
                String result = "";
                sqlDB = myHelper.getWritableDatabase();
                Log.d("gg", jsonArray.getString(0));
                long nStart = 0;
                long nEnd = 0;
                nStart = System.currentTimeMillis();
                Log.d("nStart", String.valueOf(nStart));
                for(int i=0; i<jsonArray.length(); i++){
                    /*JSONObject firstJson = new JSONObject(jsonArray.getString(i));
                    temp_name = firstJson.getString("Name");
                    temp_age = firstJson.getInt("Age");
                    name[i] = temp_name;
                    age[i] = temp_age;
                    */
                    sqlDB.execSQL("INSERT INTO testTBL VALUES ('" + i +"','"+ jsonArray.getString(i) + "');");
                    //datas.add(temp_name);
                    //datas.add(String.valueOf(temp_age));
                    result += i + "      " + jsonArray.getString(i) + "\n";
                }
                nEnd = System.currentTimeMillis();
                timeInsert.setText(String.valueOf (nEnd - nStart) + "ms");
                sqlDB.close();


                //tvInsert.setText(result);


                Toast.makeText(getApplicationContext(), "DB입력완료", Toast.LENGTH_SHORT).show();

                //JSONObject firstJson = new JSONObject(jsonArray.getString(0));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "testDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE testTBL (id INTEGER PRIMARYKEY, content CHAR(200));");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            for(int i=0; i<name.length; i++){
                name[i]=null;
                age[i]=0;
            }
            db.execSQL("DROP TABLE IF EXISTS testTBL");
            onCreate(db);
        }
    }
}
