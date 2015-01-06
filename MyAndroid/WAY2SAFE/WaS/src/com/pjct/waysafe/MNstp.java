package com.pjct.waysafe;

import android.os.Bundle;
import com.example.dbase.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;
@SuppressLint("NewApi")
public class MNstp extends Activity implements Runnable {
TextView txterr;
DbaseD datasrc;
EditText ET_nm,ET_ml,ET_adrs,ET_pas1,ET_pas2;
Button Stp,updt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mnstp);
		ET_nm=(EditText) findViewById(R.id.editText1);
		ET_ml=(EditText) findViewById(R.id.editText2);
		ET_adrs=(EditText) findViewById(R.id.editText3);
		ET_pas1=(EditText) findViewById(R.id.editText5);
		ET_pas2=(EditText) findViewById(R.id.editText4);
		txterr =(TextView) findViewById(R.id.textView1);
		Stp=(Button) findViewById(R.id.button2);
		updt=(Button) findViewById(R.id.button1);
		datasrc = new DbaseD(this);
	    datasrc.open();
		
		Intent intent=getIntent();
		String prnmae=intent.getStringExtra("EXTRA_NME");
		String prml=intent.getStringExtra("EXTRA_MLE");
		String from=intent.getStringExtra("EXTRA_FRM");
		if(from.equals("loginpjg"));
		{
		ET_nm.setText(prnmae);
		ET_ml.setText(prml);
		ET_nm.setEnabled(false);
		ET_ml.setEnabled(false);
		}
		if (from.equals("loginpjm"))
		{
			ET_nm.setEnabled(true);
			ET_ml.setEnabled(true);
		}
		if(from.equals("homep"))
		{
			ET_nm.setEnabled(false);
			ET_ml.setEnabled(false);
			ET_adrs.setEnabled(false);
			ET_nm.setText(datasrc.fetchTb1(Dbasehelper.COLUMN_UNAME));
			ET_ml.setText(datasrc.fetchTb1(Dbasehelper.COLUMN_UMAIL)); 
			ET_nm.setText(datasrc.fetchTb1(Dbasehelper.COLUMN_UNAME));
			ET_adrs.setText(datasrc.fetchTb1(Dbasehelper.COLUMN_ADDRESS));
	    	updt.setEnabled(true);
	    	Stp.setEnabled(false);
	    	
		}
		
	    }
	
 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mnstp, menu);
		return true;
	}
	public void seTup(View view){
		String s1=ET_pas1.getText().toString();
		String s2=ET_pas2.getText().toString();
		String s3=ET_nm.getText().toString(); 
		String s4=ET_ml.getText().toString();
		String s5=ET_adrs.getText().toString();
	if(s1.equals(s2) && !s3.isEmpty() && !s1.isEmpty() && !s4.isEmpty() && !s5.isEmpty() ){
		datasrc.createComment1(ET_nm.getText().toString(), ET_ml.getText().toString(), ET_adrs.getText().toString(), ET_pas1.getText(). toString(),"I am at risk, help me");
		Toast.makeText(getApplicationContext(),"saved", Toast.LENGTH_LONG).show();
		Intent intent =new Intent(this,Home.class);
		String message="mnstp";
		intent.putExtra("EXTRA_FRM", message);
		startActivity(intent);
		
	}	
	else
	{
		Toast.makeText(getApplicationContext(),"something went wrong check your values", Toast.LENGTH_LONG).show();
	}
		
	}
	public void updt(View view)
	{
		if((ET_pas1.getText().toString()).equals(ET_pas2.getText().toString()) && !(ET_pas1.getText().toString()).isEmpty())
		datasrc.UpdateTb1(Dbasehelper.TABLE_NAME1, Dbasehelper.COLUMN_UPASS,ET_pas1.getText().toString() );
		Toast.makeText(getApplicationContext(),"saved", Toast.LENGTH_LONG).show();
		Intent intent =new Intent(this,Home.class);
		String message="mnstp";
		intent.putExtra("EXTRA_FRM", message);
		startActivity(intent);
		
	}
	  @Override
	  protected void onResume() {
	    datasrc.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasrc.close();
	    super.onPause();
	  }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
