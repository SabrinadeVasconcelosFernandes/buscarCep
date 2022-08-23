package com.sabsrocambole.buscarcep3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Conexao {

    //criando um método publico stático que retorna uma string e tem o nome de getDados.
    //ele vai retornar os dados a partir de uma string uri -- que será o link de acesso com a minha api.
    public static String getDados(String uri){

        //BufferedReader -->  Ele usa um Reader para ler dados do fluxo de entrada
        // de caracteres e cria um buffer de entrada de tamanho padrão.

        BufferedReader bufferedReader = null;
        // leva dois parâmetros:
        // 1) um leitor que é usado para ler os dados do fluxo de entrada.
        // 2) O tamanho do buffer de entrada.
        //por enquanto deixamos ele vazio (null)

        try{

            //vou criar uma variavel do tipo URL chamada url
            //ela é inicializada como new URL e recebe como parâmetro de construtor a uri
            // que eu tenho lá em cima no meu método
            URL url = new URL(uri);

            //criando uma classe de conexão:
            //que recebe no construtor um cashe de http conection com o url que eu criei agora
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //vou inicializar uma variavel string builder
            StringBuilder stringBuilder = new StringBuilder();

            //agora vou usar a bufferedReader que eu criei lá em cima:
            //e nele eu vou ter armazenados os dados da api
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String linha;

            while ((linha = bufferedReader.readLine()) != null){
                //cada volta do laço de repetição, a string linha vai receber um valor do bufferReader,
                //e isso vai acontecer enquanto esse valor for diferente de null
                //enquanto houver linhas para serem lidas, o string builder vai alocar essas linhas
                // dentro do string builder usando o append
                stringBuilder.append(linha+"\n");
            }

            //retornando o que foi encontrado --> tenho que retornar uma string pq eu escrevi
            //lá em cima em sua criação que retornaria uma string
            return stringBuilder.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            //verificando se o meu bufferReader está vazio e depois fecho ele
            //também coloco um try catch
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
