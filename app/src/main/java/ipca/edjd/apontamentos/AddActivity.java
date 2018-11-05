package ipca.edjd.apontamentos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Date;

import io.realm.Realm;
import ipca.edjd.apontamentos.models.Apontamento;

public class AddActivity extends AppCompatActivity {

    private static final int CAMERA_PIC_REQUEST = 1001;
    EditText editTextTitle;
    EditText editTextDiscription;
    ImageView imageViewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDiscription = findViewById(R.id.editTextDiscription);
        imageViewPhoto = findViewById(R.id.imagePhoto);

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
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_PIC_REQUEST);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode  == RESULT_OK){
            if (requestCode == CAMERA_PIC_REQUEST){
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                imageViewPhoto.setImageBitmap(bm);
            }
        }

    }
}
