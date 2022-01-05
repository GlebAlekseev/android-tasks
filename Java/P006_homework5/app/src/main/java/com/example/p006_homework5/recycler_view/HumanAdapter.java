package com.example.p006_homework5.recycler_view;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p006_homework5.R;
import com.example.p006_homework5.listeners.RefreshDataListListener;
import com.example.p006_homework5.activities.DetailActivity;
import com.example.p006_homework5.activities.MainActivity;
import com.example.p006_homework5.fragments.DownBlockFragment;
import com.example.p006_homework5.fragments.UpBlockFragment;
import com.example.p006_homework5.helpers.HelperMethods;
import com.example.p006_homework5.room_db.App;
import com.example.p006_homework5.room_db.Human;

import java.util.List;


public class HumanAdapter extends RecyclerView.Adapter<HumanAdapter.ViewHolder> implements RefreshDataListListener {
    private List<Human> listHuman;
    // Инициализация данных
    public HumanAdapter(List<Human> listHuman){
        this.listHuman = listHuman;
    }

    // Возвращает элемент представления
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       /* onItemClickListener = (DownBlockFragment)fragment;*/
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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    // Количество элементов
    @Override
    public int getItemCount() {
        return listHuman.size();
    }

    @Override
    public void refreshListData() {
        listHuman = App.getInstance().getAppDataBase().humanDao().getAll();
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
                            FragmentManager fragmentManager =((MainActivity)itemView.getContext()).getSupportFragmentManager();
                            switch  (item.getItemId()) {
                                case POPUPMENU_EDIT:
                                    UpBlockFragment upBlockFragment = (UpBlockFragment)fragmentManager.findFragmentById(R.id.fragment_up_block);
                                    View upBlockView = upBlockFragment.getView();
                                    initViews(upBlockView);
                                    Human human = listHuman.get(getLayoutPosition());
                                    // Установить данные в поля ввода
                                    HelperMethods.setDataToViews(human,v.getContext(),name,lastName,lastLastName,email,birthday,urlImage,image);
                                    HelperMethods.displayUpBlock(fragmentManager);

                                    break;
                                case POPUPMENU_DELETE:
                                    // Отобразить Диалоговое окно
                                    AlertDialog dialog = getAlertDialogBuilder(fragmentManager).create();
                                    dialog.show();
                                    break;
                            }
                            return true;
                        }

                        private AlertDialog.Builder getAlertDialogBuilder(FragmentManager fragmentManager){
                            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                            builder.setMessage(itemView.getResources().getString(R.string.confirmation));
                            builder.setCancelable(true);
                            builder.setPositiveButton(itemView.getResources().getString(R.string.remove), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    App.getInstance().getAppDataBase().humanDao().delete(emailTextView.getText().toString());

                                    DownBlockFragment downBlockFragment = (DownBlockFragment) fragmentManager.findFragmentById(R.id.fragment_down_block);
                                    downBlockFragment.refresh();
                                }
                            });
                            builder.setNegativeButton(itemView.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
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
                    popupMenu.getMenu().add(1,POPUPMENU_EDIT,1,itemView.getResources().getString(R.string.edit));
                    popupMenu.getMenu().add(1,POPUPMENU_DELETE,2,itemView.getResources().getString(R.string.remove));
                    popupMenu.setOnMenuItemClickListener(listener);
                    popupMenu.show();
                    return false;
                }

            });

            // Событие срабатывающее после клика на элемент RecyclerView, открывает DetailActivity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Human human = listHuman.get(getLayoutPosition());
                    Context context =  itemView.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.CURRENT_EMAIl,human.email);
                    context.startActivity(intent);
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

}
