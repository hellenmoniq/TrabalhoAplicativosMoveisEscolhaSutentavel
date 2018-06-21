package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.helpers.ValidarEntrada;
import com.EscolhaSustentavel.pi.model.Usuario;
import com.EscolhaSustentavel.pi.sql.DatabaseHelper;


public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = CadastroActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutNome;
    private TextInputLayout textInputLayoutNickname;
    private TextInputLayout textInputLayoutSenha;
    private TextInputLayout textInputLayoutPalavraS;

    private TextInputEditText textInputEditTextNome;
    private TextInputEditText textInputEditTextNickname;
    private TextInputEditText textInputEditTextSenha;
    private TextInputEditText textInputEditTextPalavras;

    private AppCompatButton appCompatButtonCadastrar;
    private AppCompatTextView appCompatTextViewLoginLink;

    private ValidarEntrada validarEntrada;
    private DatabaseHelper databaseHelper;
    private Usuario usuario;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        IniciarViews();
        IniciartListeners();
        IniciarObjetos();
    }


    private void IniciarViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutNome = (TextInputLayout) findViewById(R.id.textInputLayoutNome);
        textInputLayoutNickname = (TextInputLayout) findViewById(R.id.textInputLayoutNickname);
        textInputLayoutSenha = (TextInputLayout) findViewById(R.id.textInputLayoutSenha);
        textInputLayoutPalavraS = (TextInputLayout) findViewById(R.id.textInputLayoutPalavraS);

        textInputEditTextNome = (TextInputEditText) findViewById(R.id.textInputEditTextNome);
        textInputEditTextNickname = (TextInputEditText) findViewById(R.id.textInputEditTextNickname);
        textInputEditTextSenha = (TextInputEditText) findViewById(R.id.textInputEditTextSenha);
        textInputEditTextPalavras = (TextInputEditText) findViewById(R.id.textInputEditTextPalavras);

        appCompatButtonCadastrar = (AppCompatButton) findViewById(R.id.appCompatButtonCadastrar);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

    }


    private void IniciartListeners() {
        appCompatButtonCadastrar.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }


    private void IniciarObjetos() {
        validarEntrada = new ValidarEntrada(activity);
        databaseHelper = new DatabaseHelper(activity);
        usuario = new Usuario();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonCadastrar:
                CadastroUsuario();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     */
    private void CadastroUsuario() {


        //verificando as entradas

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextNickname, textInputLayoutNickname, "Digite o Nickname")) {
            return;
        }

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextSenha, textInputLayoutSenha, "Digite a Senha")) {
            return;
        }

        //se tudo foi digitado corretamente o usuario será cadastrado

        if (!databaseHelper.checkUser(textInputEditTextNickname.getText().toString().trim())) {

            usuario.setNome(textInputEditTextNome.getText().toString().trim());
            usuario.setNickname(textInputEditTextNickname.getText().toString().trim());
            usuario.setSenha(textInputEditTextSenha.getText().toString().trim());
            usuario.setPalavraseg(textInputEditTextPalavras.getText().toString().trim());
            databaseHelper.addUser(usuario);


            // se ainda não existe o nome de usuario no banco será cadastrado com sucesso
            Snackbar.make(nestedScrollView, "Cadastro realizado com sucesso", Snackbar.LENGTH_LONG).show();

            LimparCampos();
            ChamarTela();


        } else {
            // caso exista mostrará essa mensagem
            Snackbar.make(nestedScrollView, "Esse nome de usuario já existe", Snackbar.LENGTH_LONG).show();
        }


    }

    //Esse método é para limpar os campos de entrada

    private void LimparCampos() {
        textInputEditTextNome.setText(null);
        textInputEditTextNickname.setText(null);
        textInputEditTextSenha.setText(null);
        textInputEditTextPalavras.setText(null);
    }

    public void ChamarTela() {
        startActivity(new Intent(CadastroActivity.this, LoginActivity.class));

    }
}
