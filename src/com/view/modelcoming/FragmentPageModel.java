package com.view.modelcoming;

import org.apache.http.impl.client.RequestWrapper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class FragmentPageModel extends Fragment{
	

	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {  
		 	
		 	
	        return inflater.inflate(R.layout.fragment_models, null);       
	    }     
}
