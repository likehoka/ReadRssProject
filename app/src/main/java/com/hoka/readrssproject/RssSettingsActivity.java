package com.hoka.readrssproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.hoka.readrssproject.view.IRssSettingsActivity;
import com.hoka.readrssproject.database.DatabaseHelper;
import com.hoka.readrssproject.model.SaveUrlItem;
import com.hoka.readrssproject.presenter.RssSettingsPresenter;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

    /*
    This activity allows you to define and address verification
    flow rss availability if the address is available that opens
    MainActivity
     */

public class RssSettingsActivity extends MvpAppCompatActivity implements IRssSettingsActivity {
    @InjectPresenter
    RssSettingsPresenter rssSettingsPresenter;

    private DatabaseHelper mDatabaseHelper = null;

    @BindView(R.id.edit_text_activity_rss_settings_url_name)
    EditText editTextUrlName;
    @BindView(R.id.button_activity_rss_settings_open_url)
    Button buttonOpenUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_settings);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_activity_rss_settings_open_url)
    public void onClick() {
        rssSettingsPresenter.showCheckRss(editTextUrlName.getText().toString());
    }

    @Override
    public void showMainActivity() {
        mDatabaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        try {
            DeleteBuilder<SaveUrlItem, Integer> deleteBuilder = mDatabaseHelper.getSaveUrlItemDao().deleteBuilder();
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SaveUrlItem saveUrlItem = new SaveUrlItem();
        saveUrlItem.setUrl(editTextUrlName.getText().toString());
        try {
            Dao<SaveUrlItem, Integer> mSaveUrlItemDao = mDatabaseHelper.getSaveUrlItemDao();
            mSaveUrlItemDao.create(saveUrlItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RssSettingsActivity.this.finish();
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(this, R.string.error_not_rss_address, Toast.LENGTH_SHORT).show();

    }
}
