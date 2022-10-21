package com.franzliszt.newbookkeeping.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.franzliszt.newbookkeeping.R;
import com.franzliszt.newbookkeeping.base.ViewBar;
import com.franzliszt.newbookkeeping.sql.Record;

import java.util.List;

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> {
    private String[] s_select = {"日用百货", "文化休闲", "交通出行", "生活服务", "服装装扮", "餐饮美食", "数码电器", "其他标签"};
    private int[] img_select = {
            R.drawable.icon_type_one,
            R.drawable.icon_type_two,
            R.drawable.icon_type_three,
            R.drawable.icon_type_four,
            R.drawable.icon_type_five,
            R.drawable.icon_type_six,
            R.drawable.icon_type_seven,
            R.drawable.icon_type_eight};
    private List<ViewBar> viewBarList;

    public BarAdapter(List<ViewBar> viewBarList) {
        this.viewBarList = viewBarList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_type_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        holder.item_bar.measure(w, h);
        ViewBar viewBar = viewBarList.get(position);
        holder.item_bar.setLayoutParams(new LinearLayout.LayoutParams(viewBar.getWidth(), viewBar.getHeight()));
        holder.item_label.setText(viewBar.getLabel());
        holder.item_num.setText(viewBar.getNum());
        holder.item_price.setText(viewBar.getPrice());
        Log.d("testLabel",viewBar.getLabel());
        for (int i = 0; i < 8; i++) {
            if (viewBar.getLabel().trim().equals(s_select[i])){
                Log.d("testLabel",viewBar.getLabel());
                holder.item_img.setImageResource(img_select[i]);
            }
        }
    }

    @Override
    public int getItemCount() {
        return viewBarList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView item_label, item_num, item_price;
        private ImageView item_img;
        private View item_bar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_label = itemView.findViewById(R.id.pay_type_label);
            item_num = itemView.findViewById(R.id.pay_type_num);
            item_price = itemView.findViewById(R.id.pay_type_price);
            item_img = itemView.findViewById(R.id.pay_type_img);
            item_bar = itemView.findViewById(R.id.pay_type_bar);
        }
    }
}
