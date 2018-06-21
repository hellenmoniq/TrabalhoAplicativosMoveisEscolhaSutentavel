package com.EscolhaSustentavel.pi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.activities.AboutActivity;
import com.EscolhaSustentavel.pi.activities.HelpActivity;
import com.EscolhaSustentavel.pi.activities.InformationActivity;
import com.EscolhaSustentavel.pi.activities.SearchProductActivity;
import com.EscolhaSustentavel.pi.activities.ShowFavorites;


public class FragHome extends Fragment {


    private Button btFind ,BtHelp, btAbout, btInformation, btFavorites;

    public static FragHome newInstane() {
        return new FragHome();
    }

    public FragHome() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View rootView = getView();
        if (rootView != null) {
            btFind = (Button) rootView.findViewById(R.id.bt1);
            btFind = (Button) rootView.findViewById(R.id.bt1);
            BtHelp = (Button) rootView.findViewById(R.id.bt5);
            btAbout = (Button) rootView.findViewById(R.id.bt4);
            btFavorites = (Button) rootView.findViewById(R.id.bt2);
            btInformation = (Button) rootView.findViewById(R.id.bt3);

            Busca();
            Ajuda();
            Sobre();
            Informacao();
            Favoritos();
        }
    }


    private void Busca() {
        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchProductActivity.class));
            }
        });
    }

    private void Favoritos(){
        btFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ShowFavorites.class));
            }
        });
    }

    private void Ajuda() {
        BtHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HelpActivity.class));
            }
        });
    }

    private void Sobre() {
        btAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });
    }

    private void Informacao() {
        btInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), InformationActivity.class));
            }
        });
    }


}










