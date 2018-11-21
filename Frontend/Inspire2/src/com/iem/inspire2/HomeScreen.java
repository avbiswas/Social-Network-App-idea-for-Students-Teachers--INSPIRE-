package com.iem.inspire2;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class HomeScreen extends Activity implements OnItemClickListener  {
	private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence str;
    private CharSequence mDrawerTitle;
	private String[] mTitle;
	String JSONresponse;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Bundle bundle;
	JSONObject Json;
	String name;
	String id;
	String password;
	String email;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		   JSONresponse = extras.getString("user");
		}
		
		try {
			Json=new JSONObject(JSONresponse);
			JSONArray temp=Json.getJSONArray("data");
			Json=temp.getJSONObject(0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			name=Json.getString("name");
			email=Json.getString("email");
			password=Json.getString("password");
			id=Json.getString("id");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentView(R.layout.activity_home_screen);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        
		str = mDrawerTitle = getTitle();
        
		
		mTitle = getResources().getStringArray(R.array.Menu);
        mDrawerList = (ListView) findViewById(R.id.navigation_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.fragment_navigation_drawer, mTitle));
        // Set the list's click listener
        getActionBar().setTitle("HOME");
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 72, 31)));;
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,  R.string.navigation_drawer_open,R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerList.setOnItemClickListener(this);
        gotoWelcomeScreen();
	}
    public void gotoWelcomeScreen(){
    	
        bundle = new Bundle();
        bundle.putString("name", name);
        
        FragmentManager frm=getFragmentManager();
		Fragment fragment=null;
		fragment=new Profile();
		fragment.setArguments(bundle);
	       
		frm.beginTransaction().replace(R.id.container, fragment).commit();
		
	
    }
    public String getID(){
    	return id;
    }
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.home_screen, menu);
	        return super.onCreateOptionsMenu(menu);
	    }
	 @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	        // If the nav drawer is open, hide action items related to the content view
	        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	        return super.onPrepareOptionsMenu(menu);
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	         // The action bar home/up action should open or close the drawer.
	         // ActionBarDrawerToggle will take care of this.
	        if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
	        // Handle action buttons
			return false;
	        	    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {
		// TODO Auto-generated method stub
		FragmentManager frm=getFragmentManager();
		Fragment fragment=null;
		switch(pos){
		case 0: fragment=new Edit();
				break;
		case 1:	bundle.putString("id", id);
        		fragment=new Compose();
				fragment.setArguments(bundle);
				break;
		case 2:	bundle.putString("id", id);
				fragment=new Latest();
				fragment.setArguments(bundle);
				break;
		case 3: gotoWelcomeScreen();
				return;
		}
		
		frm.beginTransaction().replace(R.id.container, fragment).commit();
	
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		}
	
}
