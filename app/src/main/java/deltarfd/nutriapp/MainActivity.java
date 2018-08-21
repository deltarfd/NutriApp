package deltarfd.nutriapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(deltarfd.nutriapp.R.layout.activity_main);
        initSpinner();
        // Get the references to the widgets
        final EditText editTextNama = (EditText) findViewById(R.id.editTextNama);
        final EditText editTextBerat = (EditText) findViewById(R.id.editTextBerat);
        final EditText editTextTinggi = (EditText) findViewById(R.id.editTextTinggi);
        final EditText editTextUmur = (EditText) findViewById(R.id.editTextUmur);
        final RadioGroup radioGender = (RadioGroup) findViewById(R.id.radioGender);

        findViewById(R.id.btnResult).setOnClickListener(new View.OnClickListener(){

            // Logic for validation, input can't be empty
            @Override
            public void onClick(View v) {

                String strBerat = editTextBerat.getText().toString();
                String strTinggi = editTextTinggi.getText().toString();
                String strUmur = editTextUmur.getText().toString();

                if(TextUtils.isEmpty(editTextNama.getText())){
                    editTextNama.setError("Masukkan Nama");
                    editTextNama.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(strUmur)){
                    editTextUmur.setError("Masukkan Umur");
                    editTextUmur.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(strBerat)){
                    editTextBerat.setError("Masukkan Berat Badan");
                    editTextBerat.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(strTinggi)){
                    editTextTinggi.setError("Masukkan Tinggi Badan");
                    editTextTinggi.requestFocus();
                    return;
                }



                int selectedGender = radioGender.getCheckedRadioButtonId();
                RadioButton radioselected = (RadioButton) findViewById(selectedGender);
                String gender = (String) radioselected.getText();

                //Calculate BMI value
                float hasilKalori = hitungKalori(Float.parseFloat(strBerat),Float.parseFloat(strTinggi),gender, Integer.parseInt(strUmur));
                float hasilBMI = hitungBMI(Float.parseFloat(strBerat),Float.parseFloat(strTinggi)/100);
                //Define the meaning of the bmi value
                 String hasilkategori = kategoriBMI(hasilBMI);


                Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                intent.putExtra("message",String.valueOf(hasilBMI));
                intent.putExtra("message1",hasilkategori);
                intent.putExtra("message2",String.valueOf(hasilKalori));
                //Toast.makeText(MainActivity.this,String.valueOf(hasilBMI) ,Toast.LENGTH_SHORT).show();
                startActivity(intent);
//                 tv4.setText(String.valueOf(bmiValue + "-" + bmiInterpretation));
                //User Logged in Successfully Launch You home screen activity
//                Intent intent=new Intent(MainActivity.this,ResultActivity.class);
//                startActivity(intent);
//                finish();
             }
         });

    }
    //Calculate BMI
    private float hitungBMI(float berat,float tinggi){
        return (float) (berat / (tinggi * tinggi));
    }

    private float hitungKalori (float berat, float tinggi, String gender, int umur) {
        if(gender.equals("Laki-laki")){
            return (float) (66 + (13.7 * berat) + (5 * tinggi) + (6.8 * umur));
        }
        else{
            return (float) (65.5 + (9.6 * berat) + (1.8 * tinggi) + (4.7 * umur));
        }
    }

    // Interpret what BMI means
    private String kategoriBMI(float nilaiBMI) {

        if (nilaiBMI < 18.5) {
            return "Berat Badan Kurang";
        } else if (nilaiBMI >= 18.5 && nilaiBMI <= 22.9) {

            return "Normal";
        } else if (nilaiBMI >= 23.0 && nilaiBMI <= 24.9) {

            return "Beresiko";
        } else if (nilaiBMI >= 25.0 && nilaiBMI <= 29.9) {

            return "Obesitas Tingkat I";
        } else {
            return "Obesitas Tingkat II";
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

//        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void initSpinner(){
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Ringan");
        categories.add("Sedang");
        categories.add("Berat");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
}
