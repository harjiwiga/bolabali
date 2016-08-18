package com.example.harjiwigaasmoko.irabukatoko.customs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.harjiwigaasmoko.irabukatoko.R;
import com.example.harjiwigaasmoko.irabukatoko.entity.User;

import java.util.List;

/**
 * Created by harjiwigaasmoko on 8/17/16.
 */
public class UserAdapter extends ArrayAdapter<User>{

    List<User> users = null;
    Context context;
    int layoutResourceId;

    public UserAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        users = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View row = convertView;
        UserHolder holder = null;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        if(row==null){
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.name = (TextView)row.findViewById(R.id.textView2);
            holder.checkBox = (CheckBox)row.findViewById(R.id.checkBox);
            holder.checkBox.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    User user = (User) cb.getTag();
                    user.setSelected(cb.isChecked());
                }
            });
            row.setTag(holder);
        }else{
            holder = (UserHolder)row.getTag();
        }

        User user = users.get(position);
        holder.name.setText(user.getName());
        holder.checkBox.setSelected(user.isSelected());
        holder.checkBox.setTag(user);
        return row;
    }

    static class UserHolder
    {
        TextView name;
        CheckBox checkBox;
    }

}
