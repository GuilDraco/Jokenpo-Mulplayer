package com.game.jokenponerd.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.game.jokenponerd.R;
import com.game.jokenponerd.model.Entity.PlacarUsuario;

import java.util.List;


public class PlacarAdapter extends RecyclerView.Adapter<PlacarAdapter.ViewHolder>{

    private final List<PlacarUsuario> informacoesUsuario;

    public PlacarAdapter(List<PlacarUsuario> informacoesUsuario) {
        this.informacoesUsuario = informacoesUsuario;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.placar_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlacarUsuario informacao = informacoesUsuario.get(position);

        holder.nomeUsuario.setText(informacao.getNome_usuario());
        holder.totalVitorias.setText(informacao.getPlacarUsuario());
    }

    @Override
    public int getItemCount() {
        int limit = 5;
        return Math.min(informacoesUsuario.size(), limit);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nomeUsuario;
        TextView totalVitorias;

        ViewHolder(View itemView) {
            super(itemView);

            nomeUsuario = itemView.findViewById(R.id.nomeUsuario);
            totalVitorias = itemView.findViewById(R.id.resultadoUsuario);
        }
    }
}
