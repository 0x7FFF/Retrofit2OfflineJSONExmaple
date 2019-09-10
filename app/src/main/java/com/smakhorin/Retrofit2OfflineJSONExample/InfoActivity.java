package com.smakhorin.Retrofit2OfflineJSONExample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.smakhorin.Retrofit2OfflineJSONExample.REST.DTO.UserData;

public class InfoActivity extends AppCompatActivity {

    private UserData mUser;
    TextView mUsername;
    TextView mPhone;
    TextView mAddress;
    TextView mWebsite;
    TextView mCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if(extra != null) {
            mUser = extra.getParcelable("user");
        }
        mUsername = (TextView)findViewById(R.id.tv_username);
        mPhone = (TextView)findViewById(R.id.tv_phone);
        mAddress = (TextView)findViewById(R.id.tv_address);
        mWebsite = (TextView)findViewById(R.id.tv_website);
        mCompany = (TextView)findViewById(R.id.tv_company);

        mUsername.setText(mUser.getUsername());
        mPhone.setText(mUser.getPhone());
        String address = mUser.getAddress().getSuite();
        address += ", " +  mUser.getAddress().getStreet();
        address += System.getProperty("line.separator");
        address += mUser.getAddress().getCity();
        address += ", " + mUser.getAddress().getZipcode();
        mAddress.setText(address);
        mWebsite.setText(mUser.getWebsite());
        String company = mUser.getCompany().getName();
        company += System.getProperty("line.separator");
        company += mUser.getCompany().getCatchPhrase();
        company += System.getProperty("line.separator");
        company += mUser.getCompany().getBs();
        mCompany.setText(company);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mUser.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
