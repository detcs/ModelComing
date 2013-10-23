package com.view.modelcoming;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FirstFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView= inflater.inflate(R.layout.test, container,false);   
		FragmentTransaction ft=getFragmentManager().beginTransaction();
		ft.add(R.id.root,new FragmentPageModel());
		ft.commit();
		return rootView;
		
	}
	

}
