package ipca.edjd.apontamentos;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.BaseBundle;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import ipca.edjd.apontamentos.models.Apontamento;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    RealmResults<Apontamento> apontamentos;
    ApontamentosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm realm = Realm.getDefaultInstance();

        apontamentos = realm.where(Apontamento.class)
                .findAll();

        for (Apontamento a : apontamentos) {
            Log.d("apontamentos_AA", a.getTitulo());
        }

        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewApontamentos);
        adapter = new ApontamentosAdapter();
        listView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                Log.i("apontamentos","External storage permission has already been granted.");
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ApontamentosAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return apontamentos.size();
        }

        @Override
        public Object getItem(int position) {
            return apontamentos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null)
                convertView = getLayoutInflater().inflate(R.layout.rowview_apontamento,null);

            TextView textViewDescription = convertView.findViewById(R.id.textViewDescription);
            TextView textViewUCName = convertView.findViewById(R.id.textViewNomeUC);
            ImageView imageView = convertView.findViewById(R.id.imageViewApontamento);

            textViewDescription.setText(apontamentos.get(position).getTitulo());
            textViewUCName.setText(apontamentos.get(position).getUc().getNome());

            return convertView;
        }
    }

    private static final int PERMISSION_REQUEST_WRITE_EXSD = 1002;

    private void requestPermission() {
        if (ActivityCompat.
                shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Esta app precisa de escrever no cartão!");
            builder.setMessage("Por favor dê permissão de escrita.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    ActivityCompat.requestPermissions(MainActivity.this,                  new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            PERMISSION_REQUEST_WRITE_EXSD);
                }
            });
            builder.show();
        } else {
            ActivityCompat.requestPermissions(this,                  new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    PERMISSION_REQUEST_WRITE_EXSD);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_WRITE_EXSD) {
            if(ActivityCompat.
                    checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "Premission Granted", Toast.LENGTH_SHORT).show();


            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Permission not Granted");
                builder.setMessage("Go to settings to change permisson");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }

                });
                builder.show();

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

}
