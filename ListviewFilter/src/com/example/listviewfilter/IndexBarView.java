//@author Bhavya
package com.example.listviewfilter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class IndexBarView extends View
{

	// index bar margin
	float mIndexbarMargin;
	
	// user touched Y axis coordinate value 
	float msideIndexY;

	boolean mIsIndexing = false;
	
	// holds current section position selected by user
	int mCurrentSectionPosition = -1;

	// array list to store section positions
	public ArrayList<Integer> mListSections;

	// array list to store listView data
	ArrayList<String> mListItems;

	// IndexBar paint objects 
	Paint mIndexPaint;

	// context
	Context mContext;

	IIndexBarFilter mIndexBarFilter;
	
	public IndexBarView(Context mContext) 
	{
		super(mContext);
		this.mContext = mContext;
	}
	
	 public IndexBarView(Context context, AttributeSet attrs) 
	 {
        super(context, attrs);
        this.mContext = context;
	 }

    public IndexBarView(Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
        this.mContext = context;
    }
    
  
	public void setData(PinnedHeaderListView mListView,ArrayList<String> mListItems, ArrayList<Integer> mListSections)
	{
		this.mListItems = mListItems;
		this.mListSections = mListSections;
		mIndexBarFilter=mListView;
		
		mIndexbarMargin = mContext.getResources().getDimension(R.dimen.index_bar_view_margin);

		// index bar item color and text size
		mIndexPaint = new Paint();
		mIndexPaint.setColor(mContext.getResources().getColor(R.color.color_black));
		mIndexPaint.setAntiAlias(true);
		mIndexPaint.setTextSize(mContext.getResources().getDimension(R.dimen.index_bar_view_text_size));
	
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		if (mListSections != null && mListSections.size() > 1) 
		{
			float sectionHeight = (getMeasuredHeight() - 2 * mIndexbarMargin)/ mListSections.size();
			float paddingTop = (sectionHeight - (mIndexPaint.descent() - mIndexPaint.ascent())) / 2;

			for (int i = 0; i < mListSections.size(); i++) 
			{
				float paddingLeft = ( getMeasuredWidth() - mIndexPaint.measureText(getSectionText(mListSections.get(i)))) / 2;
			
				canvas.drawText(getSectionText(mListSections.get(i)),
								 paddingLeft,
								 mIndexbarMargin + (sectionHeight * i) + paddingTop + mIndexPaint.descent(),
						         mIndexPaint);
			}
			
		}
		super.onDraw(canvas);
	}
	
	public String getSectionText(int sec_position) 
	{
		return mListItems.get(sec_position);
	}
	
	boolean contains(float x, float y) 
	{
		// Determine if the point is in index bar region, which includes the
		// right margin of the bar	
		return (x >= getLeft() && y >= getTop()
				&& y <= getTop()+ getMeasuredHeight());		
	}


	void filterListItem(float sideIndexY) 
	{
		msideIndexY=sideIndexY;
		
		// filter list items and get touched section position with in index bar
		mCurrentSectionPosition = (int) (((msideIndexY) - getTop() - mIndexbarMargin) /
				((getMeasuredHeight()  - (2 * mIndexbarMargin)) / mListSections.size()));
		
		if (mCurrentSectionPosition>=0 && mCurrentSectionPosition < mListSections.size()) 
		{
			int position=mListSections.get(mCurrentSectionPosition);
			
			String preview_text=mListItems.get(position);
			
			mIndexBarFilter.filterList(msideIndexY,position,preview_text);
		}
		
	}

	
	public boolean onTouchEvent(MotionEvent ev)
	{
	
		switch (ev.getAction()) 
		{
		case MotionEvent.ACTION_DOWN:
			// If down event occurs inside index bar region, start indexing
			if (contains(ev.getX(), ev.getY())) 
			{
				// It demonstrates that the motion event started from index bar
				mIsIndexing = true;
				// Determine which section the point is in, and move the list to
				// that section
				filterListItem(ev.getY());
				return true;
			}
			else
			{
				mCurrentSectionPosition = -1;
				return false;
			}			
		case MotionEvent.ACTION_MOVE:
			if (mIsIndexing)
			{
				// If this event moves inside index bar
				if (contains(ev.getX(), ev.getY())) 
				{
					// Determine which section the point is in, and move the
					// list to that section
					filterListItem(ev.getY());
					return true;
				}
				else
				{
					mCurrentSectionPosition = -1;
					return false;
				}
				
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mIsIndexing) 
			{
				mIsIndexing = false;
				mCurrentSectionPosition = -1;
			}
			break;
		}
		return false;
	
	}

}
