package com.iem.inspire2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewArticle extends Fragment implements OnClickListener{
	ImageView article;
	LayoutInflater inflater;
	EditText comment;
	LinearLayout comments_section;
	
	String articleID;
	String id;
	String IP="http://192.168.0.103/inspire/returnFile.php";
	String commentSubmitIP="http://192.168.0.103/inspire/submitComment.php";
	String commentLoadIP="http://192.168.0.103/inspire/loadComment.php";
	
	@Override
	
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 this.inflater=inflater;
		 String topic = getArguments().getString("articleTopic");
		 articleID=getArguments().getString("articleID");
		 String articleAuthor=getArguments().getString("articleAuthor");
		 getActivity().getActionBar().setTitle(topic);
		 View rootView= inflater.inflate(R.layout.layout_view_articles, container, false);
		 id=(String)((HomeScreen) getActivity()).getID();
		 article=(ImageView)rootView.findViewById(R.id.imageView1);
		 new getArticleFile(article,articleID).execute();
		 TextView author;
		 author=(TextView) rootView.findViewById(R.id.author);
		 author.setText(articleAuthor);
		 comment=(EditText) rootView.findViewById(R.id.user_comment);
		 Button submit=(Button)rootView.findViewById(R.id.submit_comment);
		 submit.setOnClickListener(this);
		 comments_section=(LinearLayout)rootView.findViewById(R.id.comments_section);
		 String data=null;
		try {
			data = URLEncoder.encode("id", "UTF-8") 
						+ "=" + URLEncoder.encode(articleID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 new loadComment(data, this).execute();
		 return rootView;
	 }
	public class getArticleFile extends AsyncTask<Void, Void, Bitmap>{
		ImageView view;
		String data;
		getArticleFile(ImageView view, String articleID){
			this.view=view;
			try {
				data=URLEncoder.encode("articleID", "UTF-8") 
				        + "=" + URLEncoder.encode(articleID, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			view.setImageBitmap(result);
			
		}
		@Override
		protected Bitmap doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Bitmap file=null;
			try {
				HttpURLConnection connection=(HttpURLConnection) (new URL(IP)).openConnection();
				connection.setDoOutput(true);
				
				OutputStreamWriter out=new OutputStreamWriter(connection.getOutputStream());
				
				out.write(data);
				out.flush();
				out.close();
				
				
				file=BitmapFactory.decodeStream(connection.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return file;
		}
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		String data=null;
		if (comment.getText().toString().equals("")){
			Toast.makeText(getActivity(), "No comment!", Toast.LENGTH_LONG).show();
		}
		else{
		try {
			data=URLEncoder.encode("articleID", "UTF-8") 
			+ "=" + URLEncoder.encode(articleID, "UTF-8")+"&"+URLEncoder.encode("userID", "UTF-8") 
			+ "=" + URLEncoder.encode(id, "UTF-8")+"&"+URLEncoder.encode("comment", "UTF-8") 
			+ "=" + URLEncoder.encode(comment.getText().toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new submitComment(data).execute();
		}
	}
	public class submitComment extends AsyncTask<Void, Void, Void>{
		String data;
		String response;
		submitComment(String data){
			this.data=data;
			
		}
		@Override
		protected void onPostExecute(Void params) {
			// TODO Auto-generated method stub
			super.onPostExecute(params);
			Toast.makeText(getActivity(),response, Toast.LENGTH_SHORT).show();;
			
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpURLConnection connection=(HttpURLConnection) (new URL(commentSubmitIP)).openConnection();
				
				
				connection.setDoOutput(true);
				OutputStreamWriter out=new OutputStreamWriter(connection.getOutputStream());
				
				out.write(data);
				out.flush();
				out.close();
				
				BufferedReader br=new BufferedReader(new InputStreamReader(new BufferedInputStream(connection.getInputStream())));
				response=br.readLine();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
			
		}
		
	}
	public class loadComment extends AsyncTask<Void, Void, String>{
		ViewArticle viewArticle;
		String data;
		loadComment(String data,ViewArticle viewArticle){
			this.data=data;
			this.viewArticle=viewArticle;
			
		}
		@Override
		protected void onPostExecute(String response) {
			// TODO Auto-generated method stub
			super.onPostExecute(response);	
			System.out.println(response);
			
			viewArticle.showComments(response);
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpURLConnection connection=(HttpURLConnection) (new URL(commentLoadIP)).openConnection();
				
				
				connection.setDoOutput(true);
				OutputStreamWriter out=new OutputStreamWriter(connection.getOutputStream());
				
				out.write(data);
				out.flush();
				out.close();
				
				BufferedReader br=new BufferedReader(new InputStreamReader(new BufferedInputStream(connection.getInputStream())));
				String response=br.readLine();
				return response;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
			
		}
		
	}
	public void showComments(String JSONresponse){
		
		View comment;
		TextView commentText;
		TextView commentAuthor;
		TextView commentDate;
		TextView upvotesNo;
		Button upvote;
		if (JSONresponse.equals("No Comments Yet"))
		{
			TextView text=(TextView)comments_section.findViewById(R.id.commentsHeader);
			text.setText(JSONresponse);
		}
		else
		{	
			TextView text=(TextView)comments_section.findViewById(R.id.commentsHeader);
			text.setText(R.string.comment);
		
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
			comment=inflater.inflate(R.layout.comment, null,false);
			commentText=(TextView)comment.findViewById(R.id.commentText);
			commentDate=(TextView)comment.findViewById(R.id.commentDate);
			commentAuthor=(TextView)comment.findViewById(R.id.comment_author);
			upvotesNo=(TextView) comment.findViewById(R.id.upvotes_no);
			upvote=(Button) comment.findViewById(R.id.upvote_button);
			
			//String id;
			
			try {
				unit=(JSONObject) article_List.get(i);
				
				commentText.setText(unit.getString("commentText"));
				commentDate.setText(unit.getString("date"));
				commentAuthor.setText(unit.getString("name"));
				id=unit.getString("commentID");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
			//upvote.setOnClickListener(this);
			comments_section.addView(comment);
			
			}
		}
	}

}
