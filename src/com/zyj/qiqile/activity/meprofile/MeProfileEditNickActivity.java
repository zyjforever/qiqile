package com.zyj.qiqile.activity.meprofile;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicWriteActivity;
import com.zyj.qiqile.constant.Constants;
import com.zyj.qiqile.domain.bo.UserBO;

public class MeProfileEditNickActivity extends BasicWriteActivity {

	private EditText nickEditView;

	private String nick;

	@Override
	protected void initView() {
		nickEditView = (EditText) findViewById(R.id.user_nick);
		super.initView();
	}

	@Override
	protected void setContext() {
		setContext(MeProfileEditNickActivity.this);
	}

	@Override
	protected Boolean validate() {
		Boolean result = Boolean.FALSE;
		String string = nickEditView.getText().toString();
		if (string != null && string.length() > 0) {
			if (string.length() > Constants.NICK_MAXSIZE
					|| string.length() < Constants.NICK_MINSIZE) {
				Toast.makeText(context, R.string.error_user_nick_toolong,
						Toast.LENGTH_LONG);
			} else {
				result = Boolean.TRUE;
				nick = string;
			}
		} else {
			Toast.makeText(context, R.string.input_nick, Toast.LENGTH_SHORT);
		}
		return result;
	}

	@Override
	protected void beforeFinish() {
		Intent intent = new Intent(MeProfileEditNickActivity.this,
				MeProfileEditActivity.class);
		intent.putExtra(UserBO.NICK, nick);
		super.hiddenSoftInput(nickEditView);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	protected void setContentView() {
		setTitleString(getString(R.string.input_nick_header));
		setContentView(R.layout.me_profile_edit_nick);
	}

	@Override
	protected void beforeBack() {
		setResult(RESULT_CANCELED, null);
		super.hiddenSoftInput(nickEditView);
	}

}
