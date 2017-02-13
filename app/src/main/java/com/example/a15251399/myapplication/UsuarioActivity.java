package com.example.a15251399.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.a15251399.myapplication.model.Http;
import com.example.a15251399.myapplication.model.Usuario;
import com.google.gson.Gson;

import java.util.HashMap;

public class UsuarioActivity extends AppCompatActivity {

    Context context;
    TextView welcome;

    int user_id;
    final String mode = "idSearch";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        welcome = (TextView) findViewById(R.id.txt_boasvindas);

        Intent data = getIntent();
        user_id = data.getIntExtra("idUsuario", -1);

        new getUser().execute();
    }

    private class getUser extends AsyncTask<Void, Void, Void> {
        String url;
        Usuario usuario;

        HashMap<String, String> parametros = new HashMap<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            parametros.put("id", String.valueOf(user_id));

            url = getResources().getString(R.string.serverAddr) + "?mode=" + mode;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = Http.post(url, parametros);
            usuario = new Gson().fromJson(json, Usuario.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            String boas_vindas = "Bem vindo, " + usuario.getNome();
            welcome.setText(boas_vindas);
        }
    }

}
