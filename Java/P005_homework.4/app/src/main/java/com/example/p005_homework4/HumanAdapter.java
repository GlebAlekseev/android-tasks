package com.example.p005_homework4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class HumanAdapter extends RecyclerView.Adapter<HumanAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<Human> listHuman = new ArrayList<>();
    // Инициализация данных
    public HumanAdapter(List<Human> listHuman){
        this.listHuman = listHuman;
    }

    // Возвращает элемент представления
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        onItemClickListener = (MainActivity)parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    // Связывание элемента представления с данными
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Human human = listHuman.get(position);
        if (human != null){
            holder.fioTextView.setText(human.getFio());
            holder.emailTextView.setText(human.getEmail());
            holder.birthdayTextView.setText(human.getBirthday());

            Glide.with(holder.imageView.getContext())
                    .load(human.getUrl())
                    .override(400, 400)
                    .centerCrop()
                    .into(holder.imageView);
        }
    }

    // Количество элементов
    @Override
    public int getItemCount() {
        return listHuman.size();
    }

    // Класс, который представляет элемент представления
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView fioTextView;
        public TextView emailTextView;
        public TextView birthdayTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
            birthdayTextView = itemView.findViewById(R.id.tv_birthday);
            emailTextView = itemView.findViewById(R.id.tv_email);
            fioTextView = itemView.findViewById(R.id.tv_fio);
            // Событие срабатывающее после клика на элемент RecyclerView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), emailTextView.getText(), Toast.LENGTH_SHORT).show();
                    if (onItemClickListener != null){
                        onItemClickListener.OnItemClick(listHuman.get(getLayoutPosition()));
                    }
                }
            });
        }

    }

    // Интерфейс OnItemClickListener
    public interface OnItemClickListener{
        void OnItemClick(Human human);
    }

}
