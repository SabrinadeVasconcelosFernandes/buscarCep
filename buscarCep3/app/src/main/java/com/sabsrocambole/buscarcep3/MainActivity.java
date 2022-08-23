package com.sabsrocambole.buscarcep3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.infoCep);
    }

    public void buscarCep(View view){
        //criando um objeto do tipo tarefa
        //usando a sua função execute e
        //dentro dele vou colocar o caminho da API
        Tarefa tarefa = new Tarefa();
        tarefa.execute("https://http://viacep.com.br/ws/01001000/json");
    }

    //parametros do AsyncTask (3):
// -1) tipo de informação que vai ser passada para os parametros do nosso método
//
// -2) progresso da requisição, podemos usar para saber quantos por cento dessa requisição foi feita
//
// -3) retorno da função -> retorno da execução do consumo de webService
//

    private class Tarefa extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            String retorno = Conexao.getDados(strings[0]);
            return retorno;
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }
    }
}