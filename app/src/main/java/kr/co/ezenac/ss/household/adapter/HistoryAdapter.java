package kr.co.ezenac.ss.household.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.ezenac.ss.household.R;
import kr.co.ezenac.ss.household.db.DBManager;
import kr.co.ezenac.ss.household.model.History;

/**
 * Created by Administrator on 2017-11-21.
 */

public class HistoryAdapter extends BaseAdapter {
    ArrayList<History> items = new ArrayList<>();
    Context context;
    DBManager dbManager;

    public HistoryAdapter(ArrayList<History> items, Context context, DBManager dbManager){
        this.items = items;
        this.context = context;
        this.dbManager = dbManager;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();

        if(view==null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false);
            holder.info_text = view.findViewById(R.id.info_text);
            holder.info_money = view.findViewById(R.id.info_money);
            holder.info_color = view.findViewById(R.id.info_color);
            holder.btn_delete = view.findViewById(R.id.btn_delete);

            view.setTag(holder);
        } else{
            holder = (Holder) view.getTag();
        }

        History item = (History) getItem(i);
        holder.info_money.setText(item.getMoney().toString());


        if(item.getType()==0){
            holder.info_color.setBackgroundResource(R.drawable.shape_oval_expense);
            holder.info_text.setText(R.string.expense);
            holder.info_text.setTextColor(context.getColor(R.color.expense));
        } else{
            holder.info_color.setBackgroundResource(R.drawable.shape_oval_income);
            holder.info_text.setText(R.string.income);
            holder.info_text.setTextColor(context.getColor(R.color.income));

        }



        final int tmpI = i;
        final int id = item.getId();
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("경고");
                alertDialog.setMessage("정말 삭제하시겠습니까?");
                alertDialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        items.remove(tmpI);
                        dbManager.delete(id);
                        notifyDataSetChanged();
                    }
                });
                alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
            }
        });

        return view;
    }

    private class Holder{
        TextView info_text;
        TextView info_money;
        ImageView info_color;
        Button btn_delete;
    }

}
