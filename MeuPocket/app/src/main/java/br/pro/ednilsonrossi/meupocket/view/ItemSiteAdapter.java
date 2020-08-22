package br.pro.ednilsonrossi.meupocket.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.model.Site;

/**
 * Agora nosso Adapter é uma extensão do Adapter fornecido pelo pacote
 * RecyclerView, ele necessita de alguns ajustes adicionais se comparado
 * ao ArrayAdapter que implementamos na versão anterior.
 */
public class ItemSiteAdapter extends RecyclerView.Adapter<ItemSiteAdapter.SitesViewHolder> {

    //A fonte de dados está em nossa implementação
    private List<Site> siteList;

    //Aqui implementamos um objeto que será responsável pelo tratamento do clique
    //no item de nosso RecyclerView
    private static RecyclerItemClickListener clickListener;

    //Construtor
    public ItemSiteAdapter(List<Site> siteList) {
        this.siteList = siteList;
    }

    //Método setClickListener receberá a implamentação do clique no elemento da lista.
    //Essa implementação é forncedida MainActivity, por exemplo.
    public void setClickListener(RecyclerItemClickListener clickListener) {
        ItemSiteAdapter.clickListener = clickListener;
    }

    /*
    Esse método precisa ser sobrescrito para que o tratamento adequado dos itens da RecyclerView
    seja realizado. Aqui configuraremos qual o layout que será utilizado e inflaremos esse layout.
    Tudo aqui é definido no ViewHolder.
     */
    @NonNull
    @Override
    public SitesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_pocket, parent, false);
        SitesViewHolder viewHolder = new SitesViewHolder(view);
        return viewHolder;
    }


    /*
    Esse método é chamado sempre que um item do RecyclerView é apresentado, assim ele é responsável
    por reciclar os elementos de layout e configurar o que será apresentado na tela do aplicativo.
     */
    @Override
    public void onBindViewHolder(@NonNull SitesViewHolder holder, final int position) {
        holder.tituloTextView.setText(siteList.get(position).getTitulo());
        holder.enderecoTextView.setText(siteList.get(position).getTitulo());
        if(siteList.get(position).isFavorito())
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


    /*
    Como a fonte de dados não está na classe pai nosso adapter é quem deve devolver a quantidade
    de elementos. Esse método é que define o tamanho de nossa RecyclerView e é consultado sempre
    que a lista é rolada. Definir um valor inválido aqui pode causar falhas graves em nosso app.
     */
    @Override
    public int getItemCount() {
        return siteList.size();
    }


    /*
    A inner class SitesViewHolder continua seguindo o mesmo padrão de projeto, contudo agora
    ela deve estender a classe ViewHolder do pacote RecyclerView.
     */
    /*
    Como queremos tratar o clique no item da RecyclerView temos que implementar o OnClickListener
    do pacote View.
     */
    public static class SitesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }

    /*
    Configuramos se o objeto é ou não favorito
     */
    private void onEstrelaClique(int position) {
        if (siteList.get(position).isFavorito())
            siteList.get(position).undoFavorite();
        else
            siteList.get(position).doFavotite();
        notifyDataSetChanged();
    }

}
