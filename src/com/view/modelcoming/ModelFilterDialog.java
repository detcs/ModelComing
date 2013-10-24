package com.view.modelcoming;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class ModelFilterDialog extends Dialog {

	Context context;
	public ModelFilterDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	public ModelFilterDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.modelfilter_dialog);
    }


}
