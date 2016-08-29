package com.demo.wang.rxdemo.v;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.wang.rxdemo.R;

import java.util.List;

/**
 * @author wangwenhao
 *
 */
public class ViewPagerIndicator extends LinearLayout
{
	/**
	 * 选中栏字体颜色
	 */
	private int colorSelected;
	/**
	 * 未选中栏字体颜色
	 */
	private int colorNormal;
	/**
	 * 线颜色
	 */
	private int mlineColor;
	/**
	 * 线高度
	 */
	private int mlineHeight;

	/**
	 * 栏目字体大小
	 */
	private int colorSize;
	/**
	 * 画笔类型
	 */
	private int paintType;
	/**
	 * 绘制三角形的画笔
	 */
	private Paint mPaint;
	/**
	 * path构成一个三角形
	 */
	private Path mPath;
	/**
	 * 宽度
	 */
	private int mTriangleWidth;
	/**
	 * 偏移宽度
	 */
	private int mAttachWidth;
	/**
	 * 三角形的高度
	 */
	private int mTriangleHeight;

	/**
	 * 三角形的宽度为单个Tab的1/6
	 */
	private static final float RADIO_TRIANGEL = 1.0f / 6;
	/**
	 * 线形的宽度为单个Tab的1/6
	 */
	private static final float RADIO_LINE = 6.0f / 7;
	/**
	 * 三角形的最大宽度
	 */
	private final int DIMENSION_TRIANGEL_WIDTH = (int) (getScreenWidth() / 3 * RADIO_TRIANGEL);

	/**
	 * 初始时，三角形指示器的偏移量
	 */
	private int mInitTranslationX;
	/**
	 * 手指滑动时的偏移量
	 */
	private float mTranslationX;

	/**
	 * 默认的Tab数量
	 */
	private static final int COUNT_DEFAULT_TAB = 4;
	/**
	 * tab数量
	 */
	private int mTabVisibleCount = COUNT_DEFAULT_TAB;

	/**
	 * tab上的内容
	 */
	private List<String> mTabTitles;
	/**
	 * 与之绑定的ViewPager
	 */
	public ViewPager mViewPager;

	/**
	 * 标题正常时的颜色
	 */
	private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;
	/**
	 * 标题背景默认的颜色
	 */
	private static final int COLOR_BG_NORMAL = 0x77FDFF00;
	/**
	 * 标题选中时的颜色
	 */
	private static final int COLOR_TEXT_HIGHLIGHTCOLOR = 0xFFFFFFFF;
	/**
	 * 栏目字体大小
	 */
	private static final int SIZE_TEXT_DEFAULT=16;

	public ViewPagerIndicator(Context context)
	{
		this(context, null);
	}

	public ViewPagerIndicator(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// 获得自定义属性，tab的数量
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
		mTabVisibleCount = a.getInt(R.styleable.ViewPagerIndicator_item_count,COUNT_DEFAULT_TAB);
		colorNormal=a.getInt(R.styleable.ViewPagerIndicator_item_text_color_normal,COLOR_TEXT_NORMAL);
		colorSelected=a.getInt(R.styleable.ViewPagerIndicator_item_text_color_selected,COLOR_TEXT_HIGHLIGHTCOLOR);
		colorSize=a.getInt(R.styleable.ViewPagerIndicator_item_text_size,SIZE_TEXT_DEFAULT);
		paintType=a.getInt(R.styleable.ViewPagerIndicator_item_bottom_type,0);	//画笔类型 0 线 1：三角形
		mlineColor=a.getInt(R.styleable.ViewPagerIndicator_item_line_color,COLOR_TEXT_HIGHLIGHTCOLOR);	//画线颜色
		mlineHeight=a.getInt(R.styleable.ViewPagerIndicator_item_line_height,10);	//画线高度
		if (mTabVisibleCount < 0)
			mTabVisibleCount = COUNT_DEFAULT_TAB;
		a.recycle();
		initPaint();


	}

	/**
	 * 初始化画笔
	 */
	private void initPaint(){
		switch (paintType){
			case 0:
				//线型
				mPaint = new Paint();
				mPaint.setColor(mlineColor);
				mPaint.setStrokeWidth(mlineHeight);
				break;
			case 1:
				// 初始化三角形画笔
				mPaint = new Paint();
				mPaint.setAntiAlias(true);
				mPaint.setColor(Color.parseColor("#ffffffff"));
				mPaint.setStyle(Style.FILL);
				mPaint.setPathEffect(new CornerPathEffect(3));//设置折点角度，越大角度越圆
				break;

		}

	}
	/**
	 * 绘制指示器
	 */
	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		canvas.save();
		// 画笔平移到正确的位置
		canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
		switch (paintType){
			case 0:
				canvas.drawLine(mAttachWidth,0,mTriangleWidth+mAttachWidth,0,mPaint);
				break;
			case 1:
				canvas.drawPath(mPath, mPaint);//画三角形
				break;
		}


		canvas.restore();

