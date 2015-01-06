package com.pjct.waysafe;



import android.os.Bundle;

import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.pjct.waysafe.*;
import com.example.dbase.*;
public class Welcome1 extends Loginpj {
	DbaseD datasrc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		datasrc = new DbaseD(this);
	    datasrc.open();
		Intent intent=getIntent();
		String from=intent.getStringExtra("EXTRA_FRM");
		setContentView(R.layout.activity_welcome1);
		if(from.equals("home"))
		{
			Toast.makeText(getApplicationContext(),"READ STATEMENT", Toast.LENGTH_LONG).show();
	    	
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome1, menu);
		return true;
	}

	public void acpt(View view)
	{
		Intent intent =new Intent(this,Loginpj.class);
		String message="welcome";
		intent.putExtra("EXTRA_FRM", message);
		startActivity(intent);
	}
	
}
