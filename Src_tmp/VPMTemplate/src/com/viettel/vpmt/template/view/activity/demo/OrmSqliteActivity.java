package com.viettel.vpmt.template.view.activity.demo;

import android.os.Bundle;

import com.viettel.invoice.one.R;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.vpmt.template.base.BaseActivity;
import com.viettel.vpmt.template.orm.sqlite.DatabaseManager;
import com.viettel.vpmt.template.presenter.UserPresenter;
import com.viettel.vpmt.template.view.fragment.demo.OrmSqliteFragment;

public class OrmSqliteActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_orm_sqlite_activity);
		setConfigLayoutActivity(true, true, R.drawable.background_color);
		setTitleActionBar("ORM SQLite Demo");

		disEnabledSlidingActionBar();
		gotoOrmSqliteFragment();

	}

	/**
	 * Mo ta chuc nang cua ham
	 * 
	 * @author: duchha
	 * @param:
	 * @return: void
	 * @Since: Mar 5, 2014
	 * @throws:
	 */
	private void gotoOrmSqliteFragment() {
		SenderAction objectSender;
		objectSender = new SenderAction();
		objectSender.sender = this;
		objectSender.bundle = new Bundle();
		objectSender.startScreen = OrmSqliteFragment.getInstance();
		UserPresenter.getInstance().onHandleSwitchFragment(objectSender,
				R.id.displayFragment, false);
	}

	/**
	 * noi dung sua
	 * 
	 * @author:thuantp
	 * @since:Feb 19, 2014
	 */
	@Override
	protected void onDestroy() {
		DatabaseManager.getInstance().releaseHelper();
		super.onDestroy();
	}

}
