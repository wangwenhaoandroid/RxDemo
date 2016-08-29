package com.demo.wang.rxdemo.p;


import com.demo.wang.rxdemo.v.IBaseActivityView;

/**
 * Title:BaseActivityPresenter
 * Package:com.xuyuanshu.presenter.presenter_impl
 * Description:TODO
 * Author: wwh@tomcat360.com
 * Date: 16/8/28
 * Version: V1.0.0
 * 版本号修改日期修改人修改内容
 */

public class BaseActivityPresenter<T extends IBaseActivityView> implements Presenter<T> {

	private T mView;

	@Override
	public void attachView(T view) {
		this.mView = view;
	}

	@Override
	public void detachView() {
		this.mView = null;
	}

	public boolean isViewAttached() {
		return mView != null;
	}

	public T getView() {
		return mView;
	}

}
