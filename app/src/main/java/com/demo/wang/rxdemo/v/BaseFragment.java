package com.demo.wang.rxdemo.v;

import android.os.Bundle;


public class BaseFragment extends LazyFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		setTranslucentStatus();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


	@Override
	protected void lazyLoad() {
		
	}


}
