package deltarfd.nutriapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView hasil_imt = (TextView) findViewById(R.id.hasil_imt);
        TextView hasil_kategori = (TextView) findViewById(R.id.hasil_kategori);
        TextView hasil_kalori = (TextView) findViewById(R.id.hasil_kalori);
        TextView hasil_karbohidrat = (TextView) findViewById(R.id.hasil_karbohidrat);
        TextView hasil_protein = (TextView) findViewById(R.id.hasil_protein);
        TextView hasil_lemak = (TextView) findViewById(R.id.hasil_lemak);

        hasil_imt.setText(new DecimalFormat("##.#").format(Float.parseFloat(getIntent().getStringExtra("message"))));
        hasil_kategori.setText(getIntent().getStringExtra("message1"));
        hasil_kalori.setText(new DecimalFormat("##").format(Float.parseFloat(getIntent().getStringExtra("message2"))) + " kkal");

        float kalori = Float.parseFloat(getIntent().getStringExtra("message2"));
        float karbohidrat = (float) (0.65 * kalori / 4);
        float protein = (float) (0.2 * kalori / 4);
        float lemak = (float) (0.15 * kalori / 9);

        hasil_karbohidrat.setText(new DecimalFormat("##.#").format(karbohidrat) + " gram");
        hasil_protein.setText(new DecimalFormat("##.#").format(protein) + " gram");
        hasil_lemak.setText(new DecimalFormat("##.#").format(lemak) + " gram");

        initSnackbarAlert();
        findViewById(R.id.btnKembali).setOnClickListener(new View.OnClickListener() {

            // Logic for validation, input can't be empty
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
//        String bmivalue = getIntent().getStringExtra("message");

//        hasil_imt.setText(bmivalue);
//        hasil_kategori.setText(interpretBMI(bmivalue));

    }

    void initSnackbarAlert(){
        SpannableStringBuilder snackbarText = new SpannableStringBuilder();
        snackbarText.append("Selamat datang di ");
        int boldStart = snackbarText.length();
        snackbarText.append("CodePolitan");
        snackbarText.setSpan(new ForegroundColorSpan(Color.RED), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        snackbarText.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        snackbarText.append(".");

        Snackbar snackbar = Snackbar.make(coordinatorLayout, snackbarText, Snackbar.LENGTH_LONG)
                .setDuration(8000);
        snackbar.show();
    }

}

