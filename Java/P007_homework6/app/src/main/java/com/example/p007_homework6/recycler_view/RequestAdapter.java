package com.example.p007_homework6.recycler_view;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p007_homework6.R;
import com.example.p007_homework6.activities.MainActivity;
import com.example.p007_homework6.listeners.RefreshDataListListener;
import com.example.p007_homework6.listeners.ShowCityNameListener;
import com.example.p007_homework6.retrofit.gson_model.Main;
import com.example.p007_homework6.room_db.App;
import com.example.p007_homework6.room_db.Request;
import com.example.p007_homework6.room_db.RequestDao;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> implements RefreshDataListListener {
    private ShowCityNameListener showCityNameListener;
    private List<Request> requestList;

    public RequestAdapter(List<Request> requestList){
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request request = requestList.get(position);
        if (request != null){
            holder.tvCityName.setText(request.name);
            if (request.status) holder.llBack.setBackgroundResource(R.color.good);
            if (!request.status) holder.llBack.setBackgroundResource(R.color.bad);
            Date date = new Date(request.time_created);
            Format format = new SimpleDateFormat(holder.itemView.getContext().getResources().getString(R.string.time_pattern_HHmmss));
            holder.tvTimeCreated.setText(format.format(date));
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    @Override
    public void refresh(List<Request> requestList) {
        this.requestList = requestList;
        notifyDataSetChanged();
    }
    public void registerShowCityNameListener(ShowCityNameListener showCityNameListener){
        this.showCityNameListener = showCityNameListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvCityName;
        public TextView tvTimeCreated;
        public LinearLayout llBack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tvCityName);
            tvTimeCreated = itemView.findViewById(R.id.tvTimeCreated);
            llBack = itemView.findViewById(R.id.llBack);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Request request = requestList.get(getLayoutPosition());
                    showCityNameListener.showCityName(request.name);
                }
            });
        }
    }

}
