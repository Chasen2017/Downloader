package com.example.chasen.downloader.frags.downloaded;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chasen.downloader.R;

/**
 * 已经下载完成的页面
 */
public class DownloadedFragment extends Fragment {


    public DownloadedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_downloaded, container, false);
    }

}
