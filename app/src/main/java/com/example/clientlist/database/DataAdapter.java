package com.example.clientlist.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientlist.R;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {
    private List<Client> clientsListArray;
    private AdapterOnItemClicked adapterOnItemClicked;
    private int[] colorArray = {R.drawable.circle_green, R.drawable.circle_reed, R.drawable.circle_blue};
    private Context context;
    private SharedPreferences def_pref;

    public DataAdapter(List<Client> clientsListArray, AdapterOnItemClicked adapterOnItemClicked, Context context) {
        this.clientsListArray = clientsListArray;
        this.adapterOnItemClicked = adapterOnItemClicked;
        this.context = context;
        def_pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolderData(view, adapterOnItemClicked);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        holder.setData(clientsListArray.get(position));

    }

    @Override
    public int getItemCount() {
        return clientsListArray.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        TextView tvSecName;
        TextView tvTel;
        ImageView imImportance;
        ImageView imSpecial;
        private AdapterOnItemClicked adapterOnItemClicked;

        public ViewHolderData(@NonNull View itemView, AdapterOnItemClicked adapterOnItemClicked) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSecName = itemView.findViewById(R.id.tvSecName);
            tvTel = itemView.findViewById(R.id.tvTel);
            imImportance = itemView.findViewById(R.id.imgImportance);
            imSpecial = itemView.findViewById(R.id.imSpecial);
            this.adapterOnItemClicked = adapterOnItemClicked;
            itemView.setOnClickListener(this);
        }

        public void setData(Client client) {
            tvName.setTextColor(Color.parseColor(def_pref.getString(context.getResources().getString(R.string.text_color_name_key), "#000000")));
            //tvSecName.setTextColor(Color.parseColor(def_pref.getString(context.getResources().getString(R.string.text_color_sec_name_key), "#000000")));
            tvName.setText(client.getName());
            tvSecName.setText(client.getSec_name());
            tvTel.setText(client.getName());
            imImportance.setImageResource(colorArray[client.getImportance()]);
            if (client.getSpecial() == 1) imSpecial.setVisibility(View.VISIBLE);

        }

        @Override
        public void onClick(View v) {
            adapterOnItemClicked.onAdapterItemClicked(getAdapterPosition());
        }
    }

    public interface AdapterOnItemClicked {
        void onAdapterItemClicked(int position);
    }

    public void updateAdapter(List<Client> clientList) {
        clientsListArray.clear();
        clientsListArray.addAll(clientList);
        notifyDataSetChanged();
    }
}
