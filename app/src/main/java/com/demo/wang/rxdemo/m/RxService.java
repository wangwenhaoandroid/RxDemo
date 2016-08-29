package com.demo.wang.rxdemo.m;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Title:RxService
 * Package:com.xuyuanshu.m.Service
 * Description:TODO
 * Author: wwh@tomcat360.com
 * Date: 16/8/28
 * Version: V1.0.0
 * 版本号修改日期修改人修改内容
 */

public class RxService {
	private static String baseUrl="https://api.douban.com/v2/movie/";
	private static Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.client(getClient())
				.build();


	private static OkHttpClient getClient(){
		return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
			@Override
			public Response intercept(Interceptor.Chain chain) throws IOException {
				return chain.proceed(chain.request() // originalRequest
						.newBuilder()
						.build());
			}
		}).build();

	}
	public static <T> T createApi(Class<T> clazz) {

		return retrofit.create(clazz);
	}
}
