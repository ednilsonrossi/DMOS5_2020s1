package br.edu.ednilsonrossi.meusalunos.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ednilsonrossi.meusalunos.R;
import br.edu.ednilsonrossi.meusalunos.model.Aluno;

public class MatriculaAdapter extends RecyclerView.Adapter<MatriculaAdapter.ViewHolder> {

    private List<Aluno> mAlunoList;
    private static MatriculaAlunoClickListener clickListener;

    public MatriculaAdapter(@NonNull List<Aluno> alunos) {
        this.mAlunoList = alunos;
    }

    public void setClickListener(MatriculaAlunoClickListener clickListener) {
        MatriculaAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MatriculaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aluno_recyclerview, parent, false);
        MatriculaAdapter.ViewHolder viewHolder = new MatriculaAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MatriculaAdapter.ViewHolder holder, int position) {
        holder.nomeAlunoTextView.setText(mAlunoList.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return mAlunoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nomeAlunoTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeAlunoTextView = itemView.findViewById(R.id.textview_nome_aluno);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clickListener != null){
                clickListener.onAlunoClick(getAdapterPosition());
            }
        }
    }
}
