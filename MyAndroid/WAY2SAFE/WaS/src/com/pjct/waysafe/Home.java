package com.pjct.waysafe;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dbase.DbaseD;
import com.example.dbase.Dbasehelper;

public class Home extends Activity {
	public final static String EXTRA_MESSAGE = "com.pjct.MESSAGE";
	
DbaseD datasource;
private boolean isMyServiceRunning() {
    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
        if (MyService.class.getName().equals(service.service.getClassName())) {
            return true;
        }
    }
    return false;
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_home);
		datasource = new DbaseD(this);
	    datasource.open();
	    ImageButton im =(ImageButton) findViewById(R.id.imageButton3);
	    im.setTag("0");
	   if(isMyServiceRunning()==true)
	    {
		 
		im.setImageResource(R.drawable.sos2);

	    }
	 
	    if(DbaseD.doesDatabaseExist((ContextWrapper) getApplicationContext(),"WAS.db")){
	    	if(datasource.NulChk(Dbasehelper.TABLE_NAME1)){
	    	Toast.makeText(getApplicationContext(),"no value in table", Toast.LENGTH_LONG).show();
	    	Intent intent =new Intent(this,Welcome1.class);
			String message="home";
			intent.putExtra("EXTRA_FRM", message);
			startActivity(intent);
	    	}
	    	else{
	    		Toast.makeText(getApplicationContext(),"Ready to go", Toast.LENGTH_LONG).show();
	    		}
	    	}
	    else
	    {
	    	Intent intent =new Intent(this,Welcome1.class);
			String message="home";
			intent.putExtra("EXTRA_FRM", message);
			startActivity(intent);
	    		    }
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	public void pfl(View view)
	{
		   Log.e("load", "loadok");
		Toast.makeText(getApplicationContext(),"clicked", Toast.LENGTH_LONG).show();
		Intent intent =new Intent(this,MNstp.class);
		String message="homep";
		intent.putExtra("EXTRA_FRM", message);
		startActivity(intent);
	}
	public void frnds(View view){
		Log.e("button", "press ok");
		Intent intent =new Intent(this,DisplayActivity.class);
		Log.e("intent", "ok");
		String message="homef";
		intent.putExtra("EXTRA_FRM", message);
		Log.e("intent started", "ok");
		startActivity(intent);
		
	}
	public void sos(View view)
	{
		
		
		ImageButton aButton = (ImageButton)view;
		if(isMyServiceRunning()==false)
		{
			startService(new Intent(getBaseContext(),MyService.class));
		aButton.setImageResource(R.drawable.sos2); 
		aButton.setTag("1");
		
		}
		else {
			
			stopService(new Intent(getBaseContext(),MyService.class));
			aButton.setImageResource(R.drawable.sos);
			Toast.makeText(getApplicationContext(),"service stoped", Toast.LENGTH_LONG).show();
		}
		}
	
	
	
	}

