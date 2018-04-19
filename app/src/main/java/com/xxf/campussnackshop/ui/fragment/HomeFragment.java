package com.xxf.campussnackshop.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxf.campussnackshop.R;
import com.xxf.campussnackshop.bean.Banner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xxf on 2018/4/18.
 */

public class HomeFragment extends Fragment {

    private SliderLayout mSliderLayout;
    private RecyclerView mRecyclerView;

    private List<Banner> mBanners = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        mSliderLayout = view.findViewById(R.id.slider);
        mRecyclerView = view.findViewById(R.id.recyclerview);


        String banner ="http://112.124.22.238:8081/course_api/banner/query?type=1";
        new FetchTask().execute(banner);

        return view;
    }

    private String fetchData(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        String json = null;
        try {
            response = client.newCall(request).execute();
            json = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;

    }

    private class FetchTask extends AsyncTask<String, Integer, Boolean> {

        String jsonResponse = null;
        @Override
        protected Boolean doInBackground(String... urls) {
                jsonResponse = fetchData(urls[0]);
            if (mBanners != null) {
                mBanners.clear();
            }
            parseJsonWithGson(jsonResponse);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            initSlider();
        }
    }

    private void parseJsonWithGson(String json){
        Gson gson = new Gson();
        mBanners = gson.fromJson(json, new TypeToken<List<Banner>>(){}.getType());
    }

    private void initSlider(){




        if(mBanners !=null){

            for (Banner banner : mBanners){


                TextSliderView textSliderView = new TextSliderView(this.getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);

            }
        }



        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);




    }

}
