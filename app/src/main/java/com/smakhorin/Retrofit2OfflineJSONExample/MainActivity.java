package com.smakhorin.Retrofit2OfflineJSONExample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smakhorin.Retrofit2OfflineJSONExample.REST.ApiClient;
import com.smakhorin.Retrofit2OfflineJSONExample.REST.ApiInterface;
import com.smakhorin.Retrofit2OfflineJSONExample.REST.DTO.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements UsersAdapter.ListItemClickListener {

    RecyclerView mUsers;
    UsersAdapter usersAdapter;
    LinearLayout mLoadingLayout;
    Button mRefreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsers = (RecyclerView)findViewById(R.id.rv_users);
        mLoadingLayout = (LinearLayout)findViewById(R.id.ll_loading);
        mRefreshButton = (Button)findViewById(R.id.btn_refresh);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Contacts");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mUsers.setLayoutManager(layoutManager);
        mUsers.setHasFixedSize(true);
        mUsers.addItemDecoration(new DividerItemDecoration(mUsers.getContext(), DividerItemDecoration.VERTICAL));
        usersAdapter = new UsersAdapter(null,this);
        getResponse();
    }

    private void getResponse() {
        ApiClient client = new ApiClient(this);
        ApiInterface api = client.getClient().create(ApiInterface.class);
        Call<List<UserData>> call = api.getAllUsers();

        call.enqueue(new Callback<List<UserData>>() {
            @Override
            public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                Log.d("onResponse:", response.body().toString());
                usersAdapter.setUserData(response.body());
                mUsers.setAdapter(usersAdapter);
                mLoadingLayout.setVisibility(View.INVISIBLE);
                mUsers.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<UserData>> call, Throwable t) {
                //t.printStackTrace();
                Toast.makeText(MainActivity.this,"No connection available and no cache is present",Toast.LENGTH_SHORT).show();
                mLoadingLayout.setVisibility(View.INVISIBLE);
                mRefreshButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent i = new Intent(MainActivity.this,InfoActivity.class);
        i.putExtra("user",usersAdapter.getUser(clickedItemIndex));
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sortAZ:
                sortAZ();
                return true;
            case R.id.sortZA:
                sortZA();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sortAZ() {
        usersAdapter.sortUsers(false);
    }

    public void sortZA() {
        usersAdapter.sortUsers(true);
    }

    //In case the first launch was in offline mode
    public void tryRefresh(View view) {
        mRefreshButton.setVisibility(View.INVISIBLE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        getResponse();
    }
}
