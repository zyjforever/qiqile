package com.zyj.qiqile.activity.more;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicWriteActivity;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;

public class ModifyPasswordActivity extends BasicWriteActivity {

	private EditText currentPasswordEdit;
	private EditText newPasswordEdit;
	private EditText newPasswordAgainEdit;
	private TextView currentAccountText;

	private String currentPassword;
	private String newPassword;
	private String newPasswordAgain;
	
	@Override
	protected void initView() {
		super.initView();
		currentPasswordEdit=(EditText) findViewById(R.id.current_password);
		newPasswordEdit=(EditText) findViewById(R.id.new_password);
		newPasswordAgainEdit=(EditText) findViewById(R.id.new_password_again);
		currentAccountText=(TextView) findViewById(R.id.current_account);
		setTitleString(getString(R.string.omenu_modify_password));
		//TODO写上邮箱名
	}

	@Override
	protected void setContext() {
		setContext(ModifyPasswordActivity.this);
		QiqileApplication.getInstance().context = this;
		ActivityManagerApplication.getInstance().addActvity(this);
	}

	@Override
	protected Boolean validate() {
		Boolean result = Boolean.FALSE;
		currentPassword = currentPasswordEdit.getText().toString();
		newPassword = newPasswordEdit.getText().toString();
		newPasswordAgain = newPasswordAgainEdit.getText().toString();
		if (currentPassword != null && !currentPassword.equals("")) {
			if (newPassword != null && !newPassword.equals("")) {
				if (newPasswordAgain != null && !newPasswordAgain.equals("")) {
					if (newPasswordAgain.equals(newPassword)) {
						result = Boolean.TRUE;
					} else {
						Toast.makeText(this, R.string.password_different,
								Toast.LENGTH_SHORT);
					}
				} else {
					Toast.makeText(this, R.string.new_password_again,
							Toast.LENGTH_SHORT);
				}
			} else {
				Toast.makeText(this, R.string.new_password, Toast.LENGTH_SHORT);
			}
		} else {
			Toast.makeText(this, R.string.current_password, Toast.LENGTH_SHORT);
		}

		return result;
	}

	@Override
	protected void beforeBack() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void beforeFinish() {
		// TODO提交到服务器
		onBackPressed();
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.modify_password);
	}

}
