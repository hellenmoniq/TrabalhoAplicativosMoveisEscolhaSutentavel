package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.adapters.ListProductAdapter;
import com.EscolhaSustentavel.pi.model.Produto;
import com.EscolhaSustentavel.pi.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HellenMS on 19/06/2018.
 */

public class ShowFavorites extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ListProductAdapter.OnClickProdut {

    Spinner ListCateg;
    List<String> categorias;

    private RecyclerView rvListProduct;
    private TextView tvNoValues;

    private DatabaseHelper db;
    private List<Produto> listProd = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favorities);

        initView();

        db = new DatabaseHelper(ShowFavorites.this);

        ListCateg.setOnItemSelectedListener(this);
        DatabaseHelper categ = new DatabaseHelper(this);
        categorias = new ArrayList<String>();

        categorias = categ.getAllCategory();


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categorias);

        // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ListCateg.setAdapter(dataAdapter);
        ListCateg.setOnItemSelectedListener(this);

    }

    private void initView() {
        ListCateg = (Spinner) findViewById(R.id.ListCateg);

        tvNoValues = (TextView) findViewById(R.id.tv_no_values);
        rvListProduct = (RecyclerView) findViewById(R.id.rv_list_product);
        rvListProduct.setHasFixedSize(true);
        rvListProduct.setLayoutManager(new LinearLayoutManager(ShowFavorites.this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        List<String> Produtos;
        DatabaseHelper bd = new DatabaseHelper(this);
        String item = parent.getItemAtPosition(position).toString();
        String opcao = (String) ListCateg.getSelectedItem();
        //Produtos = bd.ListarProdutoByFavorite(opcao, "");

        refreshListProd(opcao);

        //Log.i("Script", "onItemSelected: " + Produtos.size());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void refreshListProd(String category) {
        listProd.clear(); //limpando os produtos listados quando mudar de categoria
        listProd = db.getAllProdutoInFavorites(category, 1);

        ListProductAdapter listAdapter = new ListProductAdapter(listProd, ShowFavorites.this);
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
    public void onSelectedProduct(Produto prod, int position) {
        Intent intent = new Intent(ShowFavorites.this, DetailProductActivity.class);

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
