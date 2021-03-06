package com.Happy.happythrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class setting extends AppCompatActivity {

    ImageButton gotrash,gochart,gomybin;
    Button reset,rebank,add;
    Spinner bank;
    String bankname;
    String phone,username,userphone,userbanknum,userbank;
    EditText banknumber,accnum;
    LinearLayout readdbank;
    TextView infousername,infophonenum,infobanknum,infobankname;

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        gotrash = findViewById(R.id.Go_Trash_button);
        gochart = findViewById(R.id.Go_chart_button);
        gomybin = findViewById(R.id.Go_MyTrash_button);
        reset = findViewById(R.id.Reset);
        rebank = findViewById(R.id.ReEditbank);
        bank = findViewById(R.id.bankBrandspinner2);
        add = findViewById(R.id.adduserok);
        banknumber = findViewById(R.id.banknumber);
        accnum = findViewById(R.id.numbercheckinput);
        readdbank = findViewById(R.id.readdbanks);
        infousername = findViewById(R.id.infousername);
        infophonenum = findViewById(R.id.infophonenum);
        infobanknum = findViewById(R.id.infobanknum);
        infobankname = findViewById(R.id.infobankname);

        phone = readmemo("id.txt");

        ref();

        String[] bankitem = {"????????????", "????????????", "????????????", "????????????", "????????????", "SC????????????", "????????????", "???????????????", "K??????"};
        ArrayAdapter<String> bankadapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, bankitem
        );
        bank.setAdapter(bankadapter);

        /**
         * ?????? ????????? ????????? ?????????.
         * ??????????????? ?????? ???????????? ?????? ?????????.
         */

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);


        /**
         * ??? ????????? ????????? ??????????????????.
         */

        phone = readmemo("id.txt");

        bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bankname = (bankitem[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bankname = "";
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(banknumber.getText().toString().equals("")) && !(accnum.getText().toString().equals(""))) {

                    db.collection("Userdata").document(phone).update("??????",bankname);
                    db.collection("Userdata").document(phone).update("????????????",banknumber.getText().toString());

                    Toast.makeText(setting.this,"?????? ??????",Toast.LENGTH_SHORT).show();

                    rebank.setVisibility(View.VISIBLE);
                    reset.setVisibility(View.VISIBLE);
                    readdbank.setVisibility(View.GONE);
                    ref();

                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(accnum.getWindowToken(), 0);
                }
                else{
                    Toast.makeText(setting.this,"????????? ??????????????????",Toast.LENGTH_SHORT).show();
                }
            }
        });

        gotrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * ??? ????????? ???????????? ???????????? startActivity??? ???????????? ????????????.
                 * ????????? ???????????? setting.this ?????? QR_main.class??? ????????????.
                 *  overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                 *  ?????? ???????????? , ????????? ?????? ????????? ???
                 *  finish()??? ???????????? ??????(????????? ??????)
                 * */

                Intent intent = new Intent(setting.this, QR_main.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        gochart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * ??? ????????? ???????????? ???????????? startActivity??? ???????????? ????????????.
                 * ????????? ???????????? setting.this ?????? trash_chart.class??? ????????????.
                 *  overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                 *  ?????? ???????????? , ????????? ?????? ????????? ???
                 *  finish()??? ???????????? ??????(????????? ??????)
                 * */

                Intent intent = new Intent(setting.this, QR_main.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        gomybin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * ??? ????????? ???????????? ???????????? startActivity??? ???????????? ????????????.
                 * ????????? ???????????? setting.this ?????? my_trash.class??? ????????????.
                 *  overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                 *  ?????? ???????????? , ????????? ?????? ????????? ???
                 *  finish()??? ???????????? ??????(????????? ??????)
                 * */

                Intent intent = new Intent(setting.this, my_trash.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });


        rebank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reset.setVisibility(View.GONE);
                rebank.setVisibility(v.GONE);
                readdbank.setVisibility(View.VISIBLE);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String path = getFilesDir().toString();
                File file = new File(path);
                File[] files = file.listFiles();
                int a = files.length;
                for (int i = 0; i < a; i++) {
                    if(files[i].toString().length() < 45){
                    //123
                    }
                    else {
                        String path2 = files[i].toString();
                        File file1 = new File(path2);
                        if (file1.exists()) {
                            file1.delete();
                        }
                    }
                }
                Toast.makeText(setting.this,"????????? ??????",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String readmemo(String fileName) {

        try {
            // ???????????? ?????? ???????????? ???????????? ????????? ?????? ??????
            StringBuffer data = new StringBuffer();
            FileInputStream fs = openFileInput(fileName);//?????????
            BufferedReader buffer = new BufferedReader
                    (new InputStreamReader(fs));
            String str = buffer.readLine(); // ???????????? ????????? ?????????
            if (str != null) {
                while (str != null) {
                    data.append(str);
                    str = buffer.readLine();
                }
                buffer.close();
                return data.toString();
            }
        } catch (Exception e) {

        }
        return null;
    }

    public void ref(){
        try {
            db.collection("Userdata").document(phone).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    username = documentSnapshot.get("??????").toString();
                    userphone = documentSnapshot.get("????????????").toString();
                    userbanknum = documentSnapshot.get("????????????").toString();
                    userbank = documentSnapshot.get("??????").toString();
                    StringBuffer sb = new StringBuffer();
                    sb.append(userphone);
                    sb.insert(3,"-");
                    sb.insert(8,"-");
                    infousername.setText("?????? : "+username);
                    infophonenum.setText("???????????? : "+sb);
                    infobanknum.setText("???????????? : "+userbanknum);
                    infobankname.setText("?????? : "+userbank);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}