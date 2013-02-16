
package com.example.listviewfilter;

import java.util.ArrayList;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/*
 *  @Author: Bhavya
 */

public class MainActivity extends Activity 
{
	
	//listview section
    private StandardArrayAdapter arrayAdapter;
    private SectionListAdapter sectionAdapter;
    private SectionListView listView;
	
    EditText search;
	
	//sideIndex
	LinearLayout sideIndex;	
	// height of side index
    private int sideIndexHeight,sideIndexSize;
    // list with items for side index
    private ArrayList<Object[]> sideIndexList = new ArrayList<Object[]>();
   
    
	// an array with countries to display in the list
    private ArrayList<String> COUNTRIES;
    static String[] COUNTRIES_ARY = new String[]
    { "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea",
            "Eritrea", "Estonia", "Ethiopia", "Faeroe Islands",
            "Falkland Islands", "Fiji", "Finland", "Afghanistan", "Albania",
            "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
            "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia",
            "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
            "Bangladesh", "Barbados", "Belarus", "Belgium", "Monaco",
            "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar",
            "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles",
            "New Caledonia", "New Zealand", "Guyana", "Haiti",
            "Heard Island and McDonald Islands", "Honduras", "Hong Kong",
            "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq",
            "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan",
            "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
            "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Nicaragua", "Niger",
            "Nigeria", "Niue", "Norfolk Island", "North Korea",
            "Northern Marianas", "Norway", "Oman", "Pakistan", "Palau",
            "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines",
            "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
            "French Southern Territories", "Gabon", "Georgia", "Germany",
            "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada",
            "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau",
            "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico",
            "Micronesia", "Moldova", "Bosnia and Herzegovina", "Botswana",
            "Bouvet Island", "Brazil", "British Indian Ocean Territory",
            "Saint Vincent and the Grenadines", "Samoa", "San Marino",
            "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone",
            "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia",
            "South Africa", "South Georgia and the South Sandwich Islands",
            "South Korea", "Spain", "Sri Lanka", "Sudan", "Suriname",
            "Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland",
            "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand",
            "The Bahamas", "The Gambia", "Togo", "Tokelau", "Tonga",
            "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan",
            "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
            "United Arab Emirates", "United Kingdom", "United States",
            "United States Minor Outlying Islands", "Uruguay", "Uzbekistan",
            "Vanuatu", "Vatican City", "Venezuela", "Vietnam",
            "Virgin Islands", "Wallis and Futuna", "Western Sahara",
            "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso",
            "Burundi", "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada",
            "Cape Verde", "Cayman Islands", "Central African Republic", "Chad",
            "Chile", "China", "Reunion", "Romania", "Russia", "Rwanda",
            "Sqo Tome and Principe", "Saint Helena", "Saint Kitts and Nevis",
            "Saint Lucia", "Saint Pierre and Miquelon", "Belize", "Benin",
            "Bermuda", "Bhutan", "Bolivia", "Christmas Island",
            "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo",
            "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus",
            "Czech Republic", "Democratic Republic of the Congo", "Denmark",
            "Djibouti", "Dominica", "Dominican Republic",
            "Former Yugoslav Republic of Macedonia", "France", "French Guiana",
            "French Polynesia", "Macau", "Madagascar", "Malawi", "Malaysia",
            "Maldives", "Mali", "Malta", "Marshall Islands", "Yemen",
            "Yugoslavia", "Zambia", "Zimbabwe" };
   
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        search=(EditText)findViewById(R.id.search_query);
		search.addTextChangedListener(filterTextWatcher);
		listView = (SectionListView) findViewById(R.id.section_list_view);
		sideIndex = (LinearLayout) findViewById(R.id.list_index);	
		sideIndex.setOnTouchListener(new Indextouch());
		
