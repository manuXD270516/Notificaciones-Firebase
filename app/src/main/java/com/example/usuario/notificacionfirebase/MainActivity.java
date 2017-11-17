package com.example.usuario.notificacionfirebase;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.usuario.notificacionfirebase.WEBSERVICES.Constantes;
import com.example.usuario.notificacionfirebase.WEBSERVICES.VolleySingleton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final int PICK_IMAGE=100;
    public Uri imguri;
    private EditText etTitulo;
    private ImageView imageView;
    private TextView tv;
    DatabaseReference dbCategoria;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

        imageView=(ImageView)findViewById(R.id.img);
        etTitulo=(EditText)findViewById(R.id.titulo);
        tv=(TextView)findViewById(R.id.ti);
        dbCategoria = FirebaseDatabase.getInstance().getReference("prueba");
        storageReference = FirebaseStorage.getInstance().getReference("prueba");
    }

    public void elegirImagenCategoria(View v) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //gallery.setType("image/*");
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imguri = data.getData();
            imageView.setImageURI(imguri);
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void nada(View view){
        /*StorageReference ref = storageReference.child(valor + "." + getImageExt(imguri));
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Toast.makeText(MainActivity.this,uri.toString().trim(),Toast.LENGTH_LONG).show();
                //obj.put("urlimagen",uri.toString());
                //notificarUsers(obj.get("titulo"),"xxxxxx",obj.get("urlimagen");
            }
        });*/
    }

    long valor;
    Map<String,String> obj=new HashMap<>();
    @SuppressWarnings("VisibleForTests")
    public void subirImagen(View v) {
        if (imguri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Cargando imagen");
            dialog.show();
            valor= System.currentTimeMillis();
            StorageReference ref = storageReference.child(valor + "." + getImageExt(imguri));
            ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    obj.put("titulo",etTitulo.getText().toString().trim());
                    obj.put("urlimagen",taskSnapshot.getDownloadUrl().toString());

                    Toast.makeText(getApplicationContext(), obj.get("urlimagen"), Toast.LENGTH_SHORT).show();
                    //etTitulo.setText(taskSnapshot.getDownloadUrl().getPath().toString());
                    //final CategoriaModel categorias = new CategoriaModel(et_titulo.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString().trim(), et_descripcion.getText().toString().trim(), 0);
                    String uploadId = etTitulo.getText().toString().trim();
                    dbCategoria.child(uploadId).setValue(obj);
                    notificarUsers(obj.get("titulo"),"xxxxxx",obj.get("urlimagen"));

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Cargando....." + (int) progress + "%");
                        }
                    });


            /*ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                }
            })*/
        } else {
            Toast.makeText(getApplicationContext(), "Elegir una Imagen por favor!", Toast.LENGTH_SHORT).show();
        }
    }

    private void notificarUsers(String titulo,String desc,String urlImg) {
        String p1=urlImg.substring(0,urlImg.indexOf('&'));
        String p2=urlImg.substring(urlImg.indexOf('&')+1);
        VolleySingleton.getInstance(this).
                addToRequestQueue(
                        new JsonObjectRequest(Request.Method.GET,
                                Constantes.URL_PETICION_NOTIFICACION +"?titulo="+titulo+"&mensaje="+desc+"&url="+p1+"&resto="+p2,
                                new JSONObject(),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarPeticion(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
                                        Log.d("TAG", "Error Volley: " + error.getMessage());
                                    }
                                }
                        ));
    }

    private void procesarPeticion(JSONObject response) {
        // ninguna accion por la peticion
        int valor=331;

    }
}
