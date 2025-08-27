/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.servers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.speedlite.vpn.R;


public class ServersAdapter extends RecyclerView.Adapter<ServersViewHolder> {

    private List<ServersModel> servidores;
    private onItemClickListener listener;

    public ServersAdapter(List<ServersModel> model) {
        servidores = model;
    }

    public void setOnItemClick(onItemClickListener mlistener) {
        listener = mlistener;
    }

    @NonNull
    @Override
    public ServersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.servers_item, parent, false);

        return new ServersViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ServersViewHolder holder, int position) {
        ServersModel model = servidores.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return servidores.size();
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }
}
