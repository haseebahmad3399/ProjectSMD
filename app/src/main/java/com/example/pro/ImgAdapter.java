package com.example.pro;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ItemViewHolder> {

    private final List<Status> imagesList;
    private Context context;
    private final RelativeLayout container;

    public ImgAdapter(List<Status> imagesList, RelativeLayout container) {
        this.imagesList = imagesList;
        this.container = container;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.imgadapter_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final Status status = imagesList.get(position);
        Picasso.get().load(status.getFile()).into(holder.imageView);

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager m = context.getPackageManager();
                String s = context.getPackageName();
                PackageInfo p = null;
                try {
                    p = m.getPackageInfo(s, 0);
                    s=p.applicationInfo.dataDir;
                    Common.copyFile(status, context, container, s);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                s = p.applicationInfo.dataDir;

            }
        });

        holder.share.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("image/jpg");
            Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", status.getFile());
            shareIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
            context.startActivity(Intent.createChooser(shareIntent, "Share image"));

        });

//        holder.imageView.setOnClickListener(v -> {
//
//            final AlertDialog.Builder alertD = new AlertDialog.Builder(context);
//            LayoutInflater inflater = LayoutInflater.from(context);
//            //View view = inflater.inflate(R.layout.view_image_full_screen, null);
//            //alertD.setView(view);
//
//            //ImageView imageView = view.findViewById(R.id.img);
//            //Picasso.get().load(status.getFile()).into(imageView);
//
//            AlertDialog alert = alertD.create();
//            alert.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
//            alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            alert.show();
//
//        });

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        public ImageButton save, share;
        public ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivThumbnail);
            save = itemView.findViewById(R.id.save);
            share = itemView.findViewById(R.id.share);
        }
    }
}
