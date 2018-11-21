package com.iem.inspire2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

public class connectPHP extends AsyncTask<URL, Void, Integer>{
	int x;
	@Override
	protected Integer doInBackground(URL... url2) {
		// TODO Auto-generated method stub
		HttpURLConnection connection=null;
		String IP="http://192.168.0.100/inspire/test.php";
		 URL url = null;
		 System.setProperty("http.keepAlive", "false");	
			
		try {
			url = new URL(IP);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				InputStream in=null;
		try {
			in = new BufferedInputStream(connection.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("io");
			e1.printStackTrace();
		}
	    
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
	     try {
			x=Integer.parseInt(br.readLine());
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("number");
			e.printStackTrace();
		}
		connection.disconnect();
	
		return null;
	}

	protected void onProgressUpdate() {
        //setProgressPercent();
    }

    protected void onPostExecute(Integer result) {
        System.out.println(x);
    }
}
