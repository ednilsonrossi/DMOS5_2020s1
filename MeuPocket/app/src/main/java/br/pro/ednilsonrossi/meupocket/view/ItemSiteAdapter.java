package br.pro.ednilsonrossi.meupocket.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.model.Site;

public class ItemSiteAdapter extends ArrayAdapter{

    private LayoutInflater inflater;


    private void onEstrelaClique(int position) {
        Site site = (Site) getItem(position);
        if(site.isFavorito())
            site.undoFavorite();
        else
            site.doFavotite();

        notifyDataSetChanged();
    }

    static class ViewHolder{
        public TextView tituloTextView;
        public TextView enderecoTextView;
        public ImageView favoritoImageView;
    }

    public ItemSiteAdapter(Context context, List<Site> siteList){
        super(context, R.layout.item_lista_pocket, siteList);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_lista_pocket, null);
            holder = new ViewHolder();
            holder.tituloTextView = convertView.findViewById(R.id.text_titulo);
            holder.enderecoTextView = convertView.findViewById(R.id.text_endereco);
            holder.favoritoImageView = convertView.findViewById(R.id.image_favorito);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.favoritoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == holder.favoritoImageView) {
                    onEstrelaClique(position);
                }
            }
        });

        Site obj = (Site) getItem(position);
        holder.tituloTextView.setText(obj.getTitulo());
        holder.enderecoTextView.setText(obj.getEndereco());
        if(obj.isFavorito())
            holder.favoritoImageView.setImageResource(R.drawable.ic_favorito);
        else
            holder.favoritoImageView.setImageResource(R.drawable.ic_nao_favorito);



        return convertView;
    }



}
