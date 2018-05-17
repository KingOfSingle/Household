package kr.co.ezenac.ss.household;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InputActivity extends AppCompatActivity {

    TextView tv_day;
    EditText et_money;
    Button btn_close;
    Button btn_income;
    Button btn_expense;
    Button btn_delete;
    int year;
    int month;
    int day;

//    // 현재시간을 msec 으로 구한다.
//    long now = System.currentTimeMillis();
//    // 현재시간을 date 변수에 저장한다.
//    Date date = new Date(now);
//    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
//    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
//    // nowDate 변수에 값을 저장한다.
//    String formatDate = sdfNow.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        tv_day = findViewById(R.id.input_txt_day);
        et_money = findViewById(R.id.et_money);
        btn_close = findViewById((R.id.input_btn_close));
        btn_income = findViewById(R.id.btn_income);
        btn_expense = findViewById(R.id.btn_expense);
        btn_delete = findViewById(R.id.btn_delete);

//        tv_day.setText(formatDate);



        Intent intent = getIntent();
        final Integer pyear = intent.getIntExtra("year",0);
        final Integer pmonth = intent.getIntExtra("month",0);
        final Integer pday = intent.getIntExtra("day",0);

        setDate(pyear,pmonth,pday);


        tv_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int cyear, int cmonth, int cday) {
                        year = cyear;
                        month = cmonth+1;
                        day = cday;
                        setDate(year, month, day);
                        Log.d("kac",year + " " + month + " " + day);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(InputActivity.this, dateSetListener, year, month-1, day);
                dialog.show();

            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String money = et_money.getText().toString();

                if(money.equals("") || money.equals("0")){
                    return;
                }

                int type = 1;

                Intent intent = getIntent();
                intent.putExtra("type", type);
                intent.putExtra("cost", Integer.parseInt(money));
                intent.putExtra("year", pyear);
                intent.putExtra("month", pmonth);
                intent.putExtra("day", pday);

                Log.d("kac",pyear + " " + pmonth + " " + pday);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String money = et_money.getText().toString();

                if(money.equals("") || money.equals("0")){
                    Toast toast = Toast.makeText(InputActivity.this, "값을 입력해주세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                int type = 0;

                Intent intent = getIntent();
                intent.putExtra("type", type);
                intent.putExtra("cost", Integer.parseInt(money));
                intent.putExtra("year", pyear);
                intent.putExtra("month", pmonth);
                intent.putExtra("day", pday);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public  void setDate(Integer year, Integer month, Integer day){
        tv_day.setText(year + "/" + month + "/" + day);

    }
}
