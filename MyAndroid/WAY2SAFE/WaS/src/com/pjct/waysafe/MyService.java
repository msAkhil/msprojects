package com.pjct.waysafe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import com.example.dbase.DbaseD;
import com.example.dbase.Dbasehelper;

import uk.co.jarofgreen.lib.ShakeDetectActivity;
import uk.co.jarofgreen.lib.ShakeDetectActivityListener;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.*;
import android.util.Log;
import android.widget.Toast;
import com.example.dbase.*;

public class MyService extends Service {
	ShakeDetectActivity shakeDetectActivity;
	int stat=0;
	GPSTracker gps;
	String nm,ph,ad;

	Handler mHandler;

	 private static final String TAG_SUCCESS = "success";
	  private static String url_create_product = "http://192.168.0.101/was/create.php";
	DbaseD datasrc;
	double latitude;
	double longitude;
	 JSONParser jsonParser = new JSONParser();
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int onStartCommand(Intent intent,int flags, int startid)
	{
		datasrc = new DbaseD(this);
	    datasrc.open();
		Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();
		nm=datasrc.fetchTb1(Dbasehelper.COLUMN_UNAME);
		ph=datasrc.fetchTb1(Dbasehelper.COLUMN_UPASS);
		ad=datasrc.fetchTb1(Dbasehelper.COLUMN_ADDRESS);
		shakeDetectActivity = new ShakeDetectActivity(this); 
		shakeDetectActivity.addListener(new ShakeDetectActivityListener() {
			@Override
			public void shakeDetected() {
				MyService.this.triggerShakeDetected();
				
			}
			
		});
		
		return START_STICKY;
	}
	public void onDestroy(){
		super.onDestroy();
		Toast.makeText(this, "destroyed", Toast.LENGTH_LONG).show();
	}
	public void triggerShakeDetected() {
		Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		 String result = null;
		stat=stat+1;
		if(stat==3){
		Toast.makeText(getApplicationContext(),
	            "SHAKEd"+stat, Toast.LENGTH_SHORT).show();
		gps = new GPSTracker(MyService.this);
		

		// check if GPS enabled		
        if(gps.canGetLocation()){
        	Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        	
        	 latitude = gps.getLatitude();
        	 longitude = gps.getLongitude();
        	
        	try {
                List<Address> list = geocoder.getFromLocation(
                        latitude, longitude, 1);
                if (list != null && list.size() > 0) {
                    Address address = list.get(0);
                    // sending back first address line and locality
                    result = address.getAddressLine(0) + ", " + address.getLocality();
                }
            } catch (IOException e) {
                Log.e("err", "Impossible to connect to Geocoder", e);
            }
        	
        	// \n is for new line
        	 datasrc.getNSm(result,String.valueOf(latitude),String.valueOf(longitude));
        	 v.vibrate(300); 
        	final Handler handler = new Handler();
        	handler.postDelayed(new Runnable() {
        	    public void run() {
        	    	 latitude = gps.getLatitude();
                	 longitude = gps.getLongitude();
        	    	new CreateNewProduct().execute();

        	        handler.postDelayed(this, 20000); 
        	    }
        	 }, 20000);
        	 
        	    
        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " +result, Toast.LENGTH_LONG).show();	
        }else{
        	// can't get location
        	// GPS or Network is not enabled
        	// Ask user to enable GPS/network in settings
        	gps.showSettingsAlert();
        }
		
		v.vibrate(300); 
		}
		
		
		
	}
	class CreateNewProduct extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
    	 protected void onPreExecute() {
             super.onPreExecute();
             /*pDialog = new ProgressDialog(MainActivity.this);
             pDialog.setMessage("Creating Product..");
             pDialog.setIndeterminate(false);
             pDialog.setCancelable(true);
             pDialog.show();*/
         }
  
        @Override
        
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String uphn = ph;
            String unme =nm;
            String uadrs=ad;
           
            String ulati = String.valueOf(latitude);
            String ulong = String.valueOf(longitude);
            
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uphn", uphn));
            params.add(new BasicNameValuePair("unme", unme));
            
            params.add(new BasicNameValuePair("uadrs", uadrs));
            params.add(new BasicNameValuePair("ulati", ulati));
            params.add(new BasicNameValuePair("ulong", ulong));
            
 
            // getting JSON Object
            // Note that create product url accepts POST method
        jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
 
            // check log cat fro response
            /*Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	Toast.makeText(getBaseContext(), "insert successfully !", Toast.LENGTH_LONG).show();
                	Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    // closing this screen
                   
              } else {
                    // failed to create product
                	Toast.makeText(getBaseContext(), "Error in insertion...", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 */
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
           // pDialog.dismiss();
            Toast.makeText(getBaseContext(), "insert successfully !", Toast.LENGTH_LONG).show();
        }
 
    }
	
}
