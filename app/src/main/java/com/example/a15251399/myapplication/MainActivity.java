package com.example.a15251399.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a15251399.myapplication.model.Http;
import com.example.a15251399.myapplication.model.Usuario;
import com.google.gson.Gson;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Context context;
    EditText edt_email, edt_senha;
    Button login, cadastro;

    final String mode = "login";
    int idUsuario_ativo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        login = (Button) findViewById(R.id.btn_login);
        cadastro = (Button) findViewById(R.id.btn_cadastrar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getJson().execute();
            }
        });

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regWin = new Intent(context, CadastroActivity.class);
                startActivity(regWin);
            }
        });
    }

    private class getJson extends AsyncTask<Void, Void, Void> {
        String url;
        HashMap<String, String> parametros = new HashMap<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            String email = edt_email.getText().toString().trim();
            String senha = edt_senha.getText().toString().trim();

            parametros.put("email", email);
            parametros.put("pass", senha);

            url = getResources().getString(R.string.serverAddr) + "?mode=" + mode;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = Http.post(url, parametros);

            Log.d("JSON", json);
            idUsuario_ativo = (new Gson().fromJson(json, Usuario.class)).getId();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if( idUsuario_ativo != -1 ) {
                Intent userWin = new Intent(context, UsuarioActivity.class);
                userWin.putExtra("idUsuario", idUsuario_ativo);
                startActivity(userWin);
            } else {
                Toast.makeText(context, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
