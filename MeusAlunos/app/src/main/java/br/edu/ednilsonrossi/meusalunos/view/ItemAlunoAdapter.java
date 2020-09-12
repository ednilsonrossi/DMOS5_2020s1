package br.edu.ednilsonrossi.meusalunos.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ednilsonrossi.meusalunos.R;
import br.edu.ednilsonrossi.meusalunos.dao.AlunoDao;
import br.edu.ednilsonrossi.meusalunos.model.Aluno;

public class ItemAlunoAdapter extends RecyclerView.Adapter<ItemAlunoAdapter.AlunoViewHolder> {

    private List<Aluno> mAlunoList;

    public ItemAlunoAdapter(@NonNull List<Aluno> alunos) {
        this.mAlunoList = alunos;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        AlunoViewHolder viewHolder = new AlunoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        holder.nomeAlunoTextView.setText(mAlunoList.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return mAlunoList.size();
    }

    public class AlunoViewHolder extends RecyclerView.ViewHolder{
        public TextView nomeAlunoTextView;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeAlunoTextView = itemView.findViewById(R.id.textview_nome_aluno);
        }
    }
}
