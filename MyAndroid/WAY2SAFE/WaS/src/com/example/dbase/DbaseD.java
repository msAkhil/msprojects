package com.example.dbase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class DbaseD {
	private SQLiteDatabase database;
	  private Dbasehelper dbHelper;
	  private String[] allColumns1 = { Dbasehelper.COLUMN_ID1,
			  Dbasehelper.COLUMN_UNAME,Dbasehelper.COLUMN_UMAIL,Dbasehelper.COLUMN_ADDRESS };
	  private String[] allColumns2 = { Dbasehelper.COLUMN_ID1,
			  Dbasehelper.CONTC_AUTH,Dbasehelper.AUTH_NUM,Dbasehelper.AUTH_SMS };
	 /* private String[] allColumns3 = { Dbasehelper.COLUMN_ID1,
			  Dbasehelper.FRI_NAM,Dbasehelper.FRI_NUM,Dbasehelper.FRI_MAIL };*/
	  private String[] allColumns4 = { Dbasehelper.COLUMN_ID1,
			  Dbasehelper.LOC_LAT,Dbasehelper.LOC_LON,Dbasehelper.LOC_ADR,Dbasehelper.LOC_TM  };
	  public DbaseD (Context context) {
		    dbHelper = new Dbasehelper(context);
		  }
	  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		  }
	  public void close() {
		    dbHelper.close();
		  }

	  public void createComment1(String UN,String UM,String UA,String pass,String hlp ) {
		    ContentValues values = new ContentValues();
		    values.put(Dbasehelper.COLUMN_ID1, 1);
		    values.put(Dbasehelper.COLUMN_UNAME, UN);
		    values.put(Dbasehelper.COLUMN_UMAIL, UM);
		    values.put(Dbasehelper.COLUMN_ADDRESS, UA);
		    values.put(Dbasehelper.COLUMN_UPASS, pass);
		    values.put(Dbasehelper.COLUMN_UHLP, hlp);
		     database.insert(Dbasehelper.TABLE_NAME1, null,
		        values);
		   
		  }
	
	  public void UpdateTb1(String TBNM,String TC,String TCV){
		  String sql = "UPDATE "+Dbasehelper.TABLE_NAME1 +" SET " + TC+ " = '"+TCV+"' WHERE "+Dbasehelper.COLUMN_ID1+ " = "+1;
		  database.execSQL(sql);
		  
	  }
	  public void deleteComment1(String tbnm,int id) {
		    //long id = comment.getId();
		    //System.out.println("Comment deleted with id: " + id);
		    database.delete(tbnm, Dbasehelper.COLUMN_ID1
		        + " = " + id, null);
		  }
	  
	  public void deleteTbl(String tbname){
		  database.execSQL("delete from "+tbname);
	  }
	  public Boolean NulChk(String tbnameC){
		  String TAG="DbaseD";
		  Cursor cursor = database.rawQuery("SELECT count(*) FROM "+tbnameC,null);
		  if (cursor != null) {
			  cursor.moveToFirst();                       // Always one row returned.
			    if (cursor.getInt (0) == 0) { 
			    	return true;
			    }
			    return false; 
			    }
	return false; 	
		 
	  }
	  public static boolean doesDatabaseExist(ContextWrapper context, String dbName) {
		    File dbFile = context.getDatabasePath(dbName);
		    return dbFile.exists();
		}
	  public void createComment2(String auth,String num,String sms){
		  
		  ContentValues values1 = new ContentValues();
		   values1.put(Dbasehelper.CONTC_AUTH, auth);
		    values1.put(Dbasehelper.AUTH_NUM, num);
		    values1.put(Dbasehelper.AUTH_SMS, sms);
		     database.insert(Dbasehelper.TABLE_NAME2, null,
		        values1);
		  
		  
	  }
	  public void UpdateTb2(String TBNM,String id,String TC,String TCV){
		  String sql = "UPDATE "+TBNM +" SET " + TC+ " = '"+TCV+"' WHERE "+Dbasehelper.COLUMN_ID1+ " = "+1;
		  database.execSQL(sql);
		  
	  }
	  
	  /*public void createComment3(String FN,String FNM,String FML ) {
		    ContentValues values = new ContentValues();
		    values.put(Dbasehelper.FRI_NAM,FN);
		    values.put(Dbasehelper.FRI_NUM, FNM);
		    values.put(Dbasehelper.FRI_MAIL, FML);
		     database.insert(Dbasehelper.TABLE_NAME3, null,
		        values);
		   
		  }*/
	  public void createComment4(String lat ,String lon,String adr,String tm) {
		    ContentValues values = new ContentValues();
		    values.put(Dbasehelper.LOC_LAT,lat);
		    values.put(Dbasehelper.LOC_LON, lon);
		    values.put(Dbasehelper.LOC_ADR, adr);
		    values.put(Dbasehelper.LOC_TM, tm);
		     database.insert(Dbasehelper.TABLE_NAME4, null,
		        values);
		   
		  }
	  public String fetchTb1(String col)
	  {
		  int num_id=1;
		  String sql="SELECT  * FROM " + Dbasehelper.TABLE_NAME1+ " WHERE "
		            + allColumns1[0] + " = " + num_id;
		 
		  Cursor cs = database.rawQuery(sql, null);
		  if (cs.moveToFirst()) {
		      String a = cs.getString(cs.getColumnIndex(col));
		      cs.close();
		      return a;
		  }
		  cs.close();
		  return "";
		 

		 
	  }
	 
	  public boolean isVExist(String edittext){
		  boolean editTextValue= false;
		  Cursor cursor = database.rawQuery("SELECT * FROM " +Dbasehelper.TABLE_NAME, null);
		      while(cursor.moveToNext()){
		          String recorded_editTextValue=cursor.getString(cursor.getColumnIndex(Dbasehelper.KEY_LNAME));
		          if(recorded_editTextValue.equals(edittext)){ 
		              editTextValue= true;
		              break;
		          }
		      }
		      return editTextValue;
      }
	  
	  /*
	  public List<Comment> getAllComments() {
		    List<Comment> comments = new ArrayList<Comment>();

		    Cursor cursor = database.query(Dbasehelper.TABLE_NAME3,
		        allColumns3, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Comment comment = cursorToComment(cursor);
		      comments.add(comment);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    return comments;
		  }
	  private Comment cursorToComment(Cursor cursor) {
		    Comment comment = new Comment();
		    comment.setId(cursor.getLong(0));
		    comment.setComment(cursor.getString(1));
		    return comment;
		  }*/
	  public void getNSm(String result,String lat,String lon) {
	        String countQuery = "SELECT * FROM " +Dbasehelper.TABLE_NAME;
	        Cursor cursor = database.rawQuery(countQuery, null);
	        cursor.moveToFirst();
	        SmsManager smsManager = SmsManager.getDefault();
	        while(!cursor.isAfterLast())
	        {
	        	if(cursor!=null)
	        	{
	        		smsManager.sendTextMessage(cursor.getString(2), null,"I am at risk, location-" +result+"::goto:"+"http://maps.google.com/maps?z=12&t=m&q=loc:"+lat+"+"+lon+"", null, null);
	        	}
	        	cursor.moveToNext();
	        }
	       
	        cursor.close();
	        }
}

