package com.iem.inspire2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;


























import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Compose extends Fragment implements android.view.View.OnClickListener, OnLongClickListener {
	public static final int GALLERY_CODE=1;
	String userID;
	Bitmap screen;
	View rootView;
	LinearLayout ButtonUI;
	LinearLayout UI;
	Bitmap bitmap;
	EditText edit;
	Button imageUpload;
	//String IP="http://www.hypemeup.org/test.php";
	String IP="http://192.168.0.103/inspire/test.php";
	Button newText;
	Button submit;
	Button okay;
	Button notOkay;
	String encodedString;
	ImageView latest_uploaded_Image;
	LinearLayout text;
	EditText newTextField;
	ArrayList<View> list;
	TextView editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	userID= getArguments().getString("id");
    	rootView = inflater.inflate(R.layout.layout_compose, container, false);
        getActivity().getActionBar().setTitle("COMPOSE");
        ButtonUI=(LinearLayout)rootView.findViewById(R.id.linearButtons);
    	submit=(Button)rootView.findViewById(R.id.button1);
        imageUpload=(Button)rootView.findViewById(R.id.button2);
        newText=(Button)rootView.findViewById(R.id.button3);
        text=(LinearLayout)rootView.findViewById(R.id.linearText);
        UI=(LinearLayout)rootView.findViewById(R.id.linear_UI);
        
        list=new ArrayList<View>();
        edit = (EditText)rootView.findViewById(R.id.editText1);
        list.add(edit);
        submit.setOnClickListener(this);
        imageUpload.setOnClickListener(this);
        newText.setOnClickListener(this);
        latest_uploaded_Image=new ImageView(getActivity());
	    
        return rootView;
    }
	private Bitmap takeScreenShot() 
	{
	   
	    //HorizontalScrollView z = (HorizontalScrollView) rootView.findViewById(R.id.linearText);
	    int totalHeight = text.getHeight();
	    int totalWidth = text.getWidth();

	    Bitmap b = getBitmapFromView(text,totalHeight,totalWidth);             

	   return b;
		}

	private void upload(Bitmap b){
		String root = Environment.getExternalStorageDirectory().toString();
		   File myDir = new File(root + "/_Inspire_images");    
		   myDir.mkdirs();
		   Random generator = new Random();
		   int n = 10000;
		   n = generator.nextInt(n);
		   String fname = "Image-"+ n +".jpg";
		   File file = new File (myDir, fname);
		   if (file.exists ()) file.delete (); 
		   try {
		       FileOutputStream out = new FileOutputStream(file);
		       b.compress(Bitmap.CompressFormat.JPEG, 90, out);
		       out.flush();
		       out.close();

		   } catch (Exception e) {
		       e.printStackTrace();
		   }
	}

	public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

	    Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth,totalHeight , Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(returnedBitmap);
	    Drawable bgDrawable = view.getBackground();
	    if (bgDrawable != null)
	        bgDrawable.draw(canvas);
	    else
	        canvas.drawColor(Color.WHITE);
	    view.draw(canvas);
	    return returnedBitmap;
	}
	    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==1){
			if (resultCode==0){
				Toast.makeText(getActivity(), "Upload Cancelled",Toast.LENGTH_SHORT).show();
			}
			if (resultCode==-1){
				if (data!=null){
				Uri uriTemp=data.getData();
				latest_uploaded_Image=new ImageView(getActivity());
			     
				latest_uploaded_Image.setImageURI(uriTemp);
				latest_uploaded_Image.setLongClickable(true);
				latest_uploaded_Image.setOnLongClickListener(this);
				list.add(latest_uploaded_Image);
				text.addView(latest_uploaded_Image);
				}
			}
		}		
	}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v==imageUpload){
				Intent galleryOpen=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(galleryOpen, GALLERY_CODE);
			}
			if (v==newText){
				newTextField=new EditText(getActivity());
				newTextField.setBackgroundColor(Color.WHITE);
				list.add(newTextField);
				text.addView(newTextField);
				newTextField.requestFocus();
			}
			if(v==submit){
				getActivity().getActionBar().setTitle("CONFIRM DOCUMENT");
		        
				
				if(list.size()<2){
					Toast.makeText(getActivity(), "NO CONTENT ENTERED!", Toast.LENGTH_SHORT).show();
					return;
				}
				list.get(0).requestFocus();
				edit.setCursorVisible(false);
				 InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				    imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
				screen=takeScreenShot();
				text.removeAllViews();
				ImageView temp=new ImageView(getActivity());
				UI.removeAllViews();
				temp.setImageBitmap(screen);
				UI.addView(temp);
				okay=new Button(getActivity());
				notOkay=new Button(getActivity());
				okay.setOnClickListener(this);
				notOkay.setOnClickListener(this);
				okay.setBackgroundResource(R.drawable.submit_b);
				notOkay.setBackgroundResource(R.drawable.edit_b);
				okay.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				notOkay.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				
				UI.addView(notOkay);
				UI.addView(okay);
				
			}
			if (v==okay){
				//upload(screen);
				new uploadImage(screen).execute();
			}
			if (v==notOkay){
				getActivity().getActionBar().setTitle("COMPOSE");
		        edit.setCursorVisible(true);
				UI.removeAllViews();	
				
				for (int i=0;i<list.size();i++){
					text.addView(list.get(i));
				}
				UI.addView(text);
				UI.addView(ButtonUI);
				UI.addView(submit);
				}
			}
	
	
			
		
		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			if (list.contains(v)){
				list.remove(list.indexOf(v));
				text.removeAllViews();
				for (int i=0;i<list.size();i++){
					text.addView(list.get(i));
			
				}
				
			}
			return true;
		}
		public class uploadImage extends AsyncTask<Void, Void, Void>{
			Bitmap b;
			String encodedImage;
			String heading;
			public uploadImage(Bitmap bitmap){
				this.b=bitmap;
				heading=edit.getText().toString();
				System.out.println(heading);
				
			}
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				ByteArrayOutputStream bytearrayoutputstream= new ByteArrayOutputStream();
				b.compress(Bitmap.CompressFormat.JPEG, 100, bytearrayoutputstream);
				encodedImage=Base64.encodeToString(bytearrayoutputstream.toByteArray(),Base64.DEFAULT);
				ArrayList<NameValuePair> sendData=new ArrayList<NameValuePair>();
				sendData.add(new BasicNameValuePair("image",encodedImage));
				sendData.add(new BasicNameValuePair("id",userID));
				sendData.add(new BasicNameValuePair("articleHeading", heading));
				HttpParams requestParams=getReqParams();
				HttpClient client=new DefaultHttpClient(requestParams);
				HttpPost post=new HttpPost(IP);
				try {
					post.setEntity(new UrlEncodedFormEntity(sendData));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					client.execute(post);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
					
					
			
			@Override
			protected void onPostExecute(Void params){
				Toast.makeText(getActivity(), "DONE", Toast.LENGTH_SHORT).show();
				
			}
			public HttpParams getReqParams(){
				HttpParams requestParams=new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(requestParams, 1000*60);

				HttpConnectionParams.setSoTimeout(requestParams, 1000*60);
				return requestParams;
			}
			
		}
}
