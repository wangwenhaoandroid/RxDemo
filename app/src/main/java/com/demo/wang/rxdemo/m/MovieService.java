package com.demo.wang.rxdemo.m;


import com.demo.wang.rxdemo.m.entity.MovieEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Title:MovieService
 * Package:com.tomcat360.IService
 * Description:TODO
 * Author: wwh@tomcat360.com
 * Date: 16/8/16
 * Version: V1.0.0
 * 版本号修改日期修改人修改内容
 */

public interface MovieService {

	@GET("top250")
	Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

}
