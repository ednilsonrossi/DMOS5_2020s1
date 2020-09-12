package br.edu.ednilsonrossi.meusalunos.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ednilsonrossi.meusalunos.R;
import br.edu.ednilsonrossi.meusalunos.model.Disciplina;

public class DisciplinaAdapter extends RecyclerView.Adapter<DisciplinaAdapter.DisciplinaViewHolder>{

    private List<Disciplina> mDisciplinaList;
    private static  DisciplinaClickListener clickListener;

    public DisciplinaAdapter(List<Disciplina> disciplinas) {
        this.mDisciplinaList = disciplinas;
    }

    public void setClickListener(DisciplinaClickListener clickListener) {
        DisciplinaAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public DisciplinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disciplina_recyclerview, parent, false);
        DisciplinaViewHolder viewHolder = new DisciplinaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DisciplinaViewHolder holder, int position) {
        holder.siglaTextView.setText(mDisciplinaList.get(position).getSigla());
    }

    @Override
    public int getItemCount() {
        return mDisciplinaList.size();
    }

    public class DisciplinaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView siglaTextView;

        public DisciplinaViewHolder(@NonNull View itemView) {
            super(itemView);
            siglaTextView = itemView.findViewById(R.id.textview_nome_disciplina);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clickListener != null){
                clickListener.onDisciplinaClick(getAdapterPosition());
            }
        }
    }
}
