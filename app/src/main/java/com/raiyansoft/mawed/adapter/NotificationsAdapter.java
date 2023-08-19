package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.listener.ItemClickListener;
import com.raiyansoft.mawed.listener.NotificationReadListener;
import com.raiyansoft.mawed.model.notifications.NotificationData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsHolder> {
    private static final String TAG = NotificationsAdapter.class.getSimpleName();

    private Activity activity;
    private List<NotificationData> listNotifications;
    private LayoutInflater inflater;

    private NotificationReadListener notificationReadListener;

    public NotificationsAdapter(Activity activity, List<NotificationData> listNotifications, NotificationReadListener notificationReadListener) {
        this.activity = activity;
        this.listNotifications = listNotifications;
        this.notificationReadListener = notificationReadListener;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public NotificationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationsHolder(inflater.inflate(R.layout.row_notifications, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsHolder holder, int position) {

        NotificationData notifications = listNotifications.get(position);

        holder.tvTitle.setText(notifications.getTitle());
        holder.tvContent.setText(notifications.getMessage());
        Typeface fontBold = null, fontRegular = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             fontBold = activity.getResources().getFont(R.font.montserrat_bold);
            fontRegular = activity.getResources().getFont(R.font.montserrat_regular);
        }

        if (notifications.getRead() == 1) {
            holder.tvTitle.setTypeface(fontRegular);
            holder.tvContent.setTypeface(fontRegular);
            holder.relNotifications.setBackgroundResource(R.drawable.bg_notification_read);
            holder.civRead.setBackgroundResource(R.drawable.bg_read);
        }else{
            holder.tvTitle.setTypeface(fontBold);
            holder.tvContent.setTypeface(fontBold);
            holder.relNotifications.setBackgroundResource(R.drawable.bg_notification_unread);
            holder.civRead.setBackgroundResource(R.drawable.bg_unread);
        }

        holder.setItemClickListener((view, pos) -> {
            notificationReadListener.onReadNotification(notifications, position);
        });
    }

    @Override
    public int getItemCount() {
        return listNotifications.size();
    }

    static class NotificationsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rel_notification)
        RelativeLayout relNotifications;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.civ_read)
        CircleImageView civRead;

        private ItemClickListener itemClickListener;

        public NotificationsHolder(@NonNull View itemView) {
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
