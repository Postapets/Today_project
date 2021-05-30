package com.example.today_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.today_project.storage.Item;
import com.example.today_project.storage.Store;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

//класс адаптера, куда добавляем все данные для внесения во viewholder
public final class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private final Context context;
    private List<Item> itemList;

    public ItemAdapter(Context context){
        this.context = context;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    //при создании нового объекта viewholder добавляем массив items в него
    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int index) {

        View view = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        return new MyViewHolder(view);

//        return new RecyclerView.ViewHolder(
//                LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.items, parent, false)
//        ) {};
    }

    //при присоединении viewholder к активности выводим данные внесенные пользователем
    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MyViewHolder holder, int index) {

        //не факт, что здесь надо писать this
        Item item = this.itemList.get(index);

        //вешаем событие на view, которое получили в onCreateViewHolder
        holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int itemId = itemList.get(index).getId();
                        Intent intent = new Intent(context, ItemActivity.class);
                        intent.putExtra("item", item);
//                        intent.putExtra("index", index);
                        context.startActivity(intent);
                    }
                }
        );



        holder.name.setText(item.getName());
      //  holder.created.setText(format(item.getCreated()));

//        TextView name = holder.itemView.findViewById(R.id.name);
//        TextView created = holder.itemView.findViewById(R.id.created);
//        CheckBox itemChecked = holder.itemView.findViewById(R.id.checked);
//        TextView timeDuration = holder.itemView.findViewById(R.id.timeDuration);
//        Item item = Store.getStore().get(index);
//        name.setText(String.format("%s %s", index+1, item.getName()));
//        created.setText(format(item.getCreated()));

        //редактируем массив отмеченных элементов
        holder.itemChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean checked) {
                int itemId = itemList.get(index).getId();
                if (checked){
                    Store.getStore().addChecked(itemId);
                } else {
                    if (Store.getStore().getAllChecked().contains(itemId)){ Store.getStore().removeChecked(itemId); }
                }
            }
        });

        Long itemSeconds = item.getTimeAmount();
        holder.timeDuration.setText(String.format(
                Locale.getDefault(),"%d:%d", itemSeconds % 60, itemSeconds / 60
                ));
    }

    //преобразование даты
    private String format(Calendar cal) {
        return String.format(
                Locale.getDefault(), "%02d.%02d.%d",
                cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)
        );
    }

    @Override
    public int getItemCount() { return this.itemList.size(); }

    //    @Override
//    public int getItemCount() {
//        return Store.getStore().size();
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView created;
        CheckBox itemChecked;
        TextView timeDuration;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
        //    created = view.findViewById(R.id.created);
            itemChecked = view.findViewById(R.id.checked);
            timeDuration = view.findViewById(R.id.timeDuration);

        }
    }


}
