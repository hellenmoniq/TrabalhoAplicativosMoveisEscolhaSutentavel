package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.model.Questao;

import java.util.ArrayList;
import java.util.List;


public class QResponde extends AppCompatActivity {


    private TextView pergunta;
    private RadioButton rbResposta1;
    private RadioButton rbResposta2;
    private RadioButton rbResposta3;
    private RadioButton rbResposta4;
    // private RadioButton rgRespostas;
    private RadioButton rb;
    int respostaCerta = R.id.rbResposta1;
    int pontos = 0;




    //Lista de questao do quiz
    List<Questao> questoes = new ArrayList<Questao>() {
        {
            add(new Questao("Qual dos gases não causa o efeito estufa?", R.id.rbResposta1, "Oxigênio", "Metano", "Gás Carbônico", "Gás Helio"));
            add(new Questao("Qual dos elementos não é uma fonte de energia?", R.id.rbResposta2, "Agua Corrente", "Barra de ferro", "Sol", "Petróleo"));
            add(new Questao("O que é reciclagem?", R.id.rbResposta3, "Jogar lixo na rua", "Nome dado ao descarte de lixo", "Processo de transformação de materias", "jogar lixo na praia"));
            add(new Questao("Qual alternativa apresenta uma vantagem da energia solar?", R.id.rbResposta4, "Eficaz em qualquer clima", "Não é renovavel", "Disponibilidade", "Não polui"));


        }

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qresponde);

        IniciarViews();
        carregarQuestao();
    }


    public void IniciarViews() {

        pergunta = (TextView) findViewById(R.id.pergunta);
        rbResposta1 = (RadioButton) findViewById(R.id.rbResposta1);
        rbResposta2 = (RadioButton) findViewById(R.id.rbResposta2);
        rbResposta3 = (RadioButton) findViewById(R.id.rbResposta3);
        rbResposta4 = (RadioButton) findViewById(R.id.rbResposta4);


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        carregarQuestao();
    }


    private void carregarQuestao() {

        if (questoes.size() > 0) {

            Questao q = questoes.remove(0);
            pergunta.setText(q.getPergunta());
            List<String> resposta = q.getRespostas();
            rbResposta1.setText(resposta.get(0));
            rbResposta2.setText(resposta.get(1));
            rbResposta3.setText(resposta.get(2));
            rbResposta4.setText(resposta.get(3));
            respostaCerta = q.getRespostaCerta();
        } else {

            Intent intent = new Intent(this, RespotaActivity.class);
            intent.putExtra("pontos", pontos);
            startActivity(intent);
            finish();
        }


    }


    public void btnResponderOnClick(View v) {
        RadioGroup rgRespostas = (RadioGroup) findViewById(R.id.rgRespostas);
        RadioButton rb = (RadioButton) findViewById(rgRespostas.getCheckedRadioButtonId());

        Intent intent = new Intent(this, RespotaActivity.class);
        if (rgRespostas.getCheckedRadioButtonId() == respostaCerta) {
            intent.putExtra("acertou", true);
            pontos++;
        } else intent.putExtra("acertou", false);
        intent.putExtra("pontos", pontos);
        startActivity(intent);
        rb.setChecked(false);


    }



}

