package com.mosquefinder.arnal.prayertimesapp.Fragment;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.mosquefinder.arnal.prayertimesapp.R;
import com.mosquefinder.arnal.prayertimesapp.data.TimesPreferences;

import net.alqs.iclib.qibla.Qibla;

import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompassFragment extends Fragment implements SensorEventListener{
    private static final String TAG = "CompassActivity";
// define the display assembly compass picture
   private ImageView image;
    double latitude, longitude ;

    // record the compass picture angle turned

    private float currentDegree = 0f;
    private float f = 0.1f;
    // device sensor manager
    private SensorManager mSensorManager;

    TextView tvHeading;
    public CompassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.activity_compass, container, false);

        image = (ImageView) rootView.findViewById(R.id.imageViewCompass);


        tvHeading = (TextView) rootView.findViewById(R.id.tvHeading);
        double coord[] =  TimesPreferences.getLocationCoordinates(getActivity());

        latitude = coord[0];
        longitude = coord[1];

        Log.d(TAG, Double.toString(Qibla.findDirection(latitude, longitude)));
        double qibla = Qibla.findDirection(latitude, longitude);
         f = (float) qibla;
          f = Math.round(f);
        Log.d(TAG, Float.toString(currentDegree));

        // initialize your android device sensor capabilities

        mSensorManager = (SensorManager)getContext().getSystemService(SENSOR_SERVICE);

        return rootView;
    }
    @Override
    public void onResume() {

        super.onResume();

        // for the system's orientation sensor registered listeners

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),

                SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    public void onPause() {

        super.onPause();


        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        float degree = Math.round(event.values[0]);
        float direction = degree - f;
        tvHeading.setText("Direction To Qibla: " + Float.toString(direction) + " degrees");


      //  Log.d(TAG, Float.toString(f));
        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        // how long the animation will take place
        ra.setDuration(210);
        // set the animation after the end of the reservation status
        ra.setFillAfter(true);
        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;
        if(degree == f){
           // getView().setBackgroundColor(0xFF00FF00);
            getView().setBackgroundColor(getResources().getColor(R.color.colorGreen));
            tvHeading.setText("You are facing to Qibla");

        }else
            getView().setBackgroundColor(getResources().getColor(R.color.colorWhite));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
