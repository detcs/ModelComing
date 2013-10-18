package com.view.modelcoming;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentPageWeibo extends Fragment{

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {        
	        return inflater.inflate(R.layout.fragment_weibo, null);       
	    }     

}
