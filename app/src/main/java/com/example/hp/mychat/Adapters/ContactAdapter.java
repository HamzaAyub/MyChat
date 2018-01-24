package com.example.hp.mychat.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.mychat.R;
import com.example.hp.mychat.model.ListItem;

import java.util.List;

/**
 * Created by hp on 7/7/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.DerpHolder> {

    private List<ListItem> listData;
    private LayoutInflater inflater;

    public ContactAdapter(List<ListItem> listdata , Context c){

        this.inflater = LayoutInflater.from(c);
        this.listData = listdata;
    }

    @Override
    public DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.card_view_contacts , parent , false);
        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {
        ListItem item = listData.get(position);
        holder.name.setText(item.getName());
        holder.number.setText(item.getNumber());
        holder.img.setImageBitmap(item.getPhoto());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView number;
        private ImageView img;
        public DerpHolder(View itemView) {
            super(itemView);

            name= (TextView)itemView.findViewById(R.id.idnameContact);
            number = (TextView)itemView.findViewById(R.id.idnumberContact);
            img =(ImageView)itemView.findViewById(R.id.img_id);
        }
    }

}