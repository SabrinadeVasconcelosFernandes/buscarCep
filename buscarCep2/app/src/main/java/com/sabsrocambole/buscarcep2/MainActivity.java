package com.sabsrocambole.buscarcep2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sabsrocambole.buscarcep2.model.CEP;
import com.sabsrocambole.buscarcep2.service.HTTPService;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buscarCep(View view){

        final EditText etCep = findViewById(R.id.etMain_cep);
        final TextView tvResposta = findViewById(R.id.tvMain_resposta);

        //mensagem buscando cep
        Context context = getApplicationContext();
        CharSequence text = "Buscando CEP...";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        //FIM mensagem buscando cep

        if (etCep.getText().toString().length() > 0 && !etCep.getText().toString().equals("") && etCep.getText().toString().length() == 8){

            //CEP válido
            Context context1 = getApplicationContext();
            CharSequence text1 = "CEP válido";
            int duration1 = Toast.LENGTH_LONG;

            Toast toast1 = Toast.makeText(context1, text1, duration1);
            toast1.show();
            //FIM CEP válido

           HTTPService service = new HTTPService(etCep.getText().toString());
            try {
               CEP retorno = service.execute().get();
               tvResposta.setText(retorno.toString());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            //CEP inválido
            Context context2 = getApplicationContext();
            CharSequence text2 = "CEP inválido";
            int duration2 = Toast.LENGTH_LONG;

            Toast toast2 = Toast.makeText(context2, text2, duration2);
            toast2.show();
            //FIM CEP inválido
        }
    }
}

//não esquece de ir lá no android manifest autorizar o uso de internet, se não não roda.
// depois de package e antes de application:
// <uses-permission android:name="android.permission.INTERNET"/>