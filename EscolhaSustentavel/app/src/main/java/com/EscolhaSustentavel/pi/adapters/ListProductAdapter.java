package com.EscolhaSustentavel.pi.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.model.Produto;
import com.EscolhaSustentavel.pi.model.Usuario;

import java.util.List;

/**
 * Created by Hellen on 01/05/2017.
 */

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ProductViewHolder> {

    private List<Produto> listProduct;
    private OnClickProdut onClickProdut;

    public ListProductAdapter(List<Produto> listUsuarios, OnClickProdut mOnClick) {
        this.listProduct = listUsuarios;
        this.onClickProdut = mOnClick;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    public void GetProduto(){


    }

    @Override
    public void onBindViewHolder(ListProductAdapter.ProductViewHolder holder, final int position) {
        holder.textViewName.setText(listProduct.get(position).getNameProduct());

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickProdut.onSelectedProduct(listProduct.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+ listProduct.size());
        return listProduct.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;

        public ProductViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.tv_name_product);
        }
    }

    public interface OnClickProdut{
        void onSelectedProduct(Produto prod, int position);
    }

}
