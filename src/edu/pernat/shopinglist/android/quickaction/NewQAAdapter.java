package edu.pernat.shopinglist.android.quickaction;


import edu.pernat.shopinglist.android.R;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.content.Context;

import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewQAAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private String[] data;
	
	public NewQAAdapter(Context context) { 
		mInflater = LayoutInflater.from(context);
	}

	public void setData(String[] data) {
		this.data = data;
	}
	
	
	public int getCount() {
		return data.length;
	}

	
	public Object getItem(int item) {
		return data[item];
	}

	
	public long getItemId(int position) {
		return position;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView	= mInflater.inflate(R.layout.list, null);
			
			holder 		= new ViewHolder();
			
			holder.mTitleText	= (TextView) convertView.findViewById(R.id.t_name);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mTitleText.setText(data[position]);
		
		return convertView;
	}

	static class ViewHolder {
		TextView mTitleText;
	}
}