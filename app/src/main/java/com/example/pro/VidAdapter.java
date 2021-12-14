package com.example.pro;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class VidAdapter extends RecyclerView.Adapter<VidAdapter.ItemViewHolder>{

    private final List<Status> videoList;
    private Context context;
    private final RelativeLayout container;

    public VidAdapter(List<Status> videoList, RelativeLayout container) {
        System.out.println("In constructor of video adapter");
        this.videoList = videoList;
        this.container = container;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("oncreateviewholder");
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.imgadapter_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final Status status = videoList.get(position);
        System.out.println("In Video Adapter");
        Glide.with(context).asBitmap().load(status.getFile()).into(holder.imageView);

        holder.share.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("image/mp4");
            Uri videoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", status.getFile());
            shareIntent.putExtra(Intent.EXTRA_STREAM, videoURI);
            context.startActivity(Intent.createChooser(shareIntent, "Share image"));

        });

        LayoutInflater inflater = LayoutInflater.from(context);
//        final View view1 = inflater.inflate(R.layout.view_video_full_screen, null);
//
//        holder.imageView.setOnClickListener(v -> {
//
//            final AlertDialog.Builder alertDg = new AlertDialog.Builder(context);
//
//            FrameLayout mediaControls = view1.findViewById(R.id.videoViewWrapper);
//
//            if (view1.getParent() != null) {
//                ((ViewGroup) view1.getParent()).removeView(view1);
//            }
//
//            alertDg.setView(view1);
//
//            final VideoView videoView = view1.findViewById(R.id.video_full);
//
//            final MediaController mediaController = new MediaController(context, false);
//
//            videoView.setOnPreparedListener(mp -> {
//
//                mp.start();
//                mediaController.show(0);
//                mp.setLooping(true);
//            });
//
//            videoView.setMediaController(mediaController);
//            mediaController.setMediaPlayer(videoView);
//            videoView.setVideoURI(Uri.fromFile(status.getFile()));
//            videoView.requestFocus();
//
//            ((ViewGroup) mediaController.getParent()).removeView(mediaController);
//
//            if (mediaControls.getParent() != null) {
//                mediaControls.removeView(mediaController);
//            }
//
//            mediaControls.addView(mediaController);
//
//            final AlertDialog alert2 = alertDg.create();
//
//            alert2.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
//            alert2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            alert2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//            alert2.show();
//
//        });

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
            }
        });
    }

//    @Override
//    public void onBindViewHolder(@NonNull ImgAdapter.ItemViewHolder holder, int position) {
//        final Status status = videoList.get(position);
//        System.out.println("In Video Adapter");
//        Glide.with(context).asBitmap().load(status.getFile()).into(holder.imageView);
//
//        holder.share.setOnClickListener(v -> {
//
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//
//            shareIntent.setType("image/mp4");
//            Uri videoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", status.getFile());
//            shareIntent.putExtra(Intent.EXTRA_STREAM, videoURI);
//            context.startActivity(Intent.createChooser(shareIntent, "Share image"));
//
//        });
//
//        LayoutInflater inflater = LayoutInflater.from(context);
////        final View view1 = inflater.inflate(R.layout.view_video_full_screen, null);
////
////        holder.imageView.setOnClickListener(v -> {
////
////            final AlertDialog.Builder alertDg = new AlertDialog.Builder(context);
////
////            FrameLayout mediaControls = view1.findViewById(R.id.videoViewWrapper);
////
////            if (view1.getParent() != null) {
////                ((ViewGroup) view1.getParent()).removeView(view1);
////            }
////
////            alertDg.setView(view1);
////
////            final VideoView videoView = view1.findViewById(R.id.video_full);
////
////            final MediaController mediaController = new MediaController(context, false);
////
////            videoView.setOnPreparedListener(mp -> {
////
////                mp.start();
////                mediaController.show(0);
////                mp.setLooping(true);
////            });
////
////            videoView.setMediaController(mediaController);
////            mediaController.setMediaPlayer(videoView);
////            videoView.setVideoURI(Uri.fromFile(status.getFile()));
////            videoView.requestFocus();
////
////            ((ViewGroup) mediaController.getParent()).removeView(mediaController);
////
////            if (mediaControls.getParent() != null) {
////                mediaControls.removeView(mediaController);
////            }
////
////            mediaControls.addView(mediaController);
////
////            final AlertDialog alert2 = alertDg.create();
////
////            alert2.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
////            alert2.requestWindowFeature(Window.FEATURE_NO_TITLE);
////            alert2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////
////            alert2.show();
////
////        });
//
//        holder.save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PackageManager m = context.getPackageManager();
//                String s = context.getPackageName();
//                PackageInfo p = null;
//                try {
//                    p = m.getPackageInfo(s, 0);
//                    s=p.applicationInfo.dataDir;
//                    Common.copyFile(status, context, container, s);
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return videoList.size();
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
