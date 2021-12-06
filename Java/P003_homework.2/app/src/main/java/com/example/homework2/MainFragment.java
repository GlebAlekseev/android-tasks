package com.example.homework2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    Button button;
    EditText editText;
    TextView textView;
    BroadcastReceiver broadcastReceiver;
    Context context;

    static final String TEXTVIEW = "textview";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.button);
        editText = view.findViewById(R.id.editTextTextPersonName);
        textView = view.findViewById(R.id.textView);
        context = getContext();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getStringExtra(MainActivity.DATA);
                textView.setText(data);
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(MainActivity.ACTION);
        context.registerReceiver(broadcastReceiver, filter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();
                Intent intent = new Intent(context,MyService.class);
                intent.putExtra(MainActivity.DATA,data);
                context.startService(intent);
            }
        });
        if (savedInstanceState != null){
            String data = savedInstanceState.getString(TEXTVIEW);
            textView.setText(data);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXTVIEW,textView.getText().toString());
        context.unregisterReceiver(broadcastReceiver);
    }
}
