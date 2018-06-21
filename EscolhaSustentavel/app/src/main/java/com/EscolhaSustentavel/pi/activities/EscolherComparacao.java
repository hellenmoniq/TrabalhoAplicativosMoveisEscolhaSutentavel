package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.model.Produto;
import com.EscolhaSustentavel.pi.sql.DatabaseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EscolherComparacao extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String nameProd, descProd, compProd, timeProd, impactoProd, urlProd;
    private Double lat, lon;
    private int idProd, idSecondProd;
    private boolean prodFirst = false;
    private boolean prodSecond = false;

    private TextView tvDesc, tvComp, tvTime, tvImpact, camp_desc2, camp_impact2, camp_comp2, camp_time2;
    private ImageView ivProdFirst, ivProdSecond;
    private ImageView ivFirst, ivSecond;
    Spinner ListCateg;
    ArrayList Categorias;
    List<String> categorias;
    String nome;
    String ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_escolher_comparacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //recuperando os dados de composicao
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String txt = bundle.getString("txt");
        TextView txtResultado = (TextView) findViewById(R.id.camp_comp);
        txtResultado.setText(txt);

        idProd = bundle.getInt("id");

        //recuperando os dados de descrição
        String desc = bundle.getString("desc");
        TextView descr = (TextView) findViewById(R.id.camp_desc);
        descr.setText(desc);

        //recuperando os dados de impacto
        String impact = bundle.getString("impact");
        TextView impc = (TextView) findViewById(R.id.camp_impact);
        impc.setText(impact);

        //recuperando os dados de tempo de composicao


        String time = bundle.getString("time");
        TextView tempo = (TextView) findViewById(R.id.camp_time);
        tempo.setText(time);

        //recuperando o nome do produto
        String nomep = bundle.getString("prod_nome");
        nome = nomep;

        //recuperando a categoria do produto

        String categp = bundle.getString("produ_categ");


        prodFirst = bundle.getBoolean("prod_favorite");


        //recuperando a url da imagem

        String foto = bundle.getString("prod_url");
        urlProd = foto;

        initView();
        setData();
        addListerner();
        //setDataimg2();


        ListCateg.setOnItemSelectedListener(this);
        DatabaseHelper categ = new DatabaseHelper(this);
        categorias = new ArrayList<String>();

        categorias = categ.ListarCategorias(categp, nomep);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categorias);

        // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ListCateg.setAdapter(dataAdapter);
        ListCateg.setOnItemSelectedListener(this);

        getCountFavorite();
        verifyProductOne();
    }

    private void initView() {
        tvComp = (TextView) findViewById(R.id.camp_comp);
        tvTime = (TextView) findViewById(R.id.camp_time);
        tvImpact = (TextView) findViewById(R.id.camp_impact);
        ivFirst = (ImageView) findViewById(R.id.im);
        ivSecond = (ImageView) findViewById(R.id.im2);
        ivProdFirst = (ImageView) findViewById(R.id.iv_favorite_first);
        tvDesc = (TextView) findViewById(R.id.camp_desc);
        ListCateg = (Spinner) findViewById(R.id.ListCateg);
        camp_desc2 = (TextView) findViewById(R.id.camp_desc2);
        camp_impact2 = (TextView) findViewById(R.id.camp_impact2);
        camp_comp2 = (TextView) findViewById(R.id.camp_comp2);
        camp_time2 = (TextView) findViewById(R.id.camp_time2);
        ivProdSecond = (ImageView) findViewById(R.id.iv_favorite_second);
    }

    private void addListerner() {
        ivProdFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper bd = new DatabaseHelper(EscolherComparacao.this);
                boolean status = bd.updateProductForFavorite(idProd, prodFirst ? 0 : 1);
                if (status) {
                    if(prodFirst){
                        prodFirst = false;
                        Toast.makeText(EscolherComparacao.this, "Esse produto não é mais um favorito", Toast.LENGTH_SHORT).show();
                    } else {
                        prodFirst = true;
                        Toast.makeText(EscolherComparacao.this, "Esse produto agora é um favorito", Toast.LENGTH_SHORT).show();
                    }
                }
                getCountFavorite();
                verifyProductOne();
            }
        });

        ivProdSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper bd = new DatabaseHelper(EscolherComparacao.this);
                boolean status = bd.updateProductForFavorite(idSecondProd, prodSecond ? 0 : 1);
                if (status) {
                    if(prodSecond){
                        prodSecond = false;
                        Toast.makeText(EscolherComparacao.this, "Esse produto não é mais um favorito", Toast.LENGTH_SHORT).show();
                    } else {
                        prodSecond = true;
                        Toast.makeText(EscolherComparacao.this, "Esse produto agora é um favorito", Toast.LENGTH_SHORT).show();
                    }
                }
                getCountFavorite();
                verifyProductTwo();
            }
        });
    }

    private void getCountFavorite() {
        List<Produto> listProd = new ArrayList<>();
        DatabaseHelper bd = new DatabaseHelper(EscolherComparacao.this);
        listProd = bd.getAllProdutoInFavorites("", 1);
        Log.i("Script", "getCountFavorite: " + listProd.size());
    }

    //capturando e setando a imagem pela url
    private void setData() {
        try {
            String nameFile = urlProd.substring(urlProd.lastIndexOf('/'));
            InputStream ims = getAssets().open(nameFile.substring(1));
            Drawable d = Drawable.createFromStream(ims, null);
            ivFirst.setImageDrawable(d);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    //método que irá setar a imagem do segundo produto
    private void setDataimg2() {
        try {
            String nameFile = ft.substring(ft.lastIndexOf('/'));
            InputStream ims = getAssets().open(nameFile.substring(1));
            Drawable d = Drawable.createFromStream(ims, null);
            ivSecond.setImageDrawable(d);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private void verifyProductOne() {
        if (prodFirst) {
            ivProdFirst.setImageDrawable(ContextCompat.getDrawable(EscolherComparacao.this, R.drawable.ic_star_full));
        } else {
            ivProdFirst.setImageDrawable(ContextCompat.getDrawable(EscolherComparacao.this, R.drawable.ic_star_empty));
        }
    }

    private void verifyProductTwo() {
        if (prodSecond) {
            ivProdSecond.setImageDrawable(ContextCompat.getDrawable(EscolherComparacao.this, R.drawable.ic_star_full));
        } else {
            ivProdSecond.setImageDrawable(ContextCompat.getDrawable(EscolherComparacao.this, R.drawable.ic_star_empty));
        }
    }

    //método que irá exibir as informações do produto após ele ser selecionado no spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        List<String> Produtos;
        DatabaseHelper bd = new DatabaseHelper(this);
        String item = parent.getItemAtPosition(position).toString();
        String opcao = (String) ListCateg.getSelectedItem();
        Produtos = bd.ListarProduto(opcao, nome);

        Log.i("Script", "onItemSelected: " + Produtos.get(7));

        idSecondProd = Integer.parseInt(Produtos.get(0));
        camp_impact2.setText(Produtos.get(2));
        camp_time2.setText(Produtos.get(3));
        camp_comp2.setText(Produtos.get(4));
        camp_desc2.setText(Produtos.get(5));
        ft = Produtos.get(7);
        prodSecond = Integer.parseInt(Produtos.get(8)) != 0;

        setDataimg2();
        verifyProductTwo();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
