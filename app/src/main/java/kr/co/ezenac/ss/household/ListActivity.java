package kr.co.ezenac.ss.household;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Calendar;


import kr.co.ezenac.ss.household.adapter.HistoryAdapter;
import kr.co.ezenac.ss.household.db.DBManager;
import kr.co.ezenac.ss.household.model.History;

public class ListActivity extends AppCompatActivity {

    DBManager dbManager;
    ArrayList<History> histories = new ArrayList<>();
    ListView listView;
    HistoryAdapter historyAdapter;
    TextView txt_date;
    Button btn_date_forward;
    Button btn_date_backward;
    Button btn_add;
    int year;
    int month;
    int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.list);
        txt_date = findViewById(R.id.list_txt_date);
        btn_date_forward = findViewById(R.id.list__btn_dateForward);
        btn_date_backward = findViewById(R.id.list__btn_dateBackward);
        btn_add = findViewById(R.id.btn_add);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);

        setDate(year, month, day);

        dbManager = new DBManager(
                ListActivity.this, "SaveMyMoneyManager.db", null, 1);

        histories = dbManager.getHistoryList(year, month, day);


        historyAdapter = new HistoryAdapter(histories, ListActivity.this, dbManager);
        listView.setAdapter(historyAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, InputActivity.class);

                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("day", day);
                startActivityForResult(intent, 0);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                History item = histories.get(i);

                Intent intent = new Intent(ListActivity.this, InfoActivity.class);
                intent.putExtra("cost", item.getMoney());
                intent.putExtra("type", item.getType());
                intent.putExtra("pos", i);
                intent.putExtra("id", item.getId());

                startActivityForResult(intent, 1);
            }
        });

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int cyear, int cmonth, int cday) {
                        year = cyear;
                        month = cmonth + 1;
                        day = cday;
                        refreshList(year, month, day);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(ListActivity.this, dateSetListener,
                        year, month - 1, day);
                dialog.show();
            }
        });

        btn_date_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month - 1);
                c.set(Calendar.DAY_OF_MONTH, day);

                c.add(Calendar.DATE, 1);

                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH) + 1;
                day = c.get(Calendar.DAY_OF_MONTH);
                refreshList(year, month, day);
            }
        });

        btn_date_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month - 1);
                c.set(Calendar.DAY_OF_MONTH, day);

                c.add(Calendar.DATE, -1);

                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH) + 1;
                day = c.get(Calendar.DAY_OF_MONTH);

                refreshList(year, month, day);
            }
        });

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(ListActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(ListActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };

        TedPermission.with(ListActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.RECEIVE_SMS)
                .check();


}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0){
            if(resultCode==RESULT_OK){

                Integer money = data.getIntExtra("cost",0);
                Integer type = data.getIntExtra("type",0);
                Integer year = data.getIntExtra("year",0);
                Integer month = data.getIntExtra("month",0);
                Integer day = data.getIntExtra("day",0);

                dbManager.insert(type, money, year, month, day);
                refreshList(year, month, day);

            }
        } else if(requestCode==1){
            if(resultCode==RESULT_OK){
                int pos = data.getIntExtra("pos",0);
                int id = data.getIntExtra("id",0);

                histories.remove(pos);
                dbManager.delete(id);
                historyAdapter.notifyDataSetChanged();
            }

        }
    }

    public  void setDate(Integer year, Integer month, Integer day){
        txt_date.setText(year + "/" + month + "/" + day);
    }

    public void refreshList(Integer year, Integer month, Integer day){
        setDate(year, month, day);
        histories = dbManager.getHistoryList(year,month,day);
        historyAdapter = new HistoryAdapter(histories, ListActivity.this, dbManager);
        listView.setAdapter(historyAdapter);
    }

}