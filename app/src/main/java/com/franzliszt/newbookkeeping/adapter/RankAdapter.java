package com.franzliszt.newbookkeeping.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.franzliszt.newbookkeeping.R;
import com.franzliszt.newbookkeeping.base.RankList;
import com.franzliszt.newbookkeeping.sql.Record;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private int[] img_select = {
            R.drawable.gold,
            R.drawable.silver,
            R.drawable.tongpai,
           };
    private List<RankList> rankLists;
    public RankAdapter(List<RankList> rankLists){
        this.rankLists = rankLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RankList rank = rankLists.get(position);
        holder.item_label.setText("["+rank.getLabel()+"]");
        holder.item_content.setText(rank.getContent());
        if (rank.getType() == 1){
            holder.item_price.setText("-"+rank.getPrice());
        }else {
            holder.item_price.setText("+"+rank.getPrice());
        }
        switch (rank.getPosition()){
            case 1:
                holder.item_img.setImageResource(img_select[0]);
                break;
            case 2:
                holder.item_img.setImageResource(img_select[1]);
                break;
            case 3:
                holder.item_img.setImageResource(img_select[2]);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return rankLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView item_label,item_content,item_price;
        private ImageView item_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_label = itemView.findViewById(R.id.Rank_label);
            item_content = itemView.findViewById(R.id.Rank_name);
            item_price = itemView.findViewById(R.id.Rank_price);
            item_img = itemView.findViewById(R.id.Rank_img);
        }
    }
}
