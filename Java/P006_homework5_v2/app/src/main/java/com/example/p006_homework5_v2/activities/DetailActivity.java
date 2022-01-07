package com.example.p006_homework5_v2.activities;

import static com.example.p006_homework5_v2.activities.DetailActivity.STATE.ADD;
import static com.example.p006_homework5_v2.activities.DetailActivity.STATE.EDIT;
import static com.example.p006_homework5_v2.activities.DetailActivity.STATE.VIEW;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p006_homework5_v2.R;
import com.example.p006_homework5_v2.helpers.HelperMethods;
import com.example.p006_homework5_v2.room_db.App;
import com.example.p006_homework5_v2.room_db.Human;

public class DetailActivity extends AppCompatActivity {
    public final static String CURRENT_EMAIl = "current_email";
    public final static String CURRENT_STATE = "current_state";
    private STATE state = VIEW;
    // Up Bar
    private Button btnBack;
    private Button btnEdit;
    private Button btnRemove;
    // Blocks
    private ScrollView scrlvAdding;
    private ScrollView scrlvViewing;
    // VIEW
    private ImageView imageView;
    private TextView fioView;
    private TextView emailView;
    private TextView birthdayView;
    // ADD / EDIT
    private ImageView  imageAdd;
    private EditText lastNameAdd;
    private EditText lastLastNameAdd;
    private EditText nameAdd;
    private Button btnLoadImageAdd;
    private EditText urlImageAdd;
    private Button btnLoadInfoAdd;
    private EditText emailAdd;
    private EditText birthdayAdd;
    private Button btnSaveAdd;
    private Button btnClearAdd;


