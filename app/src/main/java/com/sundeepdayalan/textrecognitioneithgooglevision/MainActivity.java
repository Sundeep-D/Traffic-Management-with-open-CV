package com.sundeepdayalan.textrecognitioneithgooglevision;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SurfaceView cameraView;
    TextView textView,textView_for_list;
    CameraSource cameraSource;
    final int RequestCameraPrmissionID = 1001;

    ArrayList<String> arr=new ArrayList<String>();
    CardView bay1,bay2,bay3;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case RequestCameraPrmissionID:
            {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    return;
                }
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);
        textView_for_list = (TextView) findViewById(R.id.text_view_for_list);
        bay1=(CardView)findViewById(R.id.bay1);
        bay2=(CardView)findViewById(R.id.bay2);
        bay3=(CardView)findViewById(R.id.bay3);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {

        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedFps(2.0f)
                    .setRequestedPreviewSize(1200, 1024)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                                    {
                                            Manifest.permission.CAMERA

                                    }, RequestCameraPrmissionID);
                            return;
                        }

                        cameraSource.start(cameraView.getHolder());
                    }
                    catch (Exception e)
                    {

                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size() !=0)
                    {
                       textView.post(new Runnable() {
                           @Override
                           public void run() {

                               StringBuilder strBuilder = new StringBuilder();
                               for (int i = 0; i < items.size(); ++i)
                               {
                                   TextBlock item=items.valueAt(i);
                                   strBuilder.append(item.getValue());
                                   //strBuilder.append("\n");
                                   arr.add(item.getValue());

                               }

                               textView.setText("");

                              if(strBuilder.toString().equals("BAY1BAY2BAY3") )
                               {
                                  // textView.setText("BAY 1,BAY 2,BAY 3 are free for parking");
                                   bay1.setCardBackgroundColor(Color.parseColor("#32CD32"));
                                   bay2.setCardBackgroundColor(Color.parseColor("#32CD32"));
                                   bay3.setCardBackgroundColor(Color.parseColor("#32CD32"));


                               }else  if(strBuilder.toString().equals("BAY1BAY2") )
                              {
                                  //textView.setText("BAY 1,BAY 2 are free for parking");
                                  bay1.setCardBackgroundColor(Color.parseColor("#32CD32"));
                                  bay2.setCardBackgroundColor(Color.parseColor("#32CD32"));
                                  bay3.setCardBackgroundColor(Color.parseColor("#d10b0b"));
                              }
                              else  if(strBuilder.toString().equals("BAY1") )
                              {
                                  //textView.setText("BAY 1 is free for parking");
                                  bay1.setCardBackgroundColor(Color.parseColor("#32CD32"));
                                  bay2.setCardBackgroundColor(Color.parseColor("#d10b0b"));
                                  bay3.setCardBackgroundColor(Color.parseColor("#d10b0b"));
                              }else  if(strBuilder.toString().equals("BAY2BAY3") )
                              {
                                  //textView.setText("BAY 2,BAY 3 are free for parking");
                                  bay1.setCardBackgroundColor(Color.parseColor("#d10b0b"));
                                  bay2.setCardBackgroundColor(Color.parseColor("#32CD32"));
                                  bay3.setCardBackgroundColor(Color.parseColor("#32CD32"));
                              }else  if(strBuilder.toString().equals("BAY1BAY3") )
                              {
                                  //textView.setText("BAY 1,BAY 3 are free for parking");
                                  bay1.setCardBackgroundColor(Color.parseColor("#32CD32"));
                                  bay2.setCardBackgroundColor(Color.parseColor("#d10b0b"));
                                  bay3.setCardBackgroundColor(Color.parseColor("#32CD32"));
                              }else  if(strBuilder.toString().equals("BAY2") )
                              {
                                 // textView.setText("BAY 2 is free for parking");
                                  bay1.setCardBackgroundColor(Color.parseColor("#d10b0b"));
                                  bay2.setCardBackgroundColor(Color.parseColor("#32CD32"));
                                  bay3.setCardBackgroundColor(Color.parseColor("#d10b0b"));
                              }else  if(strBuilder.toString().equals("BAY3") )
                              {
                                  //textView.setText("BAY 3 is free for parking");
                                  bay1.setCardBackgroundColor(Color.parseColor("#d10b0b"));
                                  bay2.setCardBackgroundColor(Color.parseColor("#d10b0b"));
                                  bay3.setCardBackgroundColor(Color.parseColor("#32CD32"));
                              }else
                              {
                                  textView.setText("Processing.....");
                              }

//                              for(String str:arr)
//                              {
//                                  textView_for_list.setText(strBuilder);
//                              }

                                 // arr.clear();

                           }
                       });
                    }
                }
            });


        }
    }
}
