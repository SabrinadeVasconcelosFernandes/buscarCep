package com.sabsrocambole.buscarcep2.service;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.sabsrocambole.buscarcep2.model.CEP;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

//parametros do AsyncTask (3):
// -1) tipo de informação que vai ser passada para os parametros do nosso método
//      (n vamos passar nada pq o CEP será passado via construtor) --> Void
// -2) progresso da requisição, podemos usar para saber quantos por cento dessa requisição foi feita
//      (tbm não vamos usar)
// -3) retorno da função -> retorno da execução do consumo de webService
//      (vamos retornar uma classe chamada CEP que ainda não existe -- vamos criar)

public class HTTPService  extends AsyncTask<Void, Void, CEP> {

    private final String cep;
    public HTTPService(String cep) {
        this.cep = cep;
    }


    @Override
    protected CEP doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        //vai abrigar o que o scanner ler da stream url que tem a resposta do sistema -- pega a linha atual e
        // add no stringBuilder

        try {
            //criando uma nova url usando o método de concatenação para que o cep não seja fixo:
            URL url = new URL("http://ws.matheuscastiglioni.com.br/ws/cep/find/"+this.cep+"/json/");

            //criando uma conexão http para fazer algumas configurações:
            // - qual vai ser o retorno do web service?
            // - qual método vamos ultilizar para fazer o seu consumo?
            //para isso vamos ultilizara classe HttpURLConnection:
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            //método ultilizado para o nosso http request --> usamos o GET por que através dele podemos puxar
            // informações de um serviço
            connection.setRequestMethod("GET");

            //tipo de retorno que o web service vai retornar -> queremos que ele devolva um json
            connection.setRequestProperty("Accept", "application/json");


            //tempo de conexão que a nossa aplicação tem que fazer com o web service (5s):
            connection.setConnectTimeout(5000);

            //faz a conexão:
            connection.connect();


           Scanner scanner = new Scanner(url.openStream());
            //url.openStream(); --> pega a resposta que o sistema devoleveu e tranforma ela em uma stream.
            //podemos fazer a leitura desse stream através da classe scanner --> new Scanner(url.openStream());

            //enquanto o scanner tiver linhas para serem lidas nós vamos pegar essas linhas e add na nossa resposta --> StringBuilder resposta = new StringBuilder();
            while (scanner.hasNext()){
                resposta.append(scanner.next());
                //com StringBuilder é invocado o método append. Esse método aloca novas strings
                // concatenadas para o mesmo objeto
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //aqui no retorno eu quero fazer a conversão dessa stream para um objeto java:
        //podemos fazer isso de várias maneiras, mas vamos fazer usando a biblioteca Gson do Google.
        //não esquece de importar a biblioteca la no gradle do modulo. (implementation 'com.google.code.gson:gson:2.8.5')
        return new Gson().fromJson(resposta.toString(), CEP.class);
    }
}
