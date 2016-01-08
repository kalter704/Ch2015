package com.example.vasiliy.ch2015;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vasiliy on 06.01.16.
 */
public class AdapterMessageList extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    List<MyMessage> messages;

    public AdapterMessageList(Context ctx, List<MyMessage> messages) {
        this.ctx = ctx;
        this.messages = messages;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = lInflater.inflate(R.layout.item_message, parent, false);
        }

        MyMessage message = (MyMessage) getItem(position);

        ((TextView) view.findViewById(R.id.tvWho)).setText(message.getWho());
        ((TextView) view.findViewById(R.id.tvWhat)).setText(message.getWhat());

        return view;
    }
}
