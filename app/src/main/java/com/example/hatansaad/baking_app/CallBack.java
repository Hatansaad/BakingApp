package com.example.hatansaad.baking_app;

import android.content.Context;

public interface CallBack {

    void onClick(Context context, Integer id, String description, String url, int stepPosition);
}
