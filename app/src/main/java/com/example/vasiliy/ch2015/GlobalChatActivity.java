package com.example.vasiliy.ch2015;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class GlobalChatActivity extends AppCompatActivity implements View.OnClickListener {

    String[] temp = {
            "обязательно надо сохранить в данных о товаре то, что он теперь в корзине.",
            "овых пунктов. Нам остается только заполнить их данными. Это значительно ускоряет работу приложения, т.к. не надо прогонять inflate лишний раз.",
            "Тут важно понимать, что без этого обработчика",
            "lyflyfhfgj",
            "cfgjjhvgfj",
            "сделаем кнопку, которая будет отобра",
            "vzxcvcx",
            "лезны, но иногда их возможностей не хватает",
            "ажать содержимое корзины. В настоящем интернет-магазине мы повесили бы на ",
            "Например, при прокрутке списка, часть пунктов уходит за экран и их уже не надо прорисовывать. View из этих «невидимых» пунктов используются для новых пунктов.",
            "Метод getBox проверяет, какие товары отмечены галками и формирует из них коллекц",
            "jklh98yjho98y",
            "hjero89gergo",
            "hjerhg8er",
            "efe3wr3rw3r",
            "ouwehfw8efow3ef"
    };

    String[] names = {
            "Иван",
            "Марья",
            "Петр",
            "Антон",
            "Даша",
            "Борис",
            "Костя",
            "Игорь",
            "Анна",
            "Денис",
            "Андрей"
    };

    List<MyMessage> messages;
    Button btnSend;
    MyUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_chat);

        user = MyUser.getInstance();

        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        ListView lvMessages = (ListView) findViewById(R.id.listView);

        messages = new ArrayList<>();

        //fillDatas();

        AdapterMessageList messAdapter = new AdapterMessageList(this, messages);

        lvMessages.setAdapter(messAdapter);

    }

    private void fillDatas() {
        for (int i = 0; i < names.length; ++i) {
            messages.add(new MyMessage(names[i], temp[i]));
        }
    }

    @Override
    public void onClick(View v) {

    }
}
