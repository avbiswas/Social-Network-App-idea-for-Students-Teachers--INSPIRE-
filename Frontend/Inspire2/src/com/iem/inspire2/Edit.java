package com.iem.inspire2;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Edit extends Fragment implements OnClickListener{
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View v= inflater.inflate(R.layout.layout_edit, container, false);
			
	        getActivity().getActionBar().setTitle("EDIT PROFILE");
	        
	        return v;
	        
	    }
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
	 
}
