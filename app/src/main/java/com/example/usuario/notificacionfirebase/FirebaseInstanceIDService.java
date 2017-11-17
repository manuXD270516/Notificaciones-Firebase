package com.example.usuario.notificacionfirebase;

import android.app.Service;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import okhttp3.Request;
import okhttp3.RequestBody;
/**
 * Created by USUARIO on 09/11/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        //super.onTokenRefresh();
        String token=FirebaseInstanceId.getInstance().getToken();
        registrarToken(token);
    }

    private void registrarToken(String token) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.43.168:8080/Notificacion_Firebase/register.php") //http://192.168.1.71/fcm/register.php  url origen
                .post(body)
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
