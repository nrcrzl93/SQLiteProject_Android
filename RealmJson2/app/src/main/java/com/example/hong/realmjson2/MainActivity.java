package com.example.hong.realmjson2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;

public class MainActivity extends AppCompatActivity {

    Realm realm;

    private RealmResults<People> getPeopleList(){

        return realm.where(People.class).findAll();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();


        Button btnInit = (Button) findViewById(R.id.btnInit);
        btnInit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "DB초기화 완료", Toast.LENGTH_SHORT).show();
                realm.executeTransaction(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        realm.delete(People.class);
                    }
                });
            }



        });



        Button btnSelect = (Button) findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm = Realm.getDefaultInstance();
                long nStart = 0;
                long nEnd = 0;
                nStart = System.currentTimeMillis();
                RealmResults<People> results = realm.where(People.class).findAll();
                TextView tvSelectView = (TextView) findViewById(R.id.tvSelectView);
                TextView tvSelect = (TextView) findViewById(R.id.tvSelect);
                //tvSelectView.setText(String.valueOf(results));
                nEnd = System.currentTimeMillis();
                tvSelect.setText(String.valueOf(nEnd - nStart) + "ms");

            }
        });

        Button btnWhere = (Button) findViewById(R.id.btnWhere);
        btnWhere.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                realm.executeTransaction(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        long nStart = 0;
                        long nEnd = 0;
                        nStart = System.currentTimeMillis();
                        RealmResults<People> wherePeople = realm.where(People.class)
                                .between("id", 100, 3850)
                                .equalTo("content", "{\"Name\":\"Minwoo\",\"Age\":25}")
                                .findAll();
                        TextView tvWhere = (TextView) findViewById(R.id.tvWhere);

                        nEnd = System.currentTimeMillis();
                        TextView tvWhereView = (TextView) findViewById(R.id.tvWhereView);
                        //tvWhereView.setText(String.valueOf(wherePeople));
                        tvWhere.setText(String.valueOf("실행 시간 : " + (nEnd - nStart) + "ms"));
                    }
                });
            }
        });
        Button insertBtn = (Button) findViewById(R.id.btnInsert);
        insertBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                //Realm realm2 = Realm.getDefaultInstance();
                realm = Realm.getDefaultInstance();

                realm.executeTransaction(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){

                        InputStream in = getResources().openRawResource(R.raw.);

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
                                String result = "";
                                //sqlDB = myHelper.getWritableDatabase();
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
                                    //sqlDB.execSQL("INSERT INTO testTBL VALUES ('" + i +"','"+ jsonArray.getString(i) + "');");
                                    //datas.add(temp_name);
                                    //datas.add(String.valueOf(temp_age));

                                    People myPeople = realm.createObject(People.class, i);
                                    myPeople.setContent(jsonArray.getString(i));
                                    result += i + "      " + jsonArray.getString(i) + "\n";
                                }
                                nEnd = System.currentTimeMillis();
                                //timeInsert.setText(String.valueOf (nEnd - nStart) + "ms");
                                //sqlDB.close();
                                //tvInsert.setText(result);
                                TextView tvInsert = (TextView) findViewById(R.id.tvInsert);
                                tvInsert.setText(String.valueOf (nEnd - nStart) + "ms");

                                Toast.makeText(getApplicationContext(), "DB입력완료", Toast.LENGTH_SHORT).show();
                                //JSONObject firstJson = new JSONObject(jsonArray.getString(0));
                            }

                        } catch (Exception e){
                            e.printStackTrace();
                        }



                    }
                });
            }
        });
    }
}
