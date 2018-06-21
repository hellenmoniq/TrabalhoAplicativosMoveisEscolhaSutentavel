package com.EscolhaSustentavel.pi.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.helpers.ValidarEntrada;
import com.EscolhaSustentavel.pi.sql.DatabaseHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutNickname;
    private TextInputLayout textInputLayoutSenha;

    private TextInputEditText textInputEditTextNickname;
    private TextInputEditText textInputEditTextSenha;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkCadastro;
    private AppCompatTextView altereSenha;
    private ValidarEntrada validarEntrada;
    private DatabaseHelper databaseHelper;
    private SignInButton singin;
    private TextView Nome, Email;
    private ImageView foto;
    private GoogleApiClient googleApiClient;
    private static final int REQ_COD = 9001;
    private LinearLayout prof_section;
    String nome, Emailg;
    String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        IniciarViews();
        IniciarListeners();
        IniciarObjetos();
        EmitirNotificacao();

        GoogleSignInOptions opcoes = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, opcoes).build();
    }

    private void IniciarViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutNickname = (TextInputLayout) findViewById(R.id.textInputLayoutNickname);
        textInputLayoutSenha = (TextInputLayout) findViewById(R.id.textInputLayoutSenha);


        textInputEditTextNickname = (TextInputEditText) findViewById(R.id.textInputEditTextNickname);
        textInputEditTextSenha = (TextInputEditText) findViewById(R.id.textInputEditTextSenha);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);
        textViewLinkCadastro = (AppCompatTextView) findViewById(R.id.textViewLinkCadastro);
        textViewLinkCadastro = (AppCompatTextView) findViewById(R.id.textViewLinkCadastro);
        altereSenha = (AppCompatTextView) findViewById(R.id.altereSenha);
        singin = (SignInButton) findViewById(R.id.sign_in_button);
    }


    private void IniciarListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkCadastro.setOnClickListener(this);
        altereSenha.setOnClickListener(this);
        singin.setOnClickListener(this);

    }

    private void IniciarObjetos() {
        databaseHelper = new DatabaseHelper(activity);
        validarEntrada = new ValidarEntrada(activity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                VerificarLogin();
                break;
            case R.id.textViewLinkCadastro:
                Intent intentRegister = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.altereSenha:
                Intent intentAltere = new Intent(getApplicationContext(), AltereSenha.class);
                startActivity(intentAltere);
                break;
            case R.id.sign_in_button:
                Singin();
                break;
        }
    }


    public void EmitirNotificacao() {

        //tirar o botão informações sustentaveis do menu principal

        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("EscolhaSustentavel")
                        .setContentText("Se divirta com o nosso quiz!");


        //definindo vibração

        mBuilder.setVibrate(new long[]{150, 300, 150, 600});


        Intent resultIntent = new Intent(this, Quiz.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(Quiz.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int mId = 0;
        mNotificationManager.notify(mId, mBuilder.build());


    }

    private void VerificarLogin() {

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextSenha, textInputLayoutSenha, "Digite a Senha")) {
            return;
        }

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextNickname, textInputLayoutNickname, "Digite o Nickname")) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextNickname.getText().toString().trim()
                , textInputEditTextSenha.getText().toString().trim())) {

//            Intent accountsIntent = new Intent(activity, ListarUsuarios.class);
//            accountsIntent.putExtra("EMAIL", textInputEditTextNickname.getText().toString().trim());
//            LimparCampos();
//            startActivity(accountsIntent);

            // Intent accountsIntent = new Intent(activity, HomeActivity.class);
            //startActivity(accountsIntent);

            singin.setVisibility(View.VISIBLE);

            Bundle bundle = new Bundle();
            bundle.putString("nome", nome);
            bundle.putString("email", Emailg);
            bundle.putString("foto", img);
            Intent accountsIntent = new Intent(activity, HomeActivity.class);

            accountsIntent.putExtras(bundle);
            startActivity(accountsIntent);


        } else {
            Snackbar.make(nestedScrollView, "Nickname ou senha incorretos", Snackbar.LENGTH_LONG).show();
        }
    }


    //Esse método é para limpar os campos
    private void LimparCampos() {
        textInputEditTextNickname.setText(null);
        textInputEditTextSenha.setText(null);
    }


    private void Singin() {

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_COD);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void handleresult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            img = account.getPhotoUrl().toString();
            nome = name;
            Emailg = email;
            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    public void singout() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

                updateUI(false);

            }
        });
    }

    private void updateUI(boolean login) {

        if (login) {
            singin.setVisibility(View.VISIBLE);

            Bundle bundle = new Bundle();
            bundle.putString("nome", nome);
            bundle.putString("email", Emailg);
            bundle.putString("foto", img);
            Intent accountsIntent = new Intent(activity, HomeActivity.class);

            accountsIntent.putExtras(bundle);
            startActivity(accountsIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_COD) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleresult(result);
        }
    }
}