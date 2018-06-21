package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.adapters.ListProductAdapter;
import com.EscolhaSustentavel.pi.model.Produto;
import com.EscolhaSustentavel.pi.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity implements ListProductAdapter.OnClickProdut {

    private final static String TYPE_VIDRO = "Vidro";
    private final static String TYPE_METAL = "Metal";
    private final static String TYPE_PLASTICO = "Plástico";

    private LinearLayout llYellow, llRed, llGreen; //linear layout amarelo, vermelho e verde (lixeiras)
    private LinearLayout llLineYellow, llLineRed, llLineGreen; //a linha (como se fosse um sublinhado)
    private RecyclerView rvListProduct;
    private TextView tvNoValues;

    private DatabaseHelper db;
    private List<Produto> listProd = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        db = new DatabaseHelper(SearchProductActivity.this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("PI");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.s_search_product);
        }

        initView();
        addListerner();
    }
// Isso aqui FOI um teste inicial
//    private void populateTableProduct() {
//        for (int i = 0; i < 15; i++) {
//            Produto prod = new Produto();
//            prod.setIdProduct(i);
//            prod.setNameProduct("Produto " + i);
//            prod.setDescProduct("Desc de um Produto QUalquer");
//            prod.setTempoProduct((i + 300) + "anos");
//            prod.setImpactProduct("Estraga tudo :)");
//
//            if (i < 5) {
//                prod.setCategoryProduct(TYPE_VIDRO);
//            } else if (i > 5 && i < 9) {
//                prod.setCategoryProduct(TYPE_METAL);
//            } else {
//                prod.setCategoryProduct(TYPE_PLASTICO);
//            }
//
//            prod.setLatProduct(Double.parseDouble("-40.3948494"));
//            prod.setLonProduct(Double.parseDouble("-20.3948494"));
//            db.addProduct(prod);
//        }
//    }
//
//    private void loadDataBd() {
//        llLineGreen.setVisibility(View.VISIBLE);
//        listProd = db.getAllProduto("Vidro");
//        ListProductAdapter listAdapter = new ListProductAdapter(listProd, SearchProductActivity.this);
//        rvListProduct.setAdapter(listAdapter);
//
//        if (rvListProduct.getAdapter().getItemCount() == 0) {
//            rvListProduct.setVisibility(View.GONE);
//            tvNoValues.setVisibility(View.VISIBLE);
//        } else {
//            rvListProduct.setVisibility(View.VISIBLE);
//            tvNoValues.setVisibility(View.GONE);
//        }
//    }

    private void initView() {
        llYellow = (LinearLayout) findViewById(R.id.ll_yellow);
        llRed = (LinearLayout) findViewById(R.id.ll_red);
        llGreen = (LinearLayout) findViewById(R.id.ll_green);

        llLineGreen = (LinearLayout) findViewById(R.id.line_green);
        llLineRed = (LinearLayout) findViewById(R.id.line_red);
        llLineYellow = (LinearLayout) findViewById(R.id.line_yellow);

        tvNoValues = (TextView) findViewById(R.id.tv_no_values);
        rvListProduct = (RecyclerView) findViewById(R.id.rv_list_product);

        rvListProduct.setHasFixedSize(true);
        rvListProduct.setLayoutManager(new LinearLayoutManager(SearchProductActivity.this, LinearLayoutManager.VERTICAL, false));
    }


    // Pegando o clique (identificando) de cada lixeira
    private void addListerner() {
        llYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llLineYellow.setVisibility(View.VISIBLE);
                llLineRed.setVisibility(View.GONE);
                llLineGreen.setVisibility(View.GONE);
                refreshListProd(TYPE_METAL);
            }
        });

        llRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llLineYellow.setVisibility(View.GONE);
                llLineRed.setVisibility(View.VISIBLE);
                llLineGreen.setVisibility(View.GONE);
                refreshListProd(TYPE_PLASTICO);
            }
        });

        llGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llLineYellow.setVisibility(View.GONE);
                llLineRed.setVisibility(View.GONE);
                llLineGreen.setVisibility(View.VISIBLE);
                refreshListProd(TYPE_VIDRO);
            }
        });
    }

    private void refreshListProd(String category) {
        listProd.clear(); //limpando os produtos listados quando mudar de categoria
        listProd = db.getAllProduto(category);

        ListProductAdapter listAdapter = new ListProductAdapter(listProd, SearchProductActivity.this);
        rvListProduct.setAdapter(listAdapter);

        if (rvListProduct.getAdapter().getItemCount() == 0) {
            rvListProduct.setVisibility(View.GONE);
            tvNoValues.setVisibility(View.VISIBLE);
        } else {
            rvListProduct.setVisibility(View.VISIBLE);
            tvNoValues.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSelectedProduct(Produto prod, int position) {
        // TODO: 01/05/2017 Aqui é pego o produto selecionado e enviado para a próxima tela

        // TODO: 08/05/17 A intent é uma 'intenção' onde você define para qual classe quer ir
        Intent intent = new Intent(SearchProductActivity.this, DetailProductActivity.class);

        // TODO: 08/05/17 O Bundle passa valores entre as activitys usando o modelo de chave - valor
        Bundle b = new Bundle();
        b.putInt("prod_id", prod.getIdProduct());
        b.putString("prod_name", prod.getNameProduct());
        b.putString("prod_desc", prod.getDescProduct());
        b.putString("prod_comp", prod.getCompProduct());
        b.putString("prod_impact", prod.getImpactProduct());
        b.putString("prod_time", prod.getTempoProduct());
        b.putString("prod_url", prod.getUrlProduct());
        b.putString("prod_categ", prod.getCategoryProduct());
        b.putBoolean("prod_favorite", prod.isFavorite());


        intent.putExtras(b);
        startActivity(intent);

    }
}
