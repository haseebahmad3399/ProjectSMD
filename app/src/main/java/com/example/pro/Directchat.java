package com.example.pro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;


public class Directchat extends AppCompatActivity {



    EditText phno,msg;
    TextView sendbtn;
    String msgstr,phnostr="";
    CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directchat);
        phno=findViewById(R.id.phno);
        msg=findViewById(R.id.msg);
        sendbtn=findViewById(R.id.sendbtn);
        ccp=findViewById(R.id.ccp);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgstr=msg.getText().toString();
                String countrycode=ccp.getSelectedCountryCode();
                phnostr=countrycode+phno.getText().toString();


                if(!msgstr.isEmpty() && !phnostr.isEmpty()){




                        Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+phnostr+"&text="+msgstr));
                        startActivity(i);
                        msg.setText("");
                        phno.setText("");

                }
                else {
                    Toast.makeText(Directchat.this, "Please fill up the text fields", Toast.LENGTH_SHORT).show();
                }



            }
        });





        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.rgb(7, 94, 84));
        }
    }

    private boolean isWhatsappinstalled(){
        PackageManager packageManager=getPackageManager();
        boolean whtsappinstalled;
        try {
            packageManager.getPackageInfo("com.whatsapp",packageManager.GET_ACTIVITIES);
            whtsappinstalled=true;

        } catch (PackageManager.NameNotFoundException e) {
            whtsappinstalled=false;
//            e.printStackTrace();
        }

        return whtsappinstalled;
    }



    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}