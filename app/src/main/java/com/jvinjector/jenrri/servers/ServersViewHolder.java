/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.servers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.io.InputStream;

import com.speedlite.vpn.R;


public class ServersViewHolder extends RecyclerView.ViewHolder {
    private TextView servername;
    private TextView serverinfo;
    private ImageView servericon;
    private ImageView serversignal;
    private Context context;
    private ServersModel modelo;

    public ServersViewHolder(@NonNull View view, ServersAdapter.onItemClickListener listener) {
        super(view);
        context = view.getContext();
        servername = view.findViewById(R.id.servers_name);
        serverinfo = view.findViewById(R.id.servers_info);
        servericon = view.findViewById(R.id.servers_image);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(modelo.getServerPosicion());
            }
        });
    }

    public void bind(ServersModel server) {
        modelo = server;
        servername.setText(server.getServerName());
        serverinfo.setText(server.getServerInfo());
        try {
            setImagen(servericon, server.getServerFlag());
        } catch (Exception e) {
            servericon.setImageResource(R.drawable.abc_btn_radio_to_on_mtrl_015);
        }
    }


    public void setImagen(ImageView im, String nameo) throws Exception {
        InputStream inputStream = context.getAssets().open("flags/" + nameo + ".png");
        im.setImageDrawable(Drawable.createFromStream(inputStream, nameo + ".png"));
    }

}
