package com.example.rahul.evaluation_song;

import android.hardware.*;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    SensorManager smgr;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        Sensor accelerometer=smgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        smgr.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onSensorChanged(SensorEvent event)
    {
        int a=0,b=0;
        if(event.values[0] > -5 && event.values[0] < 5 && event.values[1] < 5 && event.values[1] > -5)
        {
            a=0;
        }
        else if ((event.values[0] > 5)&&(a<1))
        {
            //firsthalf
            MediaPlayer mp=new MediaPlayer();
            try
            {
                mp.setDataSource("/storage/emulated/0/song1.mp3");
                mp.prepare();
                mp.start();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            a++;
        }
        else if ((event.values[0] < -5)&&(a<1))
        {
           //secondhalf
            MediaPlayer mp=new MediaPlayer();
            try
            {

                mp.setDataSource("/storage/emulated/0/song2.mp3");
                mp.prepare();
                mp.start();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            a++;
        }
        else if ((event.values[1] < -5)&&(a<1))
        {
            //play
            MediaPlayer mp=new MediaPlayer();
            try
            {
                mp.setDataSource("/storage/emulated/0/song.mp3");
                mp.prepare();
                mp.start();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            a++;
        }
        else if((event.values[1]>5)&&(b<1))
        {
            //cut
            File f=new File("storage/emulated/0/song.mp3");
            try
            {


                FileInputStream fis=new FileInputStream(f);
                BufferedInputStream bis=new BufferedInputStream(fis);
                FileOutputStream fos=new FileOutputStream("storage/emulated/0/song1.mp3");
                FileOutputStream fos2=new FileOutputStream("storage/emulated/0/song2.mp3");

                BufferedOutputStream bos=new BufferedOutputStream(fos);
                BufferedOutputStream bos2=new BufferedOutputStream(fos2);

                int ch,j;
                float i;


                for(i=0;i<=(float)f.length()/2;i++)
                {
                    ch=bis.read();
                    bos.write(ch);
                }
                for(i=f.length()/2;i<=f.length();i++)
                {
                    ch=bis.read();
                    bos2.write(ch);


                }
                Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
            }
            catch(Exception e)
            {
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
            b++;
        }

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
