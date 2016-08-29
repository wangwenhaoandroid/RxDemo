package com.demo.wang.rxdemo.p;


import com.demo.wang.rxdemo.v.IBaseFragmentView;

/**
 * Title:Presenter
 * Package:com.xuyuanshu.presenter.presenter_interface
 * Description:TODO
 * Author: wwh@tomcat360.com
 * Date: 16/8/28
 * Version: V1.0.0
 * 版本号修改日期修改人修改内容
 */

public interface FPresenter<V extends IBaseFragmentView> {
	void attachView(V view);

	void detachView();
}
