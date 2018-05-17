package kr.co.ezenac.ss.household;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    ImageView info_color;
    TextView info_text;
    TextView info_cost;
    Button btn_info_back;
    Button btn_info_delete;
    Integer pos;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        info_color = findViewById(R.id.info_color);
        info_text = findViewById(R.id.info_text);
        info_cost = findViewById(R.id.info_cost);
        btn_info_back = findViewById(R.id.btn_info_back);
        btn_info_delete = findViewById(R.id.btn_info_delete);

        btn_info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        Integer type = intent.getIntExtra("type", 0);
        Integer cost = intent.getIntExtra("cost", 0);
        pos = intent.getIntExtra("pos", 0);
        id = intent.getIntExtra("id",0);

        if(type==0){
            info_text.setText(getString(R.string.expense));
            info_color.setBackgroundResource(R.drawable.shape_oval_expense);
        } else if(type==1){
            info_text.setText(getString(R.string.income));
            info_color.setBackgroundResource(R.drawable.shape_oval_income);
        }

        info_cost.setText(cost.toString());

        btn_info_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(InfoActivity.this);
                alertDialog.setTitle("경고");
                alertDialog.setMessage("정말 삭제하시겠습니까?");
                alertDialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = getIntent();
                        intent.putExtra("pos", pos);
                        intent.putExtra("id",id);
                        setResult(RESULT_OK, intent);
                        finish();
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

    }
}
