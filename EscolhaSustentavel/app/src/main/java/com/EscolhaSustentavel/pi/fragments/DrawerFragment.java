package com.EscolhaSustentavel.pi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.EscolhaSustentavel.pi.R;



public class DrawerFragment extends Fragment {

    private OnDrawerLeftActionListener mActionListener;

    public DrawerFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActionListener = (OnDrawerLeftActionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("You must implement OnDrawerLeftActionListener on " + context.getClass() + " to make this work!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_menu_drawer, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View rootView = getView();
        if (rootView != null) {
            rootView.findViewById(R.id.item_drawer_password).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActionListener.onDrawerUpdatePassword();
                }
            });

            rootView.findViewById(R.id.item_drawer_AlterarNome).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActionListener.onDrawerAlterarNome();
                }
            });
            rootView.findViewById(R.id.item_drawer_logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActionListener.onDrawerLogout();
                }
            });
            rootView.findViewById(R.id.item_drawer_ExcluirConta).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActionListener.onDrawerExcluirConta();
                }
            });
        }
    }

    public interface OnDrawerLeftActionListener {
        void onDrawerUpdatePassword();
        void onDrawerAlterarNome();
        void onDrawerLogout();
        void onDrawerExcluirConta();

    }

}
