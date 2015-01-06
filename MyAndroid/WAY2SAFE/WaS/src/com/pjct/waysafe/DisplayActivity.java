package com.pjct.waysafe;

import java.util.ArrayList;

import com.example.dbase.Dbasehelper;
import com.pjct.waysafe.R;
import com.pjct.waysafe.R.id;
import com.pjct.waysafe.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
/**
 * activity to display all records from SQLite database
 * @author ketan(Visit my <a
 *         href="http://androidsolution4u.blogspot.in/">blog</a>)
 */
public class DisplayActivity extends Activity {

	private Dbasehelper mHelper;
	private SQLiteDatabase dataBase;

	private ArrayList<String> userId = new ArrayList<String>();
	private ArrayList<String> user_fName = new ArrayList<String>();
	private ArrayList<String> user_lName = new ArrayList<String>();

	private ListView userList;
	private AlertDialog.Builder build;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_activity);

		userList = (ListView) findViewById(R.id.List);

		mHelper = new Dbasehelper(this);
		
		//add new record
		findViewById(R.id.btnAdd).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(),
						Friends.class);
				i.putExtra("update","ok");
				startActivity(i);

			}
		});
		
		//click to update data
		userList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent i = new Intent(getApplicationContext(),
						AddActivity.class);
				i.putExtra("Fname", user_fName.get(arg2));
				i.putExtra("Lname", user_lName.get(arg2));
				i.putExtra("ID", userId.get(arg2));
				i.putExtra("update", true);
				startActivity(i);

			}
		});
		
		//long click to delete data
		userList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				build = new AlertDialog.Builder(DisplayActivity.this);
				build.setTitle("Delete " + user_fName.get(arg2) + " "
						+ user_lName.get(arg2));
				build.setMessage("Do you want to delete ?");
				build.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								Toast.makeText(
										getApplicationContext(),
										user_fName.get(arg2) + " "
												+ user_lName.get(arg2)
												+ " is deleted.", 3000).show();

								dataBase.delete(
										Dbasehelper.TABLE_NAME,
										Dbasehelper.KEY_ID + "="
												+ userId.get(arg2), null);
								displayData();
								dialog.cancel();
							}
						});

				build.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				AlertDialog alert = build.create();
				alert.show();

				return true;
			}
		});
	}

	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}

	/**
	 * displays data from SQLite
	 */
	private void displayData() {
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ Dbasehelper.TABLE_NAME, null);

		userId.clear();
		user_fName.clear();
		user_lName.clear();
		if (mCursor.moveToFirst()) {
			do {
				userId.add(mCursor.getString(mCursor.getColumnIndex(Dbasehelper.KEY_ID)));
				user_fName.add(mCursor.getString(mCursor.getColumnIndex(Dbasehelper.KEY_FNAME)));
				user_lName.add(mCursor.getString(mCursor.getColumnIndex(Dbasehelper.KEY_LNAME)));

			} while (mCursor.moveToNext());
		}
		DisplayAdapter disadpt = new DisplayAdapter(DisplayActivity.this,userId, user_fName, user_lName);
		userList.setAdapter(disadpt);
		mCursor.close();
	}

	

}
