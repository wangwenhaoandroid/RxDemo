package com.demo.wang.rxdemo.v;


import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Title:LazyFragment
 * Package:com.tomcat360.ui.fragment
 * Description:TODO
 * Author: wwh@tomcat360.com
 * Date: 16/3/28
 * Version: V1.0.0
 * 版本号修改日期修改人修改内容
 */
public abstract class LazyFragment extends RxFragment {
	protected boolean isVisible;
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}
	protected void onVisible(){
		lazyLoad();
	}
	protected abstract void lazyLoad();
	protected void onInvisible(){}
}
