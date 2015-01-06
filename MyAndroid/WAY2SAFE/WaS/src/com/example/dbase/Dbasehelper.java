package com.example.dbase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Dbasehelper extends SQLiteOpenHelper{
	
	 public static final String TABLE_NAME1 = "userdata";
	 public static final String COLUMN_ID1= "_id";
	 public static final String COLUMN_UNAME = "name";
	 public static final String COLUMN_UMAIL = "mailid";
	 public static final String COLUMN_UPASS = "pwd";
	 public static final String COLUMN_UHLP = "helpc"; 
	 public static final String COLUMN_ADDRESS = "address";
	  private static final String DATABASE_NAME = "WAS.db";
	  private static final int DATABASE_VERSION = 1;
	  public static final String TABLE_NAME2 = "URGCONTCT";
	  public static final String CONTC_AUTH = "autho";
	  public static final String AUTH_NUM = "authonum";
	  public static final String AUTH_SMS="authosms";
	  public static final String TABLE_NAME="friends";
		public static final String KEY_FNAME="fname";
		public static final String KEY_LNAME="phone";
		public static final String KEY_ID="id";
	  public static final String TABLE_NAME4 = "LOK_DET";
	  public static final String LOC_LAT = "lati";
	  public static final String LOC_LON = "lon";
	  public static final String LOC_ADR = "adrs";
	  public static final String LOC_TM = "time";
	  private static final String DATABASE_CREATE1 = "create table "
		      + TABLE_NAME1 + "(" + COLUMN_ID1
		      + " integer primary key , " + COLUMN_UNAME
		      + " text not null,"+COLUMN_UMAIL+" text  default 'nomail',"+COLUMN_ADDRESS+" text default 'no address',"+COLUMN_UPASS+" text not null,"+COLUMN_UHLP+" text not null);";
	
	  private static final String DATABASE_CREATE2 = "create table "
		      + TABLE_NAME2 + "(" + COLUMN_ID1
		      + " integer primary key autoincrement, " + CONTC_AUTH
		      + " text not null,"+AUTH_NUM+" text not null,"+AUTH_SMS+" text not null);";
	  //private static final String DATABASE_CREATE3 = "CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_FNAME+" TEXT, "+KEY_LNAME+" TEXT)";
	  
	  private static final String DATABASE_CREATE4 = "create table "
		      + TABLE_NAME4 + "(" + COLUMN_ID1
		      + " integer primary key autoincrement, " + LOC_LAT
		      + " text  not null,"+LOC_LON+" text not null,"+LOC_ADR+"text not null,"+LOC_TM+"text not null);";
	  
	  
	  public Dbasehelper(Context context) {
		    super(context, DATABASE_NAME, null, DATABASE_VERSION);
		  }
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_FNAME+" TEXT, "+KEY_LNAME+" TEXT)";
		db.execSQL(CREATE_TABLE);

		db.execSQL(DATABASE_CREATE1);
		db.execSQL(DATABASE_CREATE2);
		//db.execSQL(DATABASE_CREATE3);
		db.execSQL(DATABASE_CREATE4);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 Log.w(Dbasehelper.class.getName(),
			        "Upgrading database from version " + oldVersion + " to "
			            + newVersion + ", which will destroy all old data");
			    db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME1 );
			    db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME2 );
			    db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME );
			    db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME4 );
			    onCreate(db);
	}
}
