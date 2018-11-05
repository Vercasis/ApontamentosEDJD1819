package ipca.edjd.apontamentos;

import android.content.Intent;
import android.os.BaseBundle;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

}
