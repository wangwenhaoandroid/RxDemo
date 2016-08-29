package com.demo.wang.rxdemo.v;


import com.trello.rxlifecycle.android.FragmentEvent;

import rx.Observable;

/**
 * Title:IBaseActivityView
 * Package:com.tomcat360.v.view_interface
 * Description:提取公共方法结束刷新，显示提示语
 * Author: wwh@tomcat360.com
 * Date: 16/4/25
 * Version: V1.0.0
 * 版本号修改日期修改人修改内容
 */
public interface IBaseFragmentView {



    /**
     * 页面显示提示语
     *
     * @param str 提示语
     */
    void showMessage(String str);

	/**
	 * RxFragment/RxActivity 中方法,声明在view中 便于在mvp中的presenter里调用
     * @param <T> T
     * @return
     */
    <T> Observable.Transformer<T, T> bindToLifecycle();

    /**
     * RxFragment/RxActivity 中方法,声明在view中 便于在mvp中的presenter里调用
     * @param <T> T
     * @return
     */
    <T> Observable.Transformer<T, T> bindUntilEvent(FragmentEvent event);

}
