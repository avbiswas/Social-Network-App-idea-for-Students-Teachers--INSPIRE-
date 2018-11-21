package com.iem.inspire2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	Button signIn;
	EditText ID;
	EditText password;
	
	String IP="http://192.168.0.103/inspire/login.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Settings.System.putInt( this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		signIn=new Button(this);
		signIn=(Button)findViewById(R.id.button1);
		
		signIn.setOnClickListener(this);
		ID=new EditText(this);
		password=new EditText(this);
		ID=(EditText)findViewById(R.id.editText1);
		password=(EditText)findViewById(R.id.editText2);
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		IP=IP+"?id="+ID.getText()+"&password="+password.getText();
		System.out.println(IP);
		(new CheckLoginAttempt(this)).execute();
	}
	public void handleResponse(String response){
		if(response.equalsIgnoreCase("error")){

			Toast.makeText(this, "Wrong UserID or Password", Toast.LENGTH_SHORT).show();
			ID.setText("");
			password.setText("");
			IP="http://192.168.0.103/inspire/login.php";
		}
		else{
		Intent intent=new Intent(this,HomeScreen.class);
		intent.putExtra("user", response);
		startActivity(intent);
		}
				

	}
	public class CheckLoginAttempt extends AsyncTask<Void, Void, String>{
		private MainActivity login;
		CheckLoginAttempt(MainActivity login){
			this.login=login;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result==null){
				Toast.makeText(getApplicationContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();
				
			}
			login.handleResponse(result);
			
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String response="0";
			try {
				HttpURLConnection connection=(HttpURLConnection) (new URL(IP)).openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Connection", "close");
				BufferedReader br=new BufferedReader(new InputStreamReader(new BufferedInputStream(connection.getInputStream())));
				response=br.readLine();
			    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			return response;
		}
		
	}
}
