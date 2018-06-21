package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.helpers.ValidarEntrada;
import com.EscolhaSustentavel.pi.model.Usuario;
import com.EscolhaSustentavel.pi.sql.DatabaseHelper;





public class AlterarConta extends AppCompatActivity implements View.OnClickListener {


    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutNovoNome;
    private TextInputLayout textInputLayoutNovaSenha;
    private TextInputLayout textInputLayoutPalavraS;
    private TextInputLayout textInputLayoutNickname;


    private AppCompatButton appCompatButtonAlterar;

    private TextInputEditText textInputEditTextNovoNome;
    private TextInputEditText textInputEditTextPalavras;
    private TextInputEditText textInputEditTextNickname;


    private ValidarEntrada validarEntrada;
    private DatabaseHelper databaseHelper;
    private Usuario usuario;

    @Override

        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_alterar_conta);
            IniciarViews();
            IniciartListeners();
            IniciarObjetos();;

        }

    private void IniciarViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutNovoNome = (TextInputLayout) findViewById(R.id.textInputLayoutnovoNome);
        textInputLayoutPalavraS = (TextInputLayout) findViewById(R.id.textInputLayoutPalavrasegu);
        textInputLayoutNickname = (TextInputLayout) findViewById(R.id.textInputLayoutnickname2);

        textInputEditTextNovoNome = (TextInputEditText) findViewById(R.id.textInputEditTextnovoNome);
        textInputEditTextPalavras = (TextInputEditText) findViewById(R.id.textInputEditTextPalavrasegu);
        //appCompatButtonAlterar = (AppCompatButton) findViewById(R.id.bt_altereNome);
        appCompatButtonAlterar = (AppCompatButton) findViewById(R.id.appCompatButtonAlterarNome);
        textInputEditTextNickname = (TextInputEditText) findViewById(R.id.textInputEditTextnickname2);



    }




    private void IniciartListeners() {

        appCompatButtonAlterar.setOnClickListener(this);

    }


    private void IniciarObjetos() {
        //se ficar dando erro tirar o this
        validarEntrada = new ValidarEntrada(this);
        databaseHelper = new DatabaseHelper(this);
        usuario = new Usuario();

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonAlterarNome:
                AlterarNome();
                break;
        }
    }




    private void AlterarNome() {


        //verificando as entradas
        if (!validarEntrada.isInputEditTextFilled(textInputEditTextNickname, textInputLayoutNickname, "Digite o Nickname")) {
            return;
        }

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextNovoNome,textInputLayoutNovoNome , "Digite o Novo nome")) {
            return;
        }

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextPalavras, textInputLayoutPalavraS, "Digite a palavra de segurança")) {
            return;
        }

        //Verficando se palavra de segurança e o nickname pertece ao usuario

        if (databaseHelper.CheckPalavra(textInputEditTextNickname.getText().toString().trim()
                , textInputEditTextPalavras.getText().toString().trim())) {

            //caso a palavra de segurança e nickname pertença a senha do usuario será trocada
            databaseHelper.AlterarNome(textInputEditTextNickname.getText().toString().trim(),textInputEditTextPalavras.getText().toString().trim(),textInputEditTextNovoNome.getText().toString().trim());
            Snackbar.make(nestedScrollView, "O nome foi alterado corretamente!", Snackbar.LENGTH_LONG).show();
            LimparCampos();

        } else {

            Snackbar.make(nestedScrollView, "Palavra de segurança ou usuario digitados incorretamente", Snackbar.LENGTH_LONG).show();
            LimparCampos();
            //ChamarTela();

        }


    }









    private void LimparCampos() {
        textInputEditTextNovoNome.setText(null);
        textInputEditTextPalavras.setText(null);
        textInputEditTextNickname.setText(null);
    }



    public void ChamarTela() {
        startActivity(new Intent(AlterarConta.this, HomeActivity.class));

    }

}


