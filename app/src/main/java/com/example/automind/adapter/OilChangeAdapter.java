package com.example.media_consumo_ultimo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.media_consumo_ultimo.R;
import com.example.media_consumo_ultimo.model.TrocaOleo;

public class OilChangeAdapter extends ListAdapter<TrocaOleo, OilChangeAdapter.ViewHolder> {

    public OilChangeAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TrocaOleo> DIFF_CALLBACK = new DiffUtil.ItemCallback<TrocaOleo>() {
        @Override
        public boolean areItemsTheSame(@NonNull TrocaOleo oldItem, @NonNull TrocaOleo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TrocaOleo oldItem, @NonNull TrocaOleo newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_troca_oleo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrocaOleo troca = getItem(position);
        holder.textData.setText(troca.getData());
        holder.textOdometro.setText("Odômetro: " + troca.getOdometro());
        holder.textCusto.setText("Custo: R$ " + troca.getCusto());
        holder.textObs.setText(troca.getObservacoes());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textData, textOdometro, textCusto, textObs;

        ViewHolder(View itemView) {
            super(itemView);
            textData = itemView.findViewById(R.id.textData);
            textOdometro = itemView.findViewById(R.id.textOdometro);
            textCusto = itemView.findViewById(R.id.textCusto);
            textObs = itemView.findViewById(R.id.textObs);
        }
    }
}