		super.dispatchDraw(canvas);
	}

	/**
	 * 初始化指示器的宽度
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		switch (paintType){
			case 0:
				mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_LINE);// 6/7 of
				mAttachWidth=(int) (w / mTabVisibleCount * (1-RADIO_LINE))/2;
				break;
			case 1:
				mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGEL);// 1/6 of
				// width
				mTriangleWidth = Math.min(DIMENSION_TRIANGEL_WIDTH, mTriangleWidth);
				// 初始化三角形
				initTriangle();

				// 初始时的偏移量
				mInitTranslationX = getWidth() / mTabVisibleCount / 2 - mTriangleWidth
						/ 2;
				break;
		}

	}

	/**
	 * 设置可见的tab的数量
	 * 
	 * @param count
	 */
	public void setVisibleTabCount(int count)
	{
		this.mTabVisibleCount = count;
	}

	/**
	 * 设置tab的标题内容 可选，可以自己在布局文件中写死
	 * 
	 * @param datas
	 */
	public void setTabItemTitles(List<String> datas)
	{
		// 如果传入的list有值，则移除布局文件中设置的view
		if (datas != null && datas.size() > 0)
		{
			this.removeAllViews();
			this.mTabTitles = datas;

			for (String title : mTabTitles)
			{
				// 添加view
				addView(generateTextView(title));
			}
			// 设置item的click事件
			setItemClickEvent();
		}

	}

	/**
	 * 对外的ViewPager的回调接口
	 * 
	 * @author wangwenhao
	 * 
	 */
	public interface PageChangeListener
	{
		void onPageScrolled(int position, float positionOffset,
							int positionOffsetPixels);

		void onPageSelected(int position);

		void onPageScrollStateChanged(int state);
	}

	// 对外的ViewPager的回调接口
	private PageChangeListener onPageChangeListener;

	// 对外的ViewPager的回调接口的设置
	public void setOnPageChangeListener(PageChangeListener pageChangeListener)
	{
		this.onPageChangeListener = pageChangeListener;
	}

	// 设置关联的ViewPager
	public void setViewPager(ViewPager mViewPager, int pos)
	{
		this.mViewPager = mViewPager;

		this.mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
				// 设置字体颜色高亮
				resetTextViewColor();
				highLightTextView(position);

				// 回调
				if (onPageChangeListener != null)
				{
					onPageChangeListener.onPageSelected(position);
				}
				Log.d("Indicator","selected"+position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels)
			{
				// 滚动
				scroll(position, positionOffset);

				// 回调
				if (onPageChangeListener != null)
				{
					onPageChangeListener.onPageScrolled(position,
							positionOffset, positionOffsetPixels);
				}

			}

			@Override
			public void onPageScrollStateChanged(int state)
			{
				// 回调
				if (onPageChangeListener != null)
				{
					onPageChangeListener.onPageScrollStateChanged(state);
				}

			}
		});
		// 设置当前页
		this.mViewPager.setCurrentItem(pos);
		// 高亮
		highLightTextView(pos);
	}

	/**
	 * 高亮文本
	 * 
	 * @param position
	 */
	protected void highLightTextView(int position)
	{
		View view = getChildAt(position);
		if (view instanceof TextView)
		{
			((TextView) view).setTextColor(colorSelected);
		}

	}

	/**
	 * 重置文本颜色
	 */
	private void resetTextViewColor()
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			View view = getChildAt(i);
			if (view instanceof TextView)
			{
				((TextView) view).setTextColor(colorNormal);
			}
		}
	}

	/**
	 * 设置点击事件
	 */
	public void setItemClickEvent()
	{
		int cCount = getChildCount();
		for (int i = 0; i < cCount; i++)
		{
			final int j = i;
			View view = getChildAt(i);
			view.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					mViewPager.setCurrentItem(j);
				}
			});
		}
	}

	/**
	 * 根据标题生成我们的TextView
	 * 
	 * @param text
	 * @return
	 */
	private TextView generateTextView(String text)
	{
		TextView tv = new TextView(getContext());
		LayoutParams lp = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.width = getScreenWidth() / mTabVisibleCount;
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(colorNormal);
		tv.setText(text);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, colorSize);
		tv.setLayoutParams(lp);
		return tv;
	}

	/**
	 * 初始化三角形指示器
	 */
	private void initTriangle()
	{
		mPath = new Path();
		mTriangleHeight = (int) (mTriangleWidth / 2 / Math.sqrt(2));
		mPath.moveTo(0, 0);
		mPath.lineTo(mTriangleWidth, 0);
		mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
		mPath.close();
	}

	/**
	 * 指示器跟随手指滚动，以及容器滚动
	 *
	 * @param position
	 * @param offset
	 */
	public void scroll(int position, float offset)
	{
		/**
		 * <pre>
		 *  0-1:position=0 ;1-0:postion=0;
		 * </pre>
		 */
		// 不断改变偏移量，invalidate
		mTranslationX = getWidth() / mTabVisibleCount * (position + offset);

		int tabWidth = getScreenWidth() / mTabVisibleCount;

		// 容器滚动，当移动到倒数最后一个的时候，开始滚动
		if (offset > 0 && position >= (mTabVisibleCount - 2)
				&& getChildCount() > mTabVisibleCount&& position < (getChildCount()-2))
		{
			if (mTabVisibleCount != 1)
			{
				this.scrollTo((position - (mTabVisibleCount - 2)) * tabWidth
						+ (int) (tabWidth * offset), 0);
			} else
			// 为count为1时 的特殊处理
			{
				this.scrollTo(
						position * tabWidth + (int) (tabWidth * offset), 0);
			}
		}

		invalidate();
	}

	/**
	 * 设置布局中view的一些必要属性；如果设置了setTabTitles，布局中view则无效
	 */
	@Override
	protected void onFinishInflate()
	{
		Log.e("TAG", "onFinishInflate");
		super.onFinishInflate();

		int cCount = getChildCount();

		if (cCount == 0)
			return;

		for (int i = 0; i < cCount; i++)
		{
			View view = getChildAt(i);
			LayoutParams lp = (LayoutParams) view
					.getLayoutParams();
			lp.weight = 0;
			lp.width = getScreenWidth() / mTabVisibleCount;
			view.setLayoutParams(lp);
		}
		// 设置点击事件
		setItemClickEvent();

	}

	/**
	 * 获得屏幕的宽度
	 * 
	 * @return
	 */
	public int getScreenWidth()
	{
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

}
