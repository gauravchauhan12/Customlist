package com.mss.customlist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TreatmentActivity extends Activity {


    LinearLayout lltime;

    String mytime;
    ArrayList<String> timelist;
    ArrayList<String> mynewlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        intui();


    }


    public void intui() {


        lltime = (LinearLayout) findViewById(R.id.ll_time);
        timelist = new ArrayList<String>();
        mynewlist = new ArrayList<String>();

        getdata();
        addTherapyViews();
        currentTimestamp();
    }


    public String getdata() {


        String json = null;
        try {
            InputStream is = this.getAssets().open("customlist.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void addTherapyViews() {


        try {

            JSONObject jo = new JSONObject(getdata());

            JSONArray js = jo.getJSONArray("listvalues");

            for (int i = 0; i < 24; i++) {


                LinearLayout.LayoutParams params;
                params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                View thearpyChild = getLayoutInflater().inflate(R.layout.adpater_physical_treatment, lltime, false);
                final TextView txttime = (TextView) thearpyChild.findViewById(R.id.txt_time);

                //  mytime = gettime(Integer.parseInt(timelist.get(i)));

                Log.e("hashdakjfg", timelist.get(i).toString());


                if (i < 12) {
                    txttime.setText(i + " am");
                } else if (i > 12) {

                    txttime.setText(i - 12 + " pm");
                } else {
                    txttime.setText(i + " pm");
                }


                // txttime.setText(mytime);

                LinearLayout llimagesss = (LinearLayout) thearpyChild.findViewById(R.id.ll_images);
                llimagesss.setVisibility(View.GONE);

                for (int j = 0; j < js.length(); j++) {

                    String finalDate = new TreatmentUtils(this)
                            .getHourFromTimeStamps(Long.parseLong(js
                                    .getJSONObject(j).getString("time")));

                    String[] taskHour = finalDate.split(":");
                    String hour = taskHour[0];


                    if (i == Integer.parseInt(hour)) {

                        llimagesss.setVisibility(View.VISIBLE);
                        //  Log.e("Time Inside ", "" + timelist.get(i));
                        //  Log.e("Main Outside ", "" + js.getJSONObject(i));

                        LinearLayout.LayoutParams imagparms;

                        imagparms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150);

                        View imagechild = getLayoutInflater().inflate(R.layout.adapter_home_treatment, llimagesss, false);

                        final TextView txtTitle = (TextView) imagechild.findViewById(R.id.txt_home_title);

                        CheckBox chkb = (CheckBox) imagechild.findViewById(R.id.chk_home);

                        txtTitle.setText(js.getJSONObject(j).getString("name"));


                        imagechild.setLayoutParams(imagparms);


                        llimagesss.addView(imagechild);

                        final int posi = llimagesss.indexOfChild(imagechild);
                        imagechild.setTag(posi);


                        chkb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                                Toast.makeText(TreatmentActivity.this, "" + txtTitle.getText(), Toast.LENGTH_SHORT).show();


                            }
                        });


                        final int position = llimagesss.indexOfChild(imagechild);
                        imagechild.setTag(position);


                        imagechild.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                int position = (Integer) view.getTag();

                                Toast.makeText(TreatmentActivity.this, "" + position, Toast.LENGTH_SHORT).show();


                            }
                        });


                    }
                }

                thearpyChild.setLayoutParams(params);
                lltime.addView(thearpyChild);


            }

        } catch (Exception e) {

            e.printStackTrace();

        }


    }


    public void currentTimestamp() {


    }


}
