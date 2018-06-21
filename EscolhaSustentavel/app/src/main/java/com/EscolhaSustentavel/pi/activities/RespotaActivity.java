package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.EscolhaSustentavel.pi.R;


public class RespotaActivity extends AppCompatActivity {

    int total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respota);


        TextView resposta = (TextView) findViewById(R.id.resposta);

        Intent intent = getIntent();
        int pontos = intent.getIntExtra("pontos", 0);
        boolean acertou = intent.getBooleanExtra("acertou", false);
        if (acertou) {

            resposta.setText("Acertou! Pontos: " + pontos);


        } else if (!acertou) {

            resposta.setText("Errou! Pontos: " + pontos);

        }


        //deixando a respota com um tempo temporario e voltando para o quiz

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        thread.start();
    }


}
