package br.pro.ednilsonrossi.meupocket.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.model.Site;

/**
 * A classe ItemSiteAdapter é responsável por fazer a ligação da fonte de dados
 * com a ListView que irá apresentá-los. É nessa implementação que devemos
 * especificar o comportamento dos elementos da Lista
 */
public class ItemSiteAdapter extends ArrayAdapter {

    //Objeto responsável por "inflar" (criar) um elemento
    //na tela
    private LayoutInflater inflater;

    /**
     * Método construtor responsável por instânciar o adapter. Vale observar que
     * a classe é uma extensão de ArrayAdapter, por isso é necessário utilizar
     * um dos construtores da classe pai.
     * @param context
     * @param siteList
     */
    public ItemSiteAdapter(Context context, List<Site> siteList) {
        super(context, R.layout.item_lista_pocket, siteList);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * O método getView() é invocado quando os ListView é apresentado. É importante observar que
     * o ListView possui os objetos que estão na tela, os demais objetos "escondidos" não fazem
     * parte o ListView, por isso, o método getView é invocado sempre que a lista é rolada,
     * trocando os elementos que estão visíveis.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;


        if (convertView == null) {
             /*
            O objeto convertView será nulo quando ainda não foi instânciado, ou seja, em nosso exemplo
            é invocado quando o site ainda não está na ListView, dessa forma, é preciso instância o
            objeto (inflar um novo item_lista_pocket) e obter as referências de cada elemento de layout.
             */
            convertView = inflater.inflate(R.layout.item_lista_pocket, null);
            holder = new ViewHolder();
            holder.tituloTextView = convertView.findViewById(R.id.text_titulo);
            holder.enderecoTextView = convertView.findViewById(R.id.text_endereco);
            holder.favoritoImageView = convertView.findViewById(R.id.image_favorito);

            /*
            Uma View possui um atributo do tipo Object denominado tag no qual podemos guardar
            qualquer objeto instânciado. Em nosso exemplo, como as referências dos elementos
            visuais da lista estão em um holder, vamos armazenar esse objeto na tag para que
            posteriormente ele seja recuparado.
             */
            convertView.setTag(holder);
        } else {
            /*
            Esse else indicada que o contentView já existe, assim precisamos ajustar nosso objeto
            holder para o holder que foi armazenado na tag da View, sendo necessário apenas recuperar
            a tag.
             */
            holder = (ViewHolder) convertView.getTag();
        }

        /*
        Nossa figura de favorito deve ser clicável, por isso, iremos definir que existe um
        listener para esse objeto. Aqui vamos implementar um onClick() que executa a troca
        da imagem do favorito.
         */
        holder.favoritoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == holder.favoritoImageView) {
                    onEstrelaClique(position);
                }
            }
        });

        /*
        É importante lembrar que estamos implementando uma classe que estende um ArrayAdapter,
        ou seja, possui todas as características da classe pai.
        O método getView() recebe como argumento a posição do elemento da fonte de dados que
        precisa ser apresentado, lembrando que a fonte de dados foi inserida no objeto pai pelo
        método construtor.
        Aqui vamos recuperar o o objeto (Site) que está na fonte de dados utilizando o método
        getItem() do ArrayAdapter. Com o objeto recuperado vamos inserir os dados dele nos
        elementos de layout que possuímos e estão com a referência dentro do holder. Atenção
        para a imagem, que recuperamos uma imagem de acordo com a preferência do usuário
        indicada no objeto Site.
         */
        Site obj = (Site) getItem(position);
        holder.tituloTextView.setText(obj.getTitulo());
        holder.enderecoTextView.setText(obj.getEndereco());
        if (obj.isFavorito())
            holder.favoritoImageView.setImageResource(R.drawable.ic_favorito);
        else
            holder.favoritoImageView.setImageResource(R.drawable.ic_nao_favorito);


        /*
        Depois de tudo pronto basta devolver o contentView para que seja apresentado pelo
        ListView
         */
        return convertView;
    }

    /**
     * O método onEstrelaClique() é chamado quando um ImageView é clicado, assim, recuperamos
     * o objeto da fonte de dados e atualizamos a preferência do usuário no objeto.
     * @param position
     */
    private void onEstrelaClique(int position) {
        Site site = (Site) getItem(position);
        if (site.isFavorito())
            site.undoFavorite();
        else
            site.doFavotite();

        /*
        Aqui uma chamada muito importante e muito bacana, ao atualizar uma propriedade de nosso objeto
        Site, alteramos a fonte de dados, um atributo que seja, contudo o ListView não sabe disso.
        Precisamos notificar que houve uma atualização nos dados a partir no método notifyDataSetChanged()
        que o Adapter se encarregará de atualizar a lista com os novos dadas.
         */
        notifyDataSetChanged();
    }

    /*
    A classe ViewHolder é uma inner class, ou seja, uma classe interna. Ela consegue "enchergar" tudo
    da classe ItemSiteAdapter. Vamos utilizar essa classe para armazenar as referências dos elementos
    de layout que estão no layout item_lista_pocket.
     */
    private static class ViewHolder {
        public TextView tituloTextView;
        public TextView enderecoTextView;
        public ImageView favoritoImageView;
    }

}
