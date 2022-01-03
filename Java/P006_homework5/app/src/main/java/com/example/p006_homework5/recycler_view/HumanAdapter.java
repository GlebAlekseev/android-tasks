package com.example.p006_homework5.recycler_view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.p006_homework5.R;
import com.example.p006_homework5.activities.MainActivity;
import com.example.p006_homework5.fragments.DownBlockFragment;
import com.example.p006_homework5.fragments.UpBarFragment;
import com.example.p006_homework5.fragments.UpBlockFragment;
import com.example.p006_homework5.helpers.HelperMethods;
import com.example.p006_homework5.room_db.App;
import com.example.p006_homework5.room_db.Human;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class HumanAdapter extends RecyclerView.Adapter<HumanAdapter.ViewHolder> implements MainActivity.RefreshDataViewListener {
    private OnItemClickListener onItemClickListener;
    private List<Human> listHuman;
    private Fragment fragment;
    // Инициализация данных
    public HumanAdapter(Fragment fragment,List<Human> listHuman){
        this.listHuman = listHuman;
        this.fragment = fragment;
    }

    // Возвращает элемент представления
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        onItemClickListener = (DownBlockFragment)fragment;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    // Связывание элемента представления с данными
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Human human = listHuman.get(position);
        if (human != null){
            holder.fioTextView.setText(human.last_name + " " + human.name + " " + human.last_last_name);
            holder.emailTextView.setText(human.email);
            holder.birthdayTextView.setText(human.birthday);
            HelperMethods.loadImageFromLinkToView(holder.imageView.getContext(), human.url_image, holder.imageView, HelperMethods.SIZE_IMAGE.SIZE_400x400);
        }
    }

    // Количество элементов
    @Override
    public int getItemCount() {
        return listHuman.size();
    }

    // Реализация методов интерфейса MainActivity.RefreshDataViewListener
    @Override
    public void Refresh() {
        // Изменить данные в адаптере RecyclerView
        listHuman = App.getInstance().getAppDataBase().humanDao().getAll();
        // Сообщить об изменении в адаптере
        notifyDataSetChanged();
        // Обновить кол-во элементов
        RefreshCount();
    }
    // Обновляет инетрфейс
    @Override
    public void RefreshCount() {
        UpBarFragment upBarFragment = (UpBarFragment)fragment.getParentFragmentManager().findFragmentById(R.id.fragment_up_bar);
        TextView textView = upBarFragment.getView().findViewById(R.id.tv_count);
        textView.setText("Контактов: " + String.valueOf(App.getInstance().getAppDataBase().humanDao().getAll().size()));
    }

    // Класс, который представляет элемент представления
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView fioTextView;
        public TextView emailTextView;
        public TextView birthdayTextView;
        public static final int POPUPMENU_EDIT = 1;
        public static final int POPUPMENU_DELETE = 2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);

            // Событие долгого клика по нажатию на элемент RecyclerView
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Создание попап-меню
                    PopupMenu popupMenu =new PopupMenu(v.getContext(),v);
                    PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
                        private EditText lastLastName;
                        private EditText lastName;
                        private EditText name;
                        private EditText email;
                        private EditText birthday;
                        private EditText urlImage;
                        private ImageView image;
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch  (item.getItemId()) {
                                case POPUPMENU_EDIT:
                                    UpBlockFragment upBlockFragment = (UpBlockFragment)fragment.getParentFragmentManager().findFragmentById(R.id.fragment_up_block);
                                    View upBlockView = upBlockFragment.getView();
                                    initViews(upBlockView);
                                    Human human = listHuman.get(getLayoutPosition());
                                    // Установить данные в поля ввода
                                    HelperMethods.setDataToViews(human,v.getContext(),name,lastName,lastLastName,email,birthday,urlImage,image);
                                    HelperMethods.displayUpBlock(fragment);
                                    break;
                                case POPUPMENU_DELETE:
                                    // Отобразить Диалоговое окно
                                    AlertDialog dialog = getAlertDialogBuilder().create();
                                    dialog.show();
                                    break;
                            }
                            return true;
                        }

                        private AlertDialog.Builder getAlertDialogBuilder(){
                            AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
                            builder.setMessage("Вы точно хотите удалить данного пользователя?");
                            builder.setCancelable(true);
                            builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    App.getInstance().getAppDataBase().humanDao().delete(emailTextView.getText().toString());
                                    Refresh();
                                }
                            });
                            builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            return builder;
                        }
                        private void initViews(View view){
                            lastLastName = view.findViewById(R.id.et_lastlastname_add);
                            lastName = view.findViewById(R.id.et_lastname_add);
                            name = view.findViewById(R.id.et_name_add);
                            email = view.findViewById(R.id.et_email_add);
                            birthday = view.findViewById(R.id.et_birthday_add);
                            urlImage = view.findViewById(R.id.et_url_image_add);
                            image = view.findViewById(R.id.iv_image_add);
                        }
                    };
                    // Наполнение попап-меню
                    popupMenu.getMenu().add(1,POPUPMENU_EDIT,1,"Редактировать");
                    popupMenu.getMenu().add(1,POPUPMENU_DELETE,1,"Удалить");
                    popupMenu.setOnMenuItemClickListener(listener);
                    popupMenu.show();
                    return false;
                }

            });

            // Событие срабатывающее после клика на элемент RecyclerView, открывает DetailActivity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.OnItemClick(listHuman.get(getLayoutPosition()));
                    }

                }
            });
        }
        // Инициализация элементов интерфейса
        private void initViews(View itemView){
            imageView = itemView.findViewById(R.id.iv_image);
            birthdayTextView = itemView.findViewById(R.id.tv_birthday);
            emailTextView = itemView.findViewById(R.id.tv_email);
            fioTextView = itemView.findViewById(R.id.tv_fio);
        }

    }

    // Интерфейс OnItemClickListener
    public interface OnItemClickListener{
        void OnItemClick(Human human);
    }


}
