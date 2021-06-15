package com.veiculos.utils;

import com.veiculos.exceptions.ResourcesNotFoundException;

import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class FIPEHttpRequest {
    public static String getRespostaApi(HttpURLConnection conexao) throws IOException {

        Reader streamReader = null;

        int statusCode = conexao.getResponseCode();

        if (statusCode > 299) {
            System.out.println(conexao.getResponseMessage());
            throw new ResourcesNotFoundException("Não foi possível obter resultado da requisição Http: "
                    + statusCode + " "
                    + conexao.getResponseMessage());
        } else {
            streamReader = new InputStreamReader((conexao.getInputStream()));
        }

        BufferedReader in = new BufferedReader(streamReader);

        String inputLine;
        StringBuilder resposta = new StringBuilder();

        while((inputLine = in.readLine()) != null) {
            resposta.append(inputLine);
        }

        in.close();

        return resposta.toString();
    }
}
