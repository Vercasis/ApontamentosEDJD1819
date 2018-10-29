package ipca.edjd.apontamentos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

import io.realm.Realm;
import ipca.edjd.apontamentos.models.Apontamento;

public class AddActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextDiscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDiscription = findViewById(R.id.editTextDiscription);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {

            Apontamento apontamento = new Apontamento();
            apontamento.setDate(new Date());
            apontamento.setTitulo(editTextTitle.getText().toString());
            apontamento.setDescricao(editTextDiscription.getText().toString());

            Apontamento.add(apontamento, Realm.getDefaultInstance());

            return true;
        }
        if (id == R.id.action_photo) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
