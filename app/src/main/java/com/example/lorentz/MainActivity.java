package com.example.lorentz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ImageView formula;
    private LinearLayout getVelocity;
    private LinearLayout getResult;
    private Button Lorentz;
    private Button Spi;
    private TextView Heading;
    private RadioGroup Mode;
    private Button Next;
    private TextView Result;
    private String mode;
    private Double Velocity;
    private Double LorentzValue;
    private Button Back;
    private Double userInput;
    private EditText velInput;
    private EditText resultInput;
    private Timer timer;
    private Calendar calendar;
    private String time;
    private TextView timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Lorentz=findViewById(R.id.Lorentz);
        Spi=findViewById(R.id.Spi);
        Heading=findViewById(R.id.Heading);
        getResult=findViewById(R.id.GetResult);
        getVelocity=findViewById(R.id.GetVelocity);
        formula=findViewById(R.id.formula);
        Mode=findViewById(R.id.Modes);
        Next=findViewById(R.id.Next);
        Result=findViewById(R.id.Result);
        Back=findViewById(R.id.back);
        velInput=findViewById(R.id.VelInput);
        resultInput=findViewById(R.id.ResultInput);
        timeText=findViewById(R.id.timer);
        timeText.setVisibility(View.INVISIBLE);
        EraseLorentz();
        DisplayMain();
        Lorentz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EraseMain();
                DisplayLorentz();
            }
        });
        Mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (Mode.getCheckedRadioButtonId() != -1) {
                    Result.setVisibility(View.INVISIBLE);
                    if (((RadioButton) findViewById(Mode.getCheckedRadioButtonId())).getText().toString().equals("Calculator")) {
                        getVelocity.setVisibility(View.VISIBLE);
                        getResult.setVisibility(View.INVISIBLE);
                        Next.setText("CALCULATE");
                        Next.setVisibility(View.VISIBLE);
                        mode = "Calculator";
                        if (velInput.getText() != null || resultInput.getText() != null) {
                            ResetInput();
                        }

                    }


                    if (((RadioButton) findViewById(Mode.getCheckedRadioButtonId())).getText().toString().equals("Practice")) {
                        getResult.setVisibility(View.VISIBLE);
                        getVelocity.setVisibility(View.VISIBLE);
                        Next.setText("CHECK");
                        Next.setVisibility(View.VISIBLE);
                        mode = "Practice";
                        if (velInput.getText() != null || resultInput.getText() != null) {
                            ResetInput();
                        }
                    }
                }
                else EraseLorentz();
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = velInput.getText().toString();
                String temp1;
                if (Next.getText().toString().equals("RESET")) {
                   ResetInput();
                }
                else {
                    if (temp.equals(""))
                        Toast.makeText(MainActivity.this, "Enter The Velocity", Toast.LENGTH_SHORT).show();
                    else {
                        Velocity = Double.parseDouble(temp);
                        if (Velocity >= 300000000)
                            Toast.makeText(MainActivity.this, "Inavalid Input", Toast.LENGTH_SHORT).show();
                        else CalculateLorentz();

                        if (mode.equals("Practice")) {
                            temp1 = resultInput.getText().toString();
                            if (temp1.equals(""))
                                Toast.makeText(MainActivity.this, "Enter The Result", Toast.LENGTH_SHORT).show();
                            else {
                                userInput = Double.parseDouble(temp1);
                                if (userInput < 1)
                                    Toast.makeText(MainActivity.this, "Inavalid Input", Toast.LENGTH_SHORT).show();
                                else {
                                    CalculateLorentz();
                                    CheckLorentz();
                                }
                            }
                        }
                    }
                }
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMain();
                EraseLorentz();
                ResetInput();
                if(timeText.getVisibility()==View.VISIBLE)
                    EraseSpi();
            }
        });
        Spi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EraseMain();
                DisplaySpi();
            }
        });


    }
    public void DisplayMain(){
        Lorentz.setVisibility(View.VISIBLE);
        Spi.setVisibility(View.VISIBLE);
        Heading.setText("BEST OF LUCK");
        Back.setVisibility(View.INVISIBLE);
    }
    public void EraseLorentz(){
        Mode.setVisibility(View.INVISIBLE);
        getVelocity.setVisibility(View.INVISIBLE);
        getResult.setVisibility(View.INVISIBLE);
        Next.setVisibility(View.INVISIBLE);
        formula.setVisibility(View.INVISIBLE);
        Result.setVisibility(View.INVISIBLE);
        if(Mode.getCheckedRadioButtonId()!=-1)
            Mode.clearCheck();
        Heading.setText("BEST OF LUCK");

    }
    public void DisplayLorentz(){
        formula.setVisibility(View.VISIBLE);
        formula.setImageResource(R.drawable.lorentz);
        Mode.setVisibility(View.VISIBLE);
        Heading.setText("LORENTZ FACTOR");
        Back.setVisibility(View.VISIBLE);

    }
    public void EraseMain(){
        Lorentz.setVisibility(View.INVISIBLE);
        Spi.setVisibility(View.INVISIBLE);
        Heading.setText("");
    }
    public void CalculateLorentz(){
        LorentzValue=(1/Math.sqrt(1-Math.pow(Velocity.doubleValue()/300000000,2 )));
        LorentzValue=new Double(Double.valueOf((new DecimalFormat("#.#########")).format(LorentzValue)));
        if(mode.equals("Calculator")){
            Result.setVisibility(View.VISIBLE);
            Result.setText(" Lorentz "+LorentzValue+" ");
            Next.setText("RESET");
        }
    }
    public void DisplaySpi(){
        timeText.setVisibility(View.VISIBLE);
        formula.setVisibility(View.VISIBLE);
        formula.setImageResource(R.drawable.spi);
        PrintTime();
        Heading.setText("SPI FACTOR");
        Back.setVisibility(View.VISIBLE);
    }
    public void EraseSpi(){
        timeText.setVisibility(View.INVISIBLE);
        formula.setVisibility(View.INVISIBLE);
        timer.cancel();
    }
    public void CheckLorentz(){
        userInput=new Double(Double.valueOf((new DecimalFormat("#.###")).format(userInput)));
        LorentzValue=new Double(Double.valueOf((new DecimalFormat("#.###")).format(LorentzValue)));
        Vibrator vibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(LorentzValue.equals(userInput)){
            resultInput.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.green));
            Result.setVisibility(View.VISIBLE);
            Result.setText(" Lorentz "+LorentzValue+" ");
            Next.setText("RESET");
            Result.setBackground(AppCompatResources.getDrawable(MainActivity.this,R.drawable.correct));
        }
        else {
            resultInput.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.red));
            Result.setVisibility(View.VISIBLE);
            Result.setText(" Lorentz "+LorentzValue+" ");
            Next.setText("RESET");
            vibrator.vibrate(1000);
            Result.setBackground(AppCompatResources.getDrawable(MainActivity.this,R.drawable.wrong));
        }
    }
    public void ResetInput() {
        velInput.getText().clear();
        resultInput.getText().clear();
        velInput.clearFocus();
        resultInput.clearFocus();
        Result.setText("");
        Result.setBackground(AppCompatResources.getDrawable(MainActivity.this,R.drawable.text));
        resultInput.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        if (mode != null) {
            if (mode.equals("Calculator")) {
                Next.setText("CALCULATE");
            } else Next.setText("CHECK");
        }
    }
    public void PrintTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        timer=new Timer();
        timeText.setVisibility(View.VISIBLE);
        Result.setVisibility(View.VISIBLE);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                calendar=Calendar.getInstance();
                time=simpleDateFormat.format(calendar.getTime());
                int HH=(calendar.get(Calendar.HOUR))%12;
                int MM=calendar.get(Calendar.MINUTE);
                int SS=calendar.get(Calendar.SECOND);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeText.setText(" TIME :"+String.format("%02d",HH)+":"+String.format("%02d",MM)+":"+String.format("%02d",SS)+" ");
                        Result.setText(" SPI :"+String.format("%.8f",CalculateSpi(HH,MM,SS))+" ");
                    }
                });
            }
        },0,1000);
    }
    public double CalculateSpi(int a,int b,int c){
        double Spi=1.0;
        for(int i=1;i<=a;i++)
            Spi=Spi*i;
        if((b!=0)||(c!=0))
        Spi=Spi/(b*b*b+c);
        return Spi;
    }
}