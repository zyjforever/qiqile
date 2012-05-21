package com.zyj.qiqile.activity.meprofile;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicWriteActivity;
import com.zyj.qiqile.constant.Constants;
import com.zyj.qiqile.domain.bo.UserBO;

public class MeProfileEditSignatureActivity extends BasicWriteActivity{

	private EditText sinatureEditView;
	
	private String signature;
	
	
	@Override
	protected void initView() {
		sinatureEditView=(EditText) findViewById(R.id.user_signature);
		super.initView();
	}

	@Override
	protected void setContext() {
		setContext(MeProfileEditSignatureActivity.this);
	}

	@Override
	protected Boolean validate() {
		Boolean result=Boolean.FALSE;
		String string=sinatureEditView.getText().toString();
		if(string!=null&&string.length()>0){
			if(string.length()>Constants.SIGNATURE_MAXSIZE){
				Toast.makeText(context, R.string.error_user_signature_toolong,
						Toast.LENGTH_LONG);
			}
			else{
				result=Boolean.TRUE;
				signature=string;
			}
		}
		return result;
	}

	@Override
	protected void beforeFinish() {
		Intent intent=new Intent(MeProfileEditSignatureActivity.this,MeProfileEditActivity.class);
		intent.putExtra(UserBO.SIGNATURE, signature);
		super.hiddenSoftInput(sinatureEditView);
		setResult(RESULT_OK,intent);
		finish();
	}

	@Override
	protected void setContentView() {
		setTitleString(getString(R.string.input_signature_header));
		setContentView(R.layout.me_profile_edit_signature);
	}

	@Override
	protected void beforeBack() {
		setResult(RESULT_CANCELED,null);
		super.hiddenSoftInput(sinatureEditView);
	}
	
}