    public enum STATE{
        VIEW,
        ADD,
        EDIT;
    }
    private View.OnClickListener goBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Завершение активности
            finish();
        }
    };
    private View.OnClickListener goEdit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Показывает окно редактирования
            setViewToState(EDIT);
        }
    };
    private View.OnClickListener goView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Показывает окно просмотра
            setViewToState(VIEW);
        }
    };
    private View.OnClickListener goRemove = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Удаление элемента
            String emailString = emailAdd.getText().toString();
            if (state == VIEW) emailString = emailView.getText().toString();
            AlertDialog dialog = HelperMethods.getAlertDialogBuilder(v.getContext(),emailString).create();
            dialog.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        state = STATE.valueOf(getIntent().getStringExtra(CURRENT_STATE));
        // На случай смены конфигурации
        if (savedInstanceState != null)
            state = STATE.valueOf(savedInstanceState.getString(CURRENT_STATE));
        // Инициализация View элементов
        initAllViews();
        // Up Bar кнопки
        findViewById(R.id.btn_back).setOnClickListener(goBack);
        findViewById(R.id.btn_edit).setOnClickListener(goEdit);
        findViewById(R.id.btn_delete).setOnClickListener(goRemove);
        // Переход в состояние, переданное с помощью интента
        setViewToState(state);
        // Добавление событий к элементам окна редактирования
        setListenersForAdding();
    }
    private Human getHumanFromViews(){
        Human human = new Human();
        human.birthday = birthdayAdd.getText().toString();
        human.email = emailAdd.getText().toString();
        human.url_image = urlImageAdd.getText().toString();
        human.last_last_name = lastLastNameAdd.getText().toString();
        human.last_name = lastNameAdd.getText().toString();
        human.name = nameAdd.getText().toString();
        return human;
    }
    // В зависимости от наличия email в БД меняет интерфейс
    private void checkValidEmail(){
        if (HelperMethods.isEmailValid(emailAdd.getText().toString())){
            emailAdd.setTextColor(getResources().getColor(R.color.design_default_color_error));
            btnLoadInfoAdd.setVisibility(View.VISIBLE);
            btnSaveAdd.setText(getResources().getString(R.string.save));
        }else  {
            emailAdd.setTextColor(getResources().getColor(R.color.black));
            btnLoadInfoAdd.setVisibility(View.INVISIBLE);
            btnSaveAdd.setText(getResources().getString(R.string.add));
        }
    }
    private void initAllViews(){
        // Up Bar
        btnBack = findViewById(R.id.btn_back);
        btnEdit = findViewById(R.id.btn_edit);
        btnRemove = findViewById(R.id.btn_delete);
        // Blocks
        scrlvAdding = findViewById(R.id.scrlv_adding);
        scrlvViewing = findViewById(R.id.scrlv_viewing);
        // VIEW
        imageView = findViewById(R.id.iv_image_view);
        fioView = findViewById(R.id.tv_fio_view);
        emailView = findViewById(R.id.tv_email_view);
        birthdayView = findViewById(R.id.tv_birthday_view);
        // ADD / EDIT
        imageAdd = findViewById(R.id.iv_image_add);
        lastNameAdd = findViewById(R.id.et_lastname_add);
        lastLastNameAdd = findViewById(R.id.et_lastlastname_add);
        nameAdd = findViewById(R.id.et_name_add);
        btnLoadImageAdd = findViewById(R.id.btn_load_image_add);
        urlImageAdd = findViewById(R.id.et_url_image_add);
        btnLoadInfoAdd = findViewById(R.id.btn_load_info_add);
        emailAdd = findViewById(R.id.et_email_add);
        birthdayAdd = findViewById(R.id.et_birthday_add);
        btnSaveAdd = findViewById(R.id.btn_save_add);
        btnClearAdd = findViewById(R.id.btn_clear_add);
    }
    private void clearAllViewingContent(){
        imageView.setImageDrawable(null);
        imageView.setContentDescription(null);
        fioView.setText("");
        emailView.setText("");
        birthdayView.setText("");
    }
    private void clearAllAddingContent(){
        imageAdd.setImageDrawable(null);
        imageAdd.setContentDescription(null);
        lastNameAdd.setText("");
        lastLastNameAdd.setText("");
        nameAdd.setText("");
        urlImageAdd.setText("");
        emailAdd.setText("");
        birthdayAdd.setText("");
    }
    private void setDataToViewing(Human human){
        fioView.setText(human.last_name + " " + human.name + " " + human.last_last_name);
        emailView.setText(human.email);
        birthdayView.setText(human.birthday);
        HelperMethods.loadImageFromLinkToView(getApplicationContext(),human.url_image,imageView, HelperMethods.SIZE_IMAGE.DEFAULT_900x900);
    }
    private void setDataToAdding(Human human){
        lastNameAdd.setText(human.last_name);
        lastLastNameAdd.setText(human.last_last_name);
        nameAdd.setText(human.name);
        urlImageAdd.setText(human.url_image);
        emailAdd.setText(human.email);
        birthdayAdd.setText(human.birthday);
        checkValidEmail();
        HelperMethods.loadImageFromLinkToView(getApplicationContext(),human.url_image,imageAdd, HelperMethods.SIZE_IMAGE.DEFAULT_900x900);
    }
    private void setListenersForAdding(){
        btnClearAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllAddingContent();
            }
        });
        btnLoadImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.loadImageFromLinkToView(v.getContext(),urlImageAdd.getText().toString(),imageAdd, HelperMethods.SIZE_IMAGE.DEFAULT_900x900);
            }
        });
        btnLoadInfoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Human human = App.getInstance().getAppDataBase().humanDao().getByEmail(emailAdd.getText().toString());
                HelperMethods.setDataToViews(human,v.getContext(),nameAdd,lastNameAdd,lastLastNameAdd,emailAdd,birthdayAdd,urlImageAdd,imageAdd);
            }
        });
        emailAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkValidEmail();
                if (emailAdd.getText().toString().length() <= 6) btnSaveAdd.setEnabled(false); else btnSaveAdd.setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnSaveAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Human human = getHumanFromViews();
                // Добавление собранного объекта в БД
                App.getInstance().getAppDataBase().humanDao().insert(human);
                setResult(MainActivity.RESULT_REFRESH);
                finish();
            }
        });
    }
    // Меняет состояние интерфейса в зависимости от входного аргумента
    private void setViewToState(STATE stateNew){
        switch(stateNew){
            case VIEW:
                state = VIEW;
                // Очистить
                clearAllViewingContent();
                scrlvAdding.setVisibility(View.INVISIBLE);
                scrlvViewing.setVisibility(View.VISIBLE);
                // Заполнить данными
                String emailView = getIntent().getStringExtra(CURRENT_EMAIl);
                Human humanView = App.getInstance().getAppDataBase().humanDao().getByEmail(emailView);
                setDataToViewing(humanView);
                // Прочее
                btnEdit.setVisibility(View.VISIBLE);
                btnEdit.setText(getResources().getString(R.string.edit));
                // Изменить событие
                btnEdit.setOnClickListener(goEdit);
                btnRemove.setVisibility(View.VISIBLE);
                break;
            case ADD:
                state = ADD;
                scrlvAdding.setVisibility(View.VISIBLE);
                scrlvViewing.setVisibility(View.INVISIBLE);
                // Кнопка Добавить/Сохранить
                btnSaveAdd.setText(getResources().getString(R.string.add));
                btnEdit.setVisibility(View.INVISIBLE);
                btnRemove.setVisibility(View.INVISIBLE);
                break;
            case EDIT:
                state = EDIT;
                clearAllAddingContent();
                scrlvAdding.setVisibility(View.VISIBLE);
                scrlvViewing.setVisibility(View.INVISIBLE);
                btnEdit.setVisibility(View.VISIBLE);
                btnEdit.setText(getResources().getString(R.string.view));
                // Изменить собыите
                btnEdit.setOnClickListener(goView);
                btnRemove.setVisibility(View.INVISIBLE);
                // Кнопка Добавить/Сохранить
                btnSaveAdd.setText(getResources().getString(R.string.save));
                btnSaveAdd.setEnabled(true);
                // Заполнить данными
                String emailEdit = getIntent().getStringExtra(CURRENT_EMAIl);
                Human humanEdit = App.getInstance().getAppDataBase().humanDao().getByEmail(emailEdit);
                setDataToAdding(humanEdit);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Сохранение последнего состояния
        outState.putString(CURRENT_STATE,state.toString());
    }
}