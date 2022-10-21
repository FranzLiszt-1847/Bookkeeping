package com.franzliszt.newbookkeeping.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.franzliszt.newbookkeeping.R;
import com.franzliszt.newbookkeeping.sql.Record;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private String[] s_select = {"日用百货","文化休闲","交通出行","生活服务","服装装扮","餐饮美食","数码电器","其他标签"};
    private int[] img_select = {
            R.drawable.icon_type_one,
            R.drawable.icon_type_two,
            R.drawable.icon_type_three,
            R.drawable.icon_type_four,
            R.drawable.icon_type_five,
            R.drawable.icon_type_six,
            R.drawable.icon_type_seven,
            R.drawable.icon_type_eight};
    private List<Record> recordList;
    public OrderAdapter(List<Record> recordList){
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Record record = recordList.get(position);
        holder.item_date.setText(record.getDate());
        holder.item_time.setText("时间 "+record.getTime());
        holder.item_label.setText("["+record.getLabel()+"]");
        holder.item_name.setText(record.getGoodsName());
        if (record.getType() == 1){
            holder.item_price.setText("-"+record.getGoodsPrice());
        }else {
            holder.item_price.setText("+"+record.getGoodsPrice());
        }
        for (int i = 0; i < 8; i++) {
            if (record.getLabel().equals(s_select[i])){
                holder.item_img.setImageResource(img_select[i]);
            }
        }

    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView item_date,item_time,item_label,item_name,item_price;
        private ImageView item_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_date = itemView.findViewById(R.id.item_date);
            item_time = itemView.findViewById(R.id.item_time);
            item_label = itemView.findViewById(R.id.item_label);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            item_img = itemView.findViewById(R.id.item_img);
        }
    }
}
