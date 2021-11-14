package com.example.clientlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.clientlist.database.AppDataBase;
import com.example.clientlist.database.AppExecutor;
import com.example.clientlist.database.Client;
import com.example.clientlist.database.DataAdapter;
import com.example.clientlist.settings.SettingsActivity;
import com.example.clientlist.utils.Constans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppDataBase myDB;
    private DataAdapter adapter;
    private List<Client> listClient;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private DataAdapter.AdapterOnItemClicked adapterOnItemClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        NavigationView nav_view = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        adapterOnItemClicked = new DataAdapter.AdapterOnItemClicked() {
            @Override
            public void onAdapterItemClicked(int position) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(Constans.NAME_KEY, listClient.get(position).getName());
                intent.putExtra(Constans.SEC_NAME_KEY, listClient.get(position).getSec_name());
                intent.putExtra(Constans.TEL_KEY, listClient.get(position).getNumber());
                intent.putExtra(Constans.DESC_KEY, listClient.get(position).getDiscription());
                intent.putExtra(Constans.IMPORTANCE_KEY, listClient.get(position).getImportance());
                intent.putExtra(Constans.SP_KEY, listClient.get(position).getSpecial());
                intent.putExtra(Constans.ID_KEY, listClient.get(position).getId());
                startActivity(intent);
            }
        };
        nav_view.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                startActivity(i);
            }
        });

    }


    private void init() {
        recyclerView = findViewById(R.id.rView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDB = AppDataBase.getInstance(getApplicationContext());
        listClient = new ArrayList<>();
        adapter = new DataAdapter(listClient, adapterOnItemClicked,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
            @Override
            public void run() {
                listClient = myDB.clientDAO().getClientList();
                AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter != null) {
                            adapter.updateAdapter(listClient);
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_client) {
            Toast.makeText(this, "Client pull", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.id_web) {
            goTo("https://neco-dessarollo.es ");
        }
        else if (id == R.id.id_settings) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goTo(String url) {
        Intent brIntent , chooser;
        brIntent = new Intent(Intent.ACTION_VIEW);
        brIntent.setData(Uri.parse(url));
        chooser = Intent.createChooser(brIntent, "Открыть с ");
        if (brIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }

    }
}