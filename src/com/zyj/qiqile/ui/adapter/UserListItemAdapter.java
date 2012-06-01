package com.zyj.qiqile.ui.adapter;

import java.util.List;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.module.item.UserListItem;
import com.zyj.qiqile.domain.vo.UserVO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserListItemAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<UserVO> userList;
	private int layout;

	public UserListItemAdapter(LayoutInflater mInflater, List<UserVO> userList) {
		this.mInflater = mInflater;
		this.userList = userList;
		this.layout = R.layout.friend_item;
	}

	@Override
	public int getCount() {
		int size = 0;
		if (userList != null) {
			size = userList.size();
		}
		return size;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return Long.valueOf(userList.get(position).getUserId());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UserListItem holder = null;
		// if (convertView == null) {
		holder = new UserListItem();
		convertView = mInflater.inflate(layout, null);
		holder.setUserNickText((TextView) convertView
				.findViewById(R.id.user_nick));
		holder.setUserImageView((ImageView) convertView
				.findViewById(R.id.user_image));
		holder.setUserAgeText((TextView) convertView
				.findViewById(R.id.user_age));
		holder.setUserSexText((TextView) convertView
				.findViewById(R.id.user_sex));
		holder.setUserSignatureText((TextView) convertView
				.findViewById(R.id.user_signature));
		holder.initView(this.userList.get(position));
		// convertView.setTag(holder);
		// }
		// holder = (UserListItem) convertView.getTag();
		holder.initView(userList.get(position));
		return convertView;
	}

}
