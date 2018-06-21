package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.fragments.DrawerFragment;
import com.EscolhaSustentavel.pi.fragments.FragHome;
import com.EscolhaSustentavel.pi.model.Produto;
import com.EscolhaSustentavel.pi.sql.DatabaseHelper;


public class HomeActivity extends AppCompatActivity implements DrawerFragment.OnDrawerLeftActionListener {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private TextView tvName, tvEmail;
    String nome, urlfoto;
    ImageView iv_profile_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_home_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.s_tab_home);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_home);
        tvName = (TextView) findViewById(R.id.tv_name_drawer);
        tvEmail = (TextView) findViewById(R.id.tv_email_drawer);
        iv_profile_drawer = (ImageView) findViewById(R.id.iv_profile_drawer);


        mDrawerLayout.addDrawerListener(actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.s_tab_home,
                R.string.s_tab_home
        ));

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            getSupportFragmentManager().popBackStack();
            fragmentTransaction.replace(R.id.fl_fragment_container, FragHome.newInstane());
            fragmentTransaction.commit();
        }


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        nome = bundle.getString("nome");
        String email = bundle.getString("email");

        //Exibindo o nome do usuário logado;
        tvName.setText(nome);
        tvEmail.setText(email);


        enableDrawer();
        populateBd();

    }

    //Menu onde fica a opção alterar senha e encerrra sessão
    @Override
    public void onDrawerUpdatePassword() {
        startActivity(new Intent(HomeActivity.this, AltereSenha.class));
        closeDrawer();
    }

    public void onDrawerAlterarNome() {
        startActivity(new Intent(HomeActivity.this, AlterarConta.class));

    }

    public void onDrawerExcluirConta() {
        startActivity(new Intent(HomeActivity.this, ExcluirConta.class));

    }


    @Override
    public void onDrawerLogout() {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    private void enableDrawer() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
        actionBarDrawerToggle.syncState();
    }

    private void closeDrawer() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
    }

    //Populando o banco com os produtos
    private void populateBd() {
        DatabaseHelper db;
        db = new DatabaseHelper(HomeActivity.this);
        db.clearData();

        // COPO
        Produto prod = new Produto();
        prod.setNameProduct("Copo");
        prod.setDescProduct("Copo de Vidro");
        prod.setCompProduct("Vidro");
        prod.setTempoProduct("4 mil anos");
        prod.setImpactProduct("Contaminação do solo");
        prod.setCategoryProduct("Vidro");
        prod.setLatProduct(Double.parseDouble("-40.3948494")); //Aqui é pra preencher com a latitude (coloquei ai um numero aleatorio)
        prod.setLonProduct(Double.parseDouble("-20.3948494")); //Aqui é pra preencher com a longitude (coloquei ai um numero aleatorio)
        prod.setUrlProduct("file:///android_asset/copo_vidro.png"); // caminho da imagem do produto na pasta assets
        db.addProduct(prod);

        Produto prod1 = new Produto();
        prod1.setNameProduct("Copo");
        prod1.setDescProduct("Copo de Metal");
        prod1.setCompProduct("Alumínio");
        prod1.setTempoProduct("200 a 500 anos");
        prod1.setImpactProduct("Emissão de gases poluentes por conta do minério bauxita");
        prod1.setCategoryProduct("Metal");
        prod1.setLatProduct(Double.parseDouble("-40.3948494"));
        prod1.setLonProduct(Double.parseDouble("-20.3948494"));
        prod1.setUrlProduct("file:///android_asset/copo_metal.png");
        db.addProduct(prod1);

        Produto prod2 = new Produto();
        prod2.setNameProduct("Copo");
        prod2.setDescProduct("Copo de Plático");
        prod2.setCompProduct("Poliestireno(PS)");
        prod2.setTempoProduct("100 a 400 anos");
        prod2.setImpactProduct("Morte de animais marinhos nos oceanos/ quando ingeridos pode ajudar no desenvolvimeno de cânceres.");
        prod2.setCategoryProduct("Plástico");
        prod2.setLatProduct(Double.parseDouble("-40.3948494"));
        prod2.setLonProduct(Double.parseDouble("-20.3948494"));
        prod2.setUrlProduct("file:///android_asset/copo_plastico.png");
        db.addProduct(prod2);

        // Vassoura
        Produto prod3 = new Produto();
        prod3.setNameProduct("Vassoura");
        prod3.setDescProduct("Vassoura cabo de aço inox");
        prod3.setCompProduct("Aço");
        prod3.setTempoProduct("10 anos");
        prod3.setImpactProduct("Demanda uma enorme quantidade de energia na sua fabricação e degradação do solo");
        prod3.setCategoryProduct("Metal");
        prod3.setLatProduct(Double.parseDouble("-40.3948494"));
        prod3.setLonProduct(Double.parseDouble("-20.3948494"));
        prod3.setUrlProduct("file:///android_asset/vassoura_metal.png");
        db.addProduct(prod3);

        Produto prod4 = new Produto();
        prod4.setNameProduct("Vassoura");
        prod4.setDescProduct("Vassoura plástico (Reciclada)");
        prod4.setCompProduct("PET");
        prod4.setTempoProduct("450 anos");
        prod4.setImpactProduct("Emissões atmosféricas de CO2");
        prod4.setCategoryProduct("Plástico");
        prod4.setLatProduct(Double.parseDouble("-40.3948494"));
        prod4.setLonProduct(Double.parseDouble("-20.3948494"));
        prod4.setUrlProduct("file:///android_asset/vassoura_plastico.png");
        db.addProduct(prod4);


        //Garrafa
        Produto prod5 = new Produto();
        prod5.setNameProduct("Jarra");
        prod5.setDescProduct("Jarra de plástico rígido");
        prod5.setCompProduct("Polipropileno");
        prod5.setTempoProduct("450 anos");
        prod5.setImpactProduct("Emissões atmosféricas de CO2");
        prod5.setCategoryProduct("Plástico");
        prod5.setLatProduct(Double.parseDouble("-40.3948494"));
        prod5.setLonProduct(Double.parseDouble("-20.3948494"));
        prod5.setUrlProduct("file:///android_asset/jarra_plastico.png");
        db.addProduct(prod5);


        Produto prod6 = new Produto();
        prod6.setNameProduct("Jarra");
        prod6.setDescProduct("Jarra de Vidro");
        prod6.setCompProduct("base sílica(areia) e dióxido de silício");
        prod6.setTempoProduct("Indeterminado");
        prod6.setImpactProduct("retira-se matérias-primas do meio ambiente necessárias à produção e consome \n" +
                "muita energia");
        prod6.setCategoryProduct("Vidro");
        prod6.setLatProduct(Double.parseDouble("-40.3948494"));
        prod6.setLonProduct(Double.parseDouble("-20.3948494"));
        prod6.setUrlProduct("file:///android_asset/jarra_vidro.png");
        db.addProduct(prod6);


        //Squeeze
        Produto prod7 = new Produto();
        prod7.setNameProduct("Squeeze");
        prod7.setDescProduct("Squeeze Plástico Dobrável");
        prod7.setCompProduct("Plático PEAD (Polietileno)");
        prod7.setTempoProduct("50 anos");
        prod7.setImpactProduct("podem acumular microorganismos após alguns dias de uso, que podem causar males à saúde, como infecções");
        prod7.setCategoryProduct("Plástico");
        prod7.setLatProduct(Double.parseDouble("-40.3948494"));
        prod7.setLonProduct(Double.parseDouble("-20.3948494"));
        prod7.setUrlProduct("file:///android_asset/squeeze_plastico.png");
        db.addProduct(prod7);

        Produto prod8 = new Produto();
        prod8.setNameProduct("Squeeze");
        prod8.setDescProduct("Squeeze aço inox");
        prod8.setCompProduct("Minério de ferro denominado hematita");
        prod8.setTempoProduct("100 a 250 anos");
        prod8.setImpactProduct("Demanda uma enorme quantidade de energia na sua fabricação e degradação do solo");
        prod8.setCategoryProduct("Metal");
        prod8.setLatProduct(Double.parseDouble("-40.3948494"));
        prod8.setLonProduct(Double.parseDouble("-20.3948494"));
        prod8.setUrlProduct("file:///android_asset/squeeze_metal.png");
        db.addProduct(prod8);


        //Tigela
        Produto prod9 = new Produto();
        prod9.setNameProduct("Tigela");
        prod9.setDescProduct("Tigela de Vidro (multiuso)");
        prod9.setCompProduct("areia, calcário, barrilha (carbonato de sódio)");
        prod9.setTempoProduct("Mais de 10 mil anos");
        prod9.setImpactProduct("quando levado para os aterros sanitários, não se decompõe, o que diminui a vida útil do aterro");
        prod9.setCategoryProduct("Vidro");
        prod9.setLatProduct(Double.parseDouble("-40.3948494"));
        prod9.setLonProduct(Double.parseDouble("-20.3948494"));
        prod9.setUrlProduct("file:///android_asset/tigela_vidro.png");
        db.addProduct(prod9);

        Produto prod10 = new Produto();
        prod10.setNameProduct("Tigela");
        prod10.setDescProduct("Tigela de Metal (multiuso)");
        prod10.setCompProduct("Ferro");
        prod10.setTempoProduct("10 anos");
        prod10.setImpactProduct("desmatamento do solo para extração do minério de ferro, poluição do ar gerado pelo pó da mineração");
        prod10.setCategoryProduct("Metal");
        prod10.setLatProduct(Double.parseDouble("-40.3948494"));
        prod10.setLonProduct(Double.parseDouble("-20.3948494"));
        prod10.setUrlProduct("file:///android_asset/tigela_metal.png");
        db.addProduct(prod10);


    }


}


