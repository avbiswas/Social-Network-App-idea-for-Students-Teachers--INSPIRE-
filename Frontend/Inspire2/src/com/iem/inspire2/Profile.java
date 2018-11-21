package com.iem.inspire2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Profile extends Fragment {
	TextView text;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 getActivity().getActionBar().setTitle("WELCOME");
		 String name = getArguments().getString("name");
		 View rootView= inflater.inflate(R.layout.fragment_home_screen, container, false);
		 
		 text=(TextView)rootView.findViewById(R.id.welcome);
		 text.setText("WELCOME \n"+name);
		 return rootView;
	 }
}
