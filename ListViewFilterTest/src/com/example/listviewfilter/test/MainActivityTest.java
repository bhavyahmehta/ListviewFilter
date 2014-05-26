// @author Bhavya Mehta
package com.example.listviewfilter.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.listviewfilter.R;
import com.example.listviewfilter.ui.MainActivity;
import com.example.listviewfilter.ui.PinnedHeaderListView;

// Main activity unit test case
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	// UI elements
    private MainActivity mActivity;
    private EditText  mSearchView;
    private PinnedHeaderListView mListView; 
    private ProgressBar mProgressVew; 
    private TextView mEmptyTextView; 
   
    
    public MainActivityTest() {
        super(MainActivity.class);
    }

    
    // Initialize member variables 
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
        mSearchView = (EditText) mActivity.findViewById(R.id.search_view);
        mListView = (PinnedHeaderListView) mActivity.findViewById(R.id.list_view);
        mProgressVew=(ProgressBar)mActivity.findViewById(R.id.loading_view);
        mEmptyTextView=(TextView)mActivity.findViewById(R.id.empty_view);
    }

   
    // null check condition
    @SmallTest
    public void testPreconditions() {
        // Try to add a message to add context to your assertions. These messages will be shown if
        // a tests fails and make it easy to understand why a test failed
        assertNotNull("mActivity is null", mActivity);
        assertNotNull("mSearchView is null", mSearchView);
        assertNotNull("mListView is null", mListView);
        assertNotNull("mProgressVew is null", mProgressVew);
        assertNotNull("mEmptyTextView is null", mEmptyTextView);
    }


    // list view is in layout or not?
    @MediumTest
    public void testListViewLayout() {
        final View decorView = mActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, mListView);
        final ViewGroup.LayoutParams layoutParams =mListView.getLayoutParams();
        assertNotNull("mListView layoutParams is null",layoutParams);
        assertEquals("mListView has wrong width",layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals("mListView has wrong height",layoutParams.height, WindowManager.LayoutParams.MATCH_PARENT);
        sleep();
        assertTrue("mListView is not visible",View.VISIBLE == mListView.getVisibility());
    }
    
    
    // thread sleep for 2000 milliseconds
    private void sleep() {
    	 try {
 			Thread.sleep(2000);
 		} catch (InterruptedException e) {
 			e.printStackTrace();
 		}
	}
    
    
    // search box is in layout or not?
    @MediumTest
	public void testSearchViewLayout() {
        final View decorView = mActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, mSearchView);
        final ViewGroup.LayoutParams layoutParams =mSearchView.getLayoutParams();
        assertNotNull("mSearchView layoutParams is null",layoutParams);
        assertEquals("mSearchView has wrong width",layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals("mSearchView has wrong height",layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
        assertTrue("mSearchView is not visible",View.VISIBLE == mSearchView.getVisibility());
    }
    
    
    // search box hint text contains correct text or not?
    @SmallTest
    public void testSearchHintText() {     
        final String expected = mActivity.getString(R.string.search_hint);
        final String actual = mSearchView.getHint().toString();
        assertEquals("mSearchView contains wrong hint text", expected, actual);
    }
    
    
    // empty view contains correct text or not?
    @SmallTest
    public void testEmptyViewText() {     
        final String expected = mActivity.getString(R.string.empty_list_msg);
        final String actual = mEmptyTextView.getText().toString();
        assertEquals("mEmptyTextView contains wrong text", expected, actual);
    }
    
   
    // search list view content by latter "A"
    @MediumTest
    public void testListViewSearch() {
    	// Send string input value
    	getInstrumentation().runOnMainSync(new Runnable() {
    	    @Override
    	    public void run() {
    	    	mSearchView.requestFocus();
    	    }
    	});
    	
    	getInstrumentation().waitForIdleSync();
    	getInstrumentation().sendStringSync("A");
    	getInstrumentation().waitForIdleSync();
    	
    	sleep();
    	
		getInstrumentation().runOnMainSync(new Runnable() {
    	    @Override
    	    public void run() {
       	    	for(int i=0;i<mListView.getChildCount();i++) {
        	    	TextView list_item=(TextView) mListView.getChildAt(i);
            		assertNotNull("listItem is null", list_item);
            		final char expected = 'A';
            	    final char actual = list_item.getText().charAt(0);
            		assertEquals("search contains wrong listItem", expected, actual);
        	    }
   	    	}
    	});
    	getInstrumentation().waitForIdleSync();    		
    }
}