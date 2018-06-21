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

public class ExcluirConta extends AppCompatActivity implements View.OnClickListener {


    private final AppCompatActivity activity = ExcluirConta.this;

    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutNickname;
    private TextInputLayout textInputLayoutPalavraS;

    private TextInputEditText textInputEditTextNickname;
    private TextInputEditText textInputEditTextPalavras;
    private AppCompatButton appCompatButtonExcluir;

    private ValidarEntrada validarEntrada;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_conta);
        IniciarViews();
        IniciartListeners();
        IniciarObjetos();
        ;

    }

    private void IniciarViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView4);

        textInputLayoutNickname = (TextInputLayout) findViewById(R.id.textInputLayoutnickname3);
        textInputLayoutPalavraS = (TextInputLayout) findViewById(R.id.textInputLayoutPalavrasegu3);

        textInputEditTextNickname = (TextInputEditText) findViewById(R.id.textInputEditTextnickname3);
        textInputEditTextPalavras = (TextInputEditText) findViewById(R.id.textInputEditTextPalavrasegu3);
        appCompatButtonExcluir = (AppCompatButton) findViewById(R.id.appCompatButtonExcluirConta);


    }


    private void IniciartListeners() {

        appCompatButtonExcluir.setOnClickListener(this);
    }


    private void IniciarObjetos() {
        //se ficar dando erro tirar o this
        validarEntrada = new ValidarEntrada(activity);
        databaseHelper = new DatabaseHelper(activity);

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonExcluirConta:
                ExcluirConta();
                break;
        }
    }


    private void ExcluirConta() {


        //verificando as entradas

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextNickname, textInputLayoutNickname, "Digite o Nickname")) {
            return;
        }

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextPalavras, textInputLayoutPalavraS, "Digite a palavra de segurança")) {
            return;
        }

        //Verficando se palavra de segurança e o nickname pertece ao usuario

        if (databaseHelper.CheckPalavra(textInputEditTextNickname.getText().toString().trim()
                , textInputEditTextPalavras.getText().toString().trim())) {

            //caso a palavra de segurança e nickname pertença a senha do usuario será trocada
            databaseHelper.deleteUser(textInputEditTextNickname.getText().toString().trim(), textInputEditTextPalavras.getText().toString().trim());
            Snackbar.make(nestedScrollView, "A conta foi excluida  com sucesso!", Snackbar.LENGTH_LONG).show();
            LimparCampos();
            ChamarTelaLogin();


        } else {

            Snackbar.make(nestedScrollView, "Usuario não econtrado!", Snackbar.LENGTH_LONG).show();
        }


    }


    private void LimparCampos() {
        textInputEditTextNickname.setText(null);
        textInputEditTextPalavras.setText(null);
    }


    public void ChamarTelaLogin() {
        startActivity(new Intent(ExcluirConta.this, LoginActivity.class));

    }

}