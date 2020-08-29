package br.pro.ednilsonrossi.mypocket2.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.pro.ednilsonrossi.mypocket2.R;
import br.pro.ednilsonrossi.mypocket2.dao.SiteDao;
import br.pro.ednilsonrossi.mypocket2.model.Site;

public class ItemSiteAdapter extends RecyclerView.Adapter<ItemSiteAdapter.SitesViewHolder> {

    private SiteDao mSiteDao;
    private List<Site> mSiteList;
    private static RecyclerItemClickListener mClickListener;

    public ItemSiteAdapter(List<Site> mSiteList, Context context) {
        this.mSiteList = mSiteList;
        mSiteDao = new SiteDao(context);
    }

    public void setClickListener(RecyclerItemClickListener clickListener) {
        ItemSiteAdapter.mClickListener = clickListener;
    }

    @NonNull
    @Override
    public SitesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_pocket, parent, false);
        SitesViewHolder viewHolder = new SitesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SitesViewHolder holder, final int position) {
        holder.tituloTextView.setText(mSiteList.get(position).getTitulo());
        holder.enderecoTextView.setText(mSiteList.get(position).getEndereco());
        if(mSiteList.get(position).isFavorito())
            holder.favoritoImageView.setImageResource(R.drawable.ic_favorito);
        else
            holder.favoritoImageView.setImageResource(R.drawable.ic_nao_favorito);

        /*
        Aqui tratamos o clique na imnagem, observe que o ImageView é um elemento do item do RecyclerView,
        assim, tratamos o onClickListener normalmente.
         */
        holder.favoritoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEstrelaClique(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSiteList.size();
    }

    /*
            Configuramos se o objeto é ou não favorito
             */
    private void onEstrelaClique(int position) {

        if (mSiteList.get(position).isFavorito())
            mSiteList.get(position).undoFavorite();
        else
            mSiteList.get(position).doFavotite();
        mSiteDao.atualiza(mSiteList.get(position));
        notifyDataSetChanged();
    }

    public class SitesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Os atributos disponíveis no layout
        public TextView tituloTextView;
        public TextView enderecoTextView;
        public ImageView favoritoImageView;


        /*
        O Construtor recupera os elementos de layout
         */
        public SitesViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.text_titulo);
            enderecoTextView = itemView.findViewById(R.id.text_endereco);
            favoritoImageView = itemView.findViewById(R.id.image_favorito);
            itemView.setOnClickListener(this);
        }

        /*
        Aqui tratamos o clique no item e não em elementos do item.
         */
        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(getAdapterPosition());
        }

    }
}