        if(COUNTRIES_ARY.length>0)
		{
        	// not forget to sort array
            Arrays.sort(COUNTRIES_ARY);
        	COUNTRIES = new ArrayList<String>(Arrays.asList(COUNTRIES_ARY));
        	arrayAdapter =new StandardArrayAdapter(COUNTRIES);
        	
        	//adaptor for section
	        sectionAdapter = new SectionListAdapter(this.getLayoutInflater(),arrayAdapter);
	        listView.setAdapter(sectionAdapter);
	        
	        
	        PoplulateSideview();
		}
        
    }
    
    private class Indextouch implements OnTouchListener 
    {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

         	
         	if(event.getAction() ==MotionEvent.ACTION_MOVE  || event.getAction() ==MotionEvent.ACTION_DOWN)
         	{
         		 
         		 sideIndex.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rectangle_shape));
         		
         		 // now you know coordinates of touch
                 float  sideIndexX = event.getX();
                 float  sideIndexY = event.getY();

                  if(sideIndexX>0 && sideIndexY>0)
                  {
                  	 // and can display a proper item it country list
                      displayListItem(sideIndexY);
                  	
                  }
         	}
         	else
         	{
         		sideIndex.setBackgroundColor(Color.TRANSPARENT);
         	}
            

             return true;
         
		}
    	
    };
   
    public void onWindowFocusChanged(boolean hasFocus)
	{
    	 // get height when component is poplulated in window
		 sideIndexHeight = sideIndex.getHeight();
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 super.onWindowFocusChanged(hasFocus);
	}  
	  
	  
		private class StandardArrayAdapter extends BaseAdapter implements Filterable
	    {

	        private final ArrayList<String> items;

	        public StandardArrayAdapter(ArrayList<String> args) 
	        {
	            this.items = args;
	        }
	      
	        @Override
	        public View getView(final int position, final View convertView, final ViewGroup parent) 
	        {
	            View view = convertView;
	            if (view == null)
	            {
	                final LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                view = vi.inflate(R.layout.row, null);
	            }
	            TextView textView = (TextView)view.findViewById(R.id.row_title);
                if (textView != null) 
                {
                    textView.setText(items.get(position));
                }
	            return view;
	        }

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return items.size();
			}

			@Override
			public Filter getFilter() {
				Filter listfilter=new MyFilter();
				return listfilter;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return items.get(position);
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
	    }

		
		public class MyFilter extends Filter
		{

			@Override
			protected FilterResults performFiltering(CharSequence constraint)
			{
				// NOTE: this function is *always* called from a background thread, and
	            // not the UI thread.
	            constraint = search.getText().toString();
	            FilterResults result = new FilterResults();
	            if(constraint != null && constraint.toString().length() > 0)
	            {
	            	//do not show side index while filter results
	            	runOnUiThread(new Runnable() {
						
						@Override
						public void run() 
						{
							((LinearLayout)findViewById(R.id.list_index)).setVisibility(View.INVISIBLE);
						}
					});
	        
	               ArrayList<String> filt=new ArrayList<String>();
	               ArrayList<String> Items=new ArrayList<String>();
	                synchronized(this)
	                {
	                    Items=COUNTRIES;
	                }
	                for(int i = 0;i<Items.size(); i++)
	                {
	                	String item = Items.get(i);
	                   if(item.toLowerCase().startsWith(constraint.toString().toLowerCase()))
	                   {
	                    	 	filt.add(item);
	                   }
	                }
	                
	                result.count = filt.size();
	                result.values = filt;
	            }
	            else
	            {
	            	
	            	runOnUiThread(new Runnable() {
						
						@Override
						public void run() 
						{
					    	((LinearLayout)findViewById(R.id.list_index)).setVisibility(View.VISIBLE);
						}
					});
	                synchronized(this)
	                {
	                    result.count = COUNTRIES.size();
	                    result.values = COUNTRIES;
	                }
	            	
	            }
	            return result;
			}

			@Override
			protected void publishResults(CharSequence constraint,FilterResults results) 
			{
					@SuppressWarnings("unchecked")
					ArrayList<String> filtered = (ArrayList<String>)results.values;
					arrayAdapter=  new StandardArrayAdapter(filtered);
					sectionAdapter = new SectionListAdapter(getLayoutInflater(),arrayAdapter);
			        listView.setAdapter(sectionAdapter);
				  
			}
			
		}
	    
	  
	    private void displayListItem(float sideIndexY)
	    {
	        // compute number of pixels for every side index item
	        double pixelPerIndexItem = (double) sideIndexHeight / sideIndexSize;

	        // compute the item index for given event position belongs to
	        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

	        if(itemPosition<sideIndexList.size())
	        {
	     	   // get the item (we can do it since we know item index)
	            Object[] indexItem = sideIndexList.get(itemPosition);
	            listView.setSelectionFromTop((Integer)indexItem[1], 0);
	        }
	    }
	   
	    @SuppressLint("DefaultLocale")
		private void PoplulateSideview()
	    {
	    		
	    		String latter_temp,latter="";
	    		int index=0;
	    		sideIndex.removeAllViews();
	    		sideIndexList.clear();
	    		for(int i=0;i<COUNTRIES.size();i++)	    		{
	    			Object[] temp=new Object[2];
	    			latter_temp=(COUNTRIES.get(i)).substring(0, 1).toUpperCase();
	    			if(!latter_temp.equals(latter))
	    			{
	    				// latter with its array index
	    				latter=latter_temp;
	    				temp[0]=latter;
	    				temp[1]=i+index;
	    				index++;
	    				sideIndexList.add(temp);
	    				
	    				TextView latter_txt=new TextView(this);
	    				latter_txt.setText(latter);
	    				
	    				latter_txt.setSingleLine(true);
	    				latter_txt.setHorizontallyScrolling(false);
	    				latter_txt.setTypeface(null, Typeface.BOLD);
	    				latter_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP,getResources().getDimension(R.dimen.index_list_font));
	    				LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1);
	    				params.gravity=Gravity.CENTER_HORIZONTAL;    
	    				
	    				latter_txt.setLayoutParams(params);
	    				latter_txt.setPadding(10, 0,10, 0);
	    				
	    				
	    				sideIndex.addView(latter_txt);
	    			}
	    		}
	    		
	    		sideIndexSize=sideIndexList.size();
	    		
	    }

		private TextWatcher filterTextWatcher = new TextWatcher() 
		   {
				
		    public void afterTextChanged(Editable s)
		    {
		    	new StandardArrayAdapter(COUNTRIES).getFilter().filter(s.toString());
		    }

		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    }

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		        // your search logic here
		    }

		}; 
   
	
}

