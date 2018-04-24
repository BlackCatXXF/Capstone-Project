package com.xxf.campussnackshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xxf.campussnackshop.R;
import com.xxf.campussnackshop.bean.Ware;

import java.util.List;

/**
 * Created by xxf on 2018/4/19.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context mContext;
    private List<Ware> mWares;

    public HomeAdapter(Context context, List<Ware> wares) {
        mContext = context;
        mWares = wares;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wares,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ware ware = mWares.get(position);
        holder.textTitle.setText(ware.getName());
        holder.textPrice.setText(ware.getPrice()+"");
        Picasso.with(mContext).load(ware.getImgUrl()).into(holder.wareImage);

    }

    @Override
    public int getItemCount() {
        return mWares.size();
    }

    public void setData(List<Ware> data){
        mWares = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textTitle;
        public TextView textPrice;
        public ImageView wareImage;
        public Button buy;
        public ViewHolder(View itemView) {
            super(itemView);
            textPrice = itemView.findViewById(R.id.text_price);
            textTitle = itemView.findViewById(R.id.text_title);
            wareImage = itemView.findViewById(R.id.ware_image);
            buy = itemView.findViewById(R.id.btn_add);
        }
    }
}
