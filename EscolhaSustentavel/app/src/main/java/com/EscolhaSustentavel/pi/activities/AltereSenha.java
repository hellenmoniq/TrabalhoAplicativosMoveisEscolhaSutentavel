package com.EscolhaSustentavel.pi.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.helpers.ValidarEntrada;
import com.EscolhaSustentavel.pi.model.Usuario;
import com.EscolhaSustentavel.pi.sql.DatabaseHelper;

import static com.EscolhaSustentavel.pi.R.id.appCompatButtonAlterar;

public class AltereSenha extends AppCompatActivity implements View.OnClickListener{


    private final AppCompatActivity activity = AltereSenha.this;

    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutNickname;
    private TextInputLayout textInputLayoutNovaSenha;
    private TextInputLayout textInputLayoutPalavraS;

    private TextInputEditText textInputEditTextNickname;
    private TextInputEditText textInputEditTextNovaSenha;
    private TextInputEditText textInputEditTextPalavras;
    private AppCompatButton appCompatButtonAlterar;

    private ValidarEntrada validarEntrada;
    private DatabaseHelper databaseHelper;
    private Usuario usuario;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altere_senha);
         IniciarViews();
         IniciartListeners();
         IniciarObjetos();;

    }

    private void IniciarViews() {
         nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

         textInputLayoutNickname = (TextInputLayout) findViewById(R.id.textInputLayoutnickname);
         textInputLayoutNovaSenha = (TextInputLayout) findViewById(R.id.textInputLayoutNovaSenha);
         textInputLayoutPalavraS = (TextInputLayout) findViewById(R.id.textInputLayoutPalavraSeg);

         textInputEditTextNickname = (TextInputEditText) findViewById(R.id.textInputEditTextnickname);
         textInputEditTextNovaSenha = (TextInputEditText) findViewById(R.id.textInputEditTextNovaSenha);
         textInputEditTextPalavras = (TextInputEditText) findViewById(R.id.textInputEditTextPalavraseg);
         appCompatButtonAlterar = (AppCompatButton) findViewById(R.id.appCompatButtonAlterar);


    }


    private void IniciartListeners() {

        appCompatButtonAlterar.setOnClickListener(this);
    }


    private void IniciarObjetos() {
        //se ficar dando erro tirar o this
        validarEntrada = new ValidarEntrada(activity);
        databaseHelper = new DatabaseHelper(activity);
        usuario = new Usuario();

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonAlterar:
                AlterarSenha();
                break;
        }
    }




    private void AlterarSenha() {


        //verificando as entradas

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextNickname, textInputLayoutNickname, "Digite o Nickname")) {
            return;
        }

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextNovaSenha, textInputLayoutNovaSenha, "Digite a Senha")) {
            return;
        }

        //Verficando se palavra de segurança e o nickname pertece ao usuario

        if (databaseHelper.CheckPalavra(textInputEditTextNickname.getText().toString().trim()
                , textInputEditTextPalavras.getText().toString().trim())) {

            //caso a palavra de segurança e nickname pertença a senha do usuario será trocada
            databaseHelper.AlterarSenha(textInputEditTextNickname.getText().toString().trim(),textInputEditTextPalavras.getText().toString().trim(),textInputEditTextNovaSenha.getText().toString().trim());
            Snackbar.make(nestedScrollView, "A senha foi alterada com sucesso!", Snackbar.LENGTH_LONG).show();
            LimparCampos();


        } else {

            Snackbar.make(nestedScrollView, "Palavra de segurança ou usuario digitados incorretamente", Snackbar.LENGTH_LONG).show();
        }


    }





    private void LimparCampos() {
        textInputEditTextNickname.setText(null);
        textInputEditTextNovaSenha.setText(null);
        textInputEditTextPalavras.setText(null);
    }
}





