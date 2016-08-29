package com.demo.wang.rxdemo.v;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.wang.rxdemo.R;
import com.demo.wang.rxdemo.p.MoviePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Title:MovieFragment
 * Package:com.demo.wang.rxdemo.v
 * Description:TODO
 * Author: wwh@tomcat360.com
 * Date: 16/8/29
 * Version: V1.0.0
 * 版本号修改日期修改人修改内容
 */

public class MovieFragment extends BaseFragment implements IMovieFragment {
	@Bind(R.id.button)
	Button button;
	@Bind(R.id.content)
	TextView contentView;

	private View rootView;
	private MoviePresenter moviePresenter;
	private int view_type;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.moviefragment, container, false);
		}
		ButterKnife.bind(this, rootView);
		return rootView;
	}

	public static MovieFragment newInstance(int s) {
		MovieFragment newFragment = new MovieFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("hello", s);
		newFragment.setArguments(bundle);
		return newFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		view_type = args != null ? args.getInt("hello") : 1;

	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		moviePresenter=new MoviePresenter();
		moviePresenter.attachView(this);
	}

	@Override
	public void getData() {
		moviePresenter.getMovieList(view_type*10+1,10);
	}

	@Override
	public void getDataSuccess(String string) {
		contentView.setText(string);
	}


	@Override
	public void showMessage(String str) {
		if (str!=null){
			Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT);
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@OnClick(R.id.button)
	public void onClick() {
		getData();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (moviePresenter.isViewAttached())
			moviePresenter.detachView();
	}
}
