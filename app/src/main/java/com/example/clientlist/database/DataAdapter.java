package com.example.clientlist.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientlist.R;

import org.w3c.dom.Text;

import java.util.List;

public class DataAdapter  extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {
    public DataAdapter(List<Client> clientsListArray) {
        this.clientsListArray = clientsListArray;
    }

    private List<Client> clientsListArray;
    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolderData(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        holder.setData(clientsListArray.get(position));

    }

    @Override
    public int getItemCount() {
        return clientsListArray.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvImportance;
        TextView tvSecName;
        TextView tvTel;
        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvImportance = itemView.findViewById(R.id.tvImportance);
            tvSecName = itemView.findViewById(R.id.tvSecName);
            tvTel = itemView.findViewById(R.id.tvTel);
        }

        public void setData(Client client) {
            tvName.setText(client.getName());
            tvImportance.setText(String.valueOf(client.getId()));
            tvSecName.setText(client.getSec_name());
            tvTel.setText(client.getName());
        }
    }
}
