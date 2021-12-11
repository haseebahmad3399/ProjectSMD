package com.example.pro;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageFragment extends Fragment {
    RecyclerView rv;
    ProgressBar progBar;
    RelativeLayout container;
    SwipeRefreshLayout srl;
    TextView msg;
    List<Status> ls = new ArrayList<>();
    Handler hnd = new Handler();
    private ImgAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.img_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ImgAdapter(ls, container);

        rv = view.findViewById(R.id.recyclerViewImage);
        progBar = view.findViewById(R.id.prgressBarImage);
        container = view.findViewById(R.id.image_container);
        srl = view.findViewById(R.id.swipeRefreshLayout);
        msg = view.findViewById(R.id.messageTextImage);

        srl.setColorSchemeColors(ContextCompat.getColor(requireActivity(), android.R.color.holo_orange_dark)
                , ContextCompat.getColor(requireActivity(), android.R.color.holo_green_dark),
                ContextCompat.getColor(requireActivity(), R.color.white),
                ContextCompat.getColor(requireActivity(), android.R.color.holo_blue_dark));

        srl.setOnRefreshListener(this::extractStatus);

        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), Common.GRID_COUNT));
        //System.out.println("yes it is existed");
        extractStatus();


    }

    private void extractStatus() {

        if (Common.STATUS_DIRECTORY_NEW.exists()) {
            System.out.println("yes it is existed-1");

            retreiveFromDirectory(Common.STATUS_DIRECTORY_NEW);

        } else if (Common.STATUS_DIRECTORY.exists()) {
            System.out.println("yes it is existed-2");

            retreiveFromDirectory(Common.STATUS_DIRECTORY);

        } else {
            msg.setVisibility(View.VISIBLE);
            msg.setText("Can't Find WhatsApp Status Directory");
            Toast.makeText(getActivity(), "Can't Find WhatsApp Status Directory", Toast.LENGTH_SHORT).show();
            srl.setRefreshing(false);
        }

    }

    private void retreiveFromDirectory(File wAFolder) {
        //new Thread(() -> {
            File[] statusFiles;
            statusFiles = wAFolder.listFiles();
            ls.clear();
            //System.out.println(statusFiles.length);

            if (statusFiles != null && statusFiles.length > 0) {
                System.out.println("Files existed at "+ wAFolder.toString());
                Arrays.sort(statusFiles);
                for (File file : statusFiles) {
                    Status status = new Status(file, file.getName(), file.getAbsolutePath());

                    if (!status.isVideo() && status.getTitle().endsWith(".jpg")) {
                        ls.add(status);
                    }

                }

                hnd.post(() -> {

                    if (ls.size() <= 0) {
                        msg.setVisibility(View.VISIBLE);
                        msg.setText("No Files Found!!!");
                    } else {
                        msg.setVisibility(View.GONE);
                        msg.setText("");
                    }

                    adapter = new ImgAdapter(ls, container);
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progBar.setVisibility(View.GONE);
                });

            } else {

                hnd.post(() -> {
                    progBar.setVisibility(View.GONE);
                    msg.setVisibility(View.VISIBLE);
                    msg.setText("No Files Found!!!!");
                    Toast.makeText(getActivity(), "No files found", Toast.LENGTH_SHORT).show();
                });

           }
            srl.setRefreshing(false);
    }

}
