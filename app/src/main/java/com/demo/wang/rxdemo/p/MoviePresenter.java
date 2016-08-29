package com.demo.wang.rxdemo.p;

import com.demo.wang.rxdemo.m.MovieService;
import com.demo.wang.rxdemo.m.RxService;
import com.demo.wang.rxdemo.m.entity.MovieEntity;
import com.demo.wang.rxdemo.v.IMovieFragment;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Title:MoviePresenter
 * Package:com.demo.wang.rxdemo.p
 * Description:TODO
 * Author: wwh@tomcat360.com
 * Date: 16/8/29
 * Version: V1.0.0
 * 版本号修改日期修改人修改内容
 */

public class MoviePresenter extends BaseFragmentPresenter<IMovieFragment> {
	private StringBuffer content=new StringBuffer();

	public void getMovieList(int start,int count){
		RxService.createApi(MovieService.class)
				.getTopMovie(start,count)
				.compose(getView().<MovieEntity>bindToLifecycle())	//自动取消订阅,防止内存泄露
				.flatMap(new Func1<MovieEntity, Observable<MovieEntity.SubjectsEntity>>() {
					@Override
					public Observable<MovieEntity.SubjectsEntity> call(MovieEntity move) {
						return Observable.from(move.getSubjects());
					}
				})
				.map(new Func1<MovieEntity.SubjectsEntity, String>() {
					@Override
					public String call(MovieEntity.SubjectsEntity student) {
						return student.getTitle();
					}
				})

				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<String>() {
					@Override
					public void onCompleted() {
						getView().getDataSuccess(content.toString());
					}

					@Override
					public void onError(Throwable e) {
						getView().showMessage(e.toString());
					}

					@Override
					public void onNext(String str) {
						content.append(str+"\n");
					}
				});
	}
}
