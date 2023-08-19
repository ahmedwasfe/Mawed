package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.listener.TimeSelectedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookTimeAdapter extends RecyclerView.Adapter<BookTimeAdapter.BookTimeHolder> {

    private Activity activity;
    private List<String> listTimes;
    private LayoutInflater inflater;
    private TimeSelectedListener timeSelectedListener;

    private RelativeLayout itemSelected = null;
    private TextView textItemSelected = null;

    public BookTimeAdapter(Activity activity, List<String> listTimes, TimeSelectedListener timeSelectedListener) {
        this.activity = activity;
        this.listTimes = listTimes;
        this.timeSelectedListener = timeSelectedListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public BookTimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookTimeHolder(inflater.inflate(R.layout.row_book_times, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookTimeHolder holder, int position) {

        holder.tvTime.setText(listTimes.get(position));

        holder.setItemClickListener((view, pos) -> {
            if (itemSelected != null) {
                itemSelected.setBackgroundResource(R.drawable.bg_time_unselected);
                textItemSelected.setTextColor(activity.getColor(R.color.colorMainText));
            }

            holder.relTime.setBackgroundResource(R.drawable.bg_time_selected);
            holder.tvTime.setTextColor(activity.getColor(R.color.colorWhite));

            itemSelected = holder.relTime;
            textItemSelected = holder.tvTime;

            timeSelectedListener.onTimeSelected(listTimes.get(position), position);
        });

    }

    @Override
    public int getItemCount() {
        return listTimes.size();
    }

    static class BookTimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rel_time)
        RelativeLayout relTime;
        @BindView(R.id.tv_time)
        TextView tvTime;

        private ItemClickListener itemClickListener;

        public BookTimeHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
