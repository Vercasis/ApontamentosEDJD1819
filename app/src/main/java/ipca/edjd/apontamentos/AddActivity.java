package ipca.edjd.apontamentos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import ipca.edjd.apontamentos.models.Apontamento;

public class AddActivity extends AppCompatActivity {

    private static final int CAMERA_PIC_REQUEST = 1001;
    EditText editTextTitle;
    EditText editTextDiscription;
    ImageView imageViewPhoto;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDiscription = findViewById(R.id.editTextDiscription);
        imageViewPhoto = findViewById(R.id.imagePhoto);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(currentUser.getUid()).child("Apontamentos");
/*
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(MainActivity.TAG, "Value is: " + value);
                Toast.makeText(AddActivity.this, value,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(MainActivity.TAG, "Failed to read value.", error.toException());
            }
        });*/

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

            if (bm!=null){
                String path =Utils.saveBitmap(bm);
                if (path !=null) {
                    apontamento.setUriPhoto(path);
                }
            }

            apontamento.setTitulo(editTextTitle.getText().toString());
            apontamento.setDescricao(editTextDiscription.getText().toString());


            //Apontamento.add(apontamento, Realm.getDefaultInstance());

            // Write a message to the database


            myRef.child(apontamento.getId()).setValue(apontamento);

            finish();

            return true;
        }
        if (id == R.id.action_photo) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_PIC_REQUEST);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    Bitmap bm;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode  == RESULT_OK){
            if (requestCode == CAMERA_PIC_REQUEST){
                bm = (Bitmap) data.getExtras().get("data");
                imageViewPhoto.setImageBitmap(bm);
            }
        }

    }
}
