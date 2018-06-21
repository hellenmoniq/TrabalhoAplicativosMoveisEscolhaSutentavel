package com.EscolhaSustentavel.pi.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.model.Usuario;

import java.util.List;



public class MenuPrincipal extends RecyclerView.Adapter<MenuPrincipal.UserViewHolder> {

    private List<Usuario> listUsuarios;

    public MenuPrincipal(List<Usuario> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_menu_principal, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewNome.setText(listUsuarios.get(position).getNome());
        holder.textViewNickname.setText(listUsuarios.get(position).getNickname());
        holder.textViewSenha.setText(listUsuarios.get(position).getSenha());
        holder.textViewPalavraSe.setText(listUsuarios.get(position).getPalavraseg());
    }

    @Override
    public int getItemCount() {
        Log.v(MenuPrincipal.class.getSimpleName(),""+ listUsuarios.size());
        return listUsuarios.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewNome;
        public AppCompatTextView textViewNickname;
        public AppCompatTextView textViewSenha;
        public AppCompatTextView textViewPalavraSe;

        public UserViewHolder(View view) {
            super(view);
            textViewNome = (AppCompatTextView) view.findViewById(R.id.textViewNome);
            textViewNickname = (AppCompatTextView) view.findViewById(R.id.textViewNickname);
            textViewSenha = (AppCompatTextView) view.findViewById(R.id.textViewSenha);
            textViewSenha = (AppCompatTextView) view.findViewById(R.id.textViewSenha);
            textViewPalavraSe=(AppCompatTextView) view.findViewById(R.id.textViewPalavraSe);

        }
    }


}
