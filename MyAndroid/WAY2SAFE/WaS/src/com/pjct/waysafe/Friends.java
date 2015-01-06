package com.pjct.waysafe;

import java.util.ArrayList;
import java.util.List;

import com.example.dbase.DbaseD;
import com.example.dbase.Dbasehelper;



import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Friends extends ListActivity {
    /** Called when the activity is first created. 
     * @return */
	ListView FrList;
	private Dbasehelper mHelper;
	private SQLiteDatabase dataBase;
	DbaseD datasource;
    class Friend {
        private String friName;
        private String friNum;

        public Friend(String stri1, String stri2) {
			// TODO Auto-generated constructor stub
        	friName = stri1;
            friNum = stri2;
		}



		public String getName() {
            return friName;
        }

       

        public String getNum() {
            return friNum;
        }


       
    }

    public class FriAdapter extends ArrayAdapter<Friend> {
        private ArrayList<Friend> items;
        private FriViewHolder friHolder;

        private class FriViewHolder {
            TextView name;
            TextView num; 
        }

        public FriAdapter(Context context, int tvResId, ArrayList<Friend> items) {
            super(context, tvResId, items);
            this.items = items;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.taxi_list_item, null);
                friHolder = new FriViewHolder();
                friHolder.name = (TextView)v.findViewById(R.id.fri_name);
                friHolder.num = (TextView)v.findViewById(R.id.fri_num);
                v.setTag(friHolder);
            } else friHolder = (FriViewHolder)v.getTag(); 

            Friend fri = items.get(pos);

            if (fri != null) {
                friHolder.name.setText(fri.getName());
                friHolder.num.setText(fri.getNum());
            }

            return v;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	datasource = new DbaseD(this);
    	  datasource.open();
    	FrList = (ListView) findViewById(android.R.id.list);
    	Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    	
    	String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
    	                ContactsContract.CommonDataKinds.Phone.NUMBER};
    	
    	Cursor people = getContentResolver().query(uri, projection, null, null, null);

    	int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
    	int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

       /* String[] taxiNames = getResources().getStringArray(R.array.taxi_name_array);
        String[] taxiAddresses = getResources().getStringArray(R.array.taxi_address_array);
*/
        ArrayList<Friend> friList = new ArrayList<Friend>();

        people.moveToFirst();
        do {
        	friList.add(new Friend(people.getString(indexName),people.getString(indexNumber)));
           
            
            // Do work...
        } while (people.moveToNext());

        setListAdapter(new FriAdapter(this, R.layout.taxi_list_item, friList)); 
        mHelper=new Dbasehelper(this);
       
    }
    protected void onListItemClick(ListView l, View v, int position, long id) {

    	super.onListItemClick(l, v, position, id);
    	if(v!=null)
    	{
    		Cursor c=null;
    	TextView txtName = (TextView) v.findViewById(R.id.fri_name);
    	TextView txtNum = (TextView) v.findViewById(R.id.fri_num);
    	Log.e("okstartr", "bdok");
    	
    	dataBase=mHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		Log.e("okstartr", "bdok");
	
		 if(datasource.isVExist(txtNum.getText().toString()))
		 {
			 
		 
		    	Toast.makeText(this, " value already there", Toast.LENGTH_LONG).show();
		 }
		 else{
			 values.put(Dbasehelper.KEY_FNAME,txtName.getText().toString());
				values.put(Dbasehelper.KEY_LNAME,txtNum.getText().toString());
				dataBase.insert(Dbasehelper.TABLE_NAME, null, values);
				dataBase.close();
				finish();

			 Toast.makeText(this, "saved", Toast.LENGTH_LONG).show();
		 }
    	/*Intent i = new Intent(getApplicationContext(),
				DisplayActivity.class);
	
		i.putExtra("update", true);
		startActivity(i);*/
    	}
    	
    	}
    
}