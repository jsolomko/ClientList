package com.example.clientlist.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientlist.R;

import org.w3c.dom.Text;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {
    private List<Client> clientsListArray;
    private AdapterOnItemClicked adapterOnItemClicked;
    private int[] colorArray = {R.drawable.circle_green, R.drawable.circle_reed, R.drawable.circle_blue};

    public DataAdapter(List<Client> clientsListArray, AdapterOnItemClicked adapterOnItemClicked) {
        this.clientsListArray = clientsListArray;
        this.adapterOnItemClicked = adapterOnItemClicked;
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
}
