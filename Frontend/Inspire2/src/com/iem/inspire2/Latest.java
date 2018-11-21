package com.iem.inspire2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.layout;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Latest extends Fragment implements OnClickListener {
	LinearLayout rootLayout;
	LayoutInflater inflater;
	View rootView;
	String JSONresponse=" ";
	String userID;
	ArrayList<String> article_ID;
	String IP="http://192.168.0.103/inspire/fetchArticles.php";
	ArrayList<View> article;
	ArrayList<TextView> article_what;
	ArrayList<TextView> article_who;
	ArrayList<TextView> article_when;
	ArrayList<TextView> article_likes;
	 
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 this.inflater=inflater;
		 
		 getActivity().getActionBar().setTitle("TIMELINE");
		 article=new ArrayList<View>();
		 article_what=new ArrayList<TextView>();
		 article_who=new ArrayList<TextView>();
		 article_when=new ArrayList<TextView>();
		 article_likes=new ArrayList<TextView>();
		 article_ID=new ArrayList<String>();
		  rootView= inflater.inflate(R.layout.layout_latest, container, false);
		  rootLayout=(LinearLayout)rootView.findViewById(R.id.latest);
		  (new fetchArticles(this)).execute();
		  
		  createNewArticle();
		  //article=inflater.inflate(R.layout.layout_articles, null,true);
		  
		  
		  
		  return rootView;
	    }

	private void createNewArticle() {
		// TODO Auto-generated method stub
		refresh();
		if (!JSONresponse.equals(" ")){
			JSONObject Json;
			JSONObject unit;
			JSONArray article_List = null;
			try {
				Json=new JSONObject(JSONresponse);
				article_List=Json.getJSONArray("data");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int i=0;i<article_List.length();i++){
			article.add(inflater.inflate(R.layout.layout_articles, null,true));
			article_what.add((TextView)article.get(i).findViewById(R.id.article_title));
			article_who.add((TextView)article.get(i).findViewById(R.id.article_author));
			article_when.add((TextView)article.get(i).findViewById(R.id.article_date));
			article_likes.add((TextView)article.get(i).findViewById(R.id.article_upvotes));
			
			
			
			
			try {
				unit=(JSONObject) article_List.get(i);
				
				article_ID.add(unit.getString("articleID"));
				article_what.get(i).setText(unit.getString("articleHeader"));
				article_who.get(i).setText("Author:"+unit.getString("authorName"));
				article_when.get(i).setText("Upload Date:"+unit.getString("date"));
				article_likes.get(i).setText("Upvotes: 0");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			article.get(i).setOnClickListener(this);
	        
			rootLayout.addView(article.get(i));
			
			}
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle bundle=new Bundle();
		int index=article.indexOf(v);
		bundle.putString("articleAuthor", article_who.get(index).getText().toString());
		bundle.putString("articleTopic", article_what.get(index).getText().toString());
		bundle.putString("articleID", article_ID.get(index));
		
		Fragment fragment=new ViewArticle();
		fragment.setArguments(bundle);
		FragmentManager frm=getFragmentManager();
		frm.beginTransaction().replace(R.id.container, fragment).commit();
		
			
	}
	
	private void refresh() {
		// TODO Auto-generated method stub
		article.clear();
		article_what.clear();
		rootLayout.removeAllViews();
	}

	public void handleResponse(String result) {
		// TODO Auto-generated method stub
		JSONresponse=result;
		createNewArticle();
		
	}

	public class fetchArticles extends AsyncTask<Void, Void, String>{
		private Latest latest_object;
		fetchArticles(Latest obj){
			this.latest_object=obj;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			latest_object.handleResponse(result);
			
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String response="0";
			try {
				HttpURLConnection connection=(HttpURLConnection) (new URL(IP)).openConnection();
				BufferedReader br=new BufferedReader(new InputStreamReader(new BufferedInputStream(connection.getInputStream())));
				response=br.readLine();
			    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
		}
		
	}

	
}
