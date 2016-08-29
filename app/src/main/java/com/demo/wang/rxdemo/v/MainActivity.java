package com.demo.wang.rxdemo.v;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.demo.wang.rxdemo.R;
import com.demo.wang.rxdemo.m.MyFragmentPagerAdapter;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@Bind(R.id.id_indicator)
	ViewPagerIndicator idIndicator;
	@Bind(R.id.vPager)
	ViewPager vPager;
	private List<String> mDatas = Arrays.asList("Fragment1", "Fragment2","Fragment3","Fragment4");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		initView();
	}

	public void initView() {
		ArrayList<RxFragment> fragmentsList = new ArrayList<RxFragment>();
		for(int i=0;i<mDatas.size();i++){
			fragmentsList.add(MovieFragment.newInstance(i));
		}
		vPager.setCurrentItem(0);
		vPager.setAdapter(new MyFragmentPagerAdapter(this
				.getSupportFragmentManager(), fragmentsList));
		idIndicator.setTabItemTitles(mDatas);
		idIndicator.setViewPager(vPager,0);
	}
}
