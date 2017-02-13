package com.example.a15251399.myapplication;

import android.content.Context;
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

import java.util.HashMap;

public class CadastroActivity extends AppCompatActivity {

    Context context;
    EditText edt_nome, edt_email, edt_senha;
    Button cadastrar;

    final String mode = "register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        edt_nome = (EditText) findViewById(R.id.edt_nome);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        cadastrar = (Button) findViewById(R.id.btn_cadastrar);
    }

    public void registerUser(View view) {
        new registerUser().execute();
    }

    private class registerUser extends AsyncTask<Void, Void, Void> {
        String url;
        String resultado;
        HashMap<String, String> url_params = new HashMap<>();

        @Override
        protected void onPreExecute() {
            String nome = edt_nome.getText().toString().trim();
            String email = edt_email.getText().toString().trim();
            String senha = edt_senha.getText().toString().trim();

            url_params.put("name", nome);
            url_params.put("email", email);
            url_params.put("pass", senha);

            url = getResources().getString(R.string.serverAddr) + "?mode=" + mode;
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            resultado = Http.post(url, url_params);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("RESULTADO", resultado);
            if( resultado.equals("0") ) {
                Toast.makeText(context, "Usuário cadastrado", Toast.LENGTH_SHORT).show();
            } else if( resultado.equals("1") ) {
                Toast.makeText(context, "Houve um erro na inserção", Toast.LENGTH_SHORT).show();
            } else if( resultado.equals("2") ) {
                Toast.makeText(context, "Email já cadastrado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
