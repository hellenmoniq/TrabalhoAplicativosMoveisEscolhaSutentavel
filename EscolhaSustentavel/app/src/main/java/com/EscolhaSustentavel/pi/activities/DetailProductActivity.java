package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.EscolhaSustentavel.pi.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Hellen on 07/05/17 for EscolhaSustentavel.
 */

// Activity Detalhes do produto
public class DetailProductActivity extends AppCompatActivity {

    private String nameProd, descProd, compProd, timeProd, impactoProd, urlProd;
    private Double lat, lon;
    private int idProd;
    private boolean isFavorite = false;

    private TextView tvDesc;
    private TextView tvComp;
    private TextView tvTime;
    private TextView tvImpact;
    private String tvname;
    private String tvcateg;
    private ImageView ivProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            idProd = b.getInt("prod_id");
            nameProd = b.getString("prod_name");
            descProd = b.getString("prod_desc");
            compProd = b.getString("prod_comp");
            timeProd = b.getString("prod_time");
            impactoProd = b.getString("prod_impact");
            urlProd = b.getString("prod_url");  // url é o caminho da foto
            tvname = b.getString("prod_name");
            tvcateg = b.getString("prod_categ");
            isFavorite = b.getBoolean("prod_favorite");
        }

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(nameProd);
        }

        initView();
        setData();
    }

    private void initView() {
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvComp = (TextView) findViewById(R.id.tv_comp);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvImpact = (TextView) findViewById(R.id.tv_impact);
        ivProd = (ImageView) findViewById(R.id.iv);
    }

    public void onSelectedDisponibilidade(View view) {
        Intent itMap = new Intent(DetailProductActivity.this, MapsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("categoria", tvcateg);
        itMap.putExtras(bundle);
        //setContentView(R.layout.activity_maps);
        startActivity(itMap);
    }

    public void ChamarTelaCompare(View view) {

        //chamando a tela de comparação
        Intent intent = new Intent(DetailProductActivity.this, EscolherComparacao.class);

        //associando os dados dos produtos
        TextView edtTexto = (TextView) findViewById(R.id.tv_comp);
        TextView edtTexto2 = (TextView) findViewById(R.id.tv_desc);
        TextView edtTexto3 = (TextView) findViewById(R.id.tv_time);
        TextView edtTexto4 = (TextView) findViewById(R.id.tv_impact);
        ImageView img = (ImageView) view.findViewById(R.id.iv);


        //passando os dados do produto para a outra tela
        String txt = "", desc = "", time = "", impact = "";
        txt = edtTexto.getText().toString();
        desc = edtTexto2.getText().toString();
        time = edtTexto3.getText().toString();
        impact = edtTexto4.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putInt("id", idProd);
        bundle.putString("txt", txt);
        bundle.putString("desc", desc);
        bundle.putString("time", time);
        bundle.putString("impact", impact);
        bundle.putString("prod_url", urlProd);
        bundle.putString("prod_nome", tvname);
        bundle.putString("produ_categ", tvcateg);
        bundle.putBoolean("prod_favorite", isFavorite);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setData() {
        tvDesc.setText(descProd);
        tvComp.setText(compProd);
        tvTime.setText(timeProd);
        tvImpact.setText(impactoProd);

        try {
            String nameFile = urlProd.substring(urlProd.lastIndexOf('/'));
            InputStream ims = getAssets().open(nameFile.substring(1));
            Drawable d = Drawable.createFromStream(ims, null);
            ivProd.setImageDrawable(d);
        } catch (IOException e) {
            e.getMessage();
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

}
