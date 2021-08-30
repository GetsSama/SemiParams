package com.example.semiparams;

import static com.example.semiparams.SemiConstants.*;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Semi_act extends AppCompatActivity
{
    String[] material = {"Si", "Ge", "GaAs"};
    ArrayList<String> Type = new ArrayList<String>();
    String GetType, LastChoise, Doping;
    String[] Param1 = {"Удельное сопротивление", "Проводимость", "Концентрация примеси"};
    String[] Param2 = {"Диффузионная длина", "Время жизни"};
    MaterialParams MaterialParams;
    TextView dopant;

    String SpinnerChoiseThree, SpinnerChoiseFoure;
    double Temp, ParametrOne, ParametrTwo, PowerOne, PowerTwo, Nc, Nv, ni, Conc;
    boolean flag;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semi);
        dopant = (TextView)findViewById(R.id.doping);
        TextView dinamic = (TextView)findViewById(R.id.Dinamic);
        TextView electron_val = (TextView)findViewById(R.id.electron_val);
        TextView hole_val = (TextView)findViewById(R.id.hole_val);
        TextView intrinsic_val = (TextView)findViewById(R.id.intrinsic_val);
        TextView conductivity_val = (TextView)findViewById(R.id.conductivity_val);
        TextView resistiv_val = (TextView)findViewById(R.id.resistiv_val);
        TextView doping_val = (TextView)findViewById(R.id.doping_val);
        TextView Nc_val = (TextView)findViewById(R.id.Nc_val);
        TextView Nv_val = (TextView)findViewById(R.id.Nv_val);
        TextView Eg_val = (TextView)findViewById(R.id.Eg_val);
        TextView Dinamic_val = (TextView)findViewById(R.id.Dinamic_val);
        TextView Dn = (TextView)findViewById(R.id.coefDiff_valN);
        TextView Dp = (TextView)findViewById(R.id.coefDiff_valP);
        EditText Temper = (EditText)findViewById(R.id.temper_val);
        EditText paramOne = (EditText)findViewById(R.id.text1);
        EditText paramTwo = (EditText)findViewById(R.id.text2);
        EditText PowOne = (EditText)findViewById(R.id.power);
        EditText PowTwo = (EditText)findViewById(R.id.power2);
        Button Solve = (Button)findViewById(R.id.Solve);
        CheckBox Check = (CheckBox)findViewById(R.id.Native);
        Type.add("");


        //Считываю заполняемые поля в переменные
        Temper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                //Temp = Float.parseFloat(charSequence.toString());
                Temp = Double.parseDouble(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
            }
        });
        paramOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                //ParamOne = Float.parseFloat(charSequence.toString());
                ParametrOne = Double.parseDouble(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
            }
        });
        paramTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                //ParamTwo = Float.parseFloat(charSequence.toString());
                ParametrTwo = Double.parseDouble(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
            }
        });
        PowOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PowerOne = Double.parseDouble(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        PowTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PowerTwo = Double.parseDouble(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Создаю 4 спиннера
        Spinner mater = (Spinner) findViewById(R.id.mater);
        Spinner TypeOfCond = (Spinner) findViewById(R.id.conduction);
        Spinner ParamOne = (Spinner) findViewById(R.id.param1);
        Spinner ParamTwo = (Spinner) findViewById(R.id.param2);
        //Добаляю адаптеры для спиннеров
        ArrayAdapter<String> adapter_material = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, material);
        ArrayAdapter<String> adapter_param1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Param1);
        ArrayAdapter<String> adapter_param2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Param2);
        //Настраиваю адаптеры
        adapter_material.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_param1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_param2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Адаптирую спиннеры
        mater.setAdapter(adapter_material);
        ParamOne.setAdapter(adapter_param1);
        ParamTwo.setAdapter(adapter_param2);
        //Спиннер для примесей, динамически изменяется
        ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Type);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeOfCond.setAdapter(adapter_type);

        //Слушатель нажатий на спиннеры
        AdapterView.OnItemSelectedListener SpinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                String ItemText = (String) adapterView.getItemAtPosition(position);
                switch (adapterView.getId())
                {
                    case R.id.mater:
                        MaterialParams = new MaterialParams(ItemText, Semi_act.this);
                        Type.clear();
                        Type.addAll(MaterialParams.getDopings());
                        adapter_type.notifyDataSetChanged();
                        break;
                    case R.id.param1:
                        switch ((String) adapterView.getItemAtPosition(position))
                        {
                            case "Удельное сопротивление":
                                SpinnerChoiseThree = "Resist";
                                break;
                            case "Проводимость":
                                SpinnerChoiseThree = "Conduct";
                                break;
                            case "Концентрация примеси":
                                SpinnerChoiseThree = "Concentration";
                                break;
                            default:
                                SpinnerChoiseThree = "Resist";
                        };
                        break;
                    case R.id.param2:
                        switch ((String) adapterView.getItemAtPosition(position))
                        {
                            case "Диффузионная длина":
                                SpinnerChoiseFoure = "Lenght";
                                break;
                            case "Время жизни":
                                SpinnerChoiseFoure = "Time";
                                break;
                            default:
                                SpinnerChoiseFoure = "Lenght";
                        };
                        if (SpinnerChoiseFoure.equals("Lenght"))
                            dinamic.setText("Tau, с");
                        else
                            dinamic.setText("L, см");
                        break;
                };
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        };

        //Слушатель нажатий на выбор примеси
        AdapterView.OnItemSelectedListener DopingListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                flag = GetType.equals("Собственный");
                if ((parent.getId() == R.id.conduction)&&(!flag))
                {
                    GetType = MaterialParams.GiveTypeOfDopant((String)parent.getItemAtPosition(position));
                    LastChoise = GetType;
                    Doping = (String)parent.getItemAtPosition(position);
                    if (LastChoise.equals("N_Type"))
                        dopant.setText("Nd, см-3");
                    else if(LastChoise.equals("P_Type"))
                        dopant.setText("Na, см-3");
                }
                else
                {
                    LastChoise = MaterialParams.GiveTypeOfDopant((String)parent.getItemAtPosition(position));
                    Doping = (String)parent.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        };

        //Присваиваю спиннерам слушатель
        mater.setOnItemSelectedListener(SpinnerListener);
        TypeOfCond.setOnItemSelectedListener(DopingListener);
        ParamOne.setOnItemSelectedListener(SpinnerListener);
        ParamTwo.setOnItemSelectedListener(SpinnerListener);

        //Слушатель нажания на кнопку
        View.OnClickListener Solver = new View.OnClickListener()
        {
            @Override
            public void onClick(View buttonSolve)
            {
                double ParamOneVal, ParamTwoVal, N, P;
                ParamOneVal = ParametrOne*Math.pow(PowerOne,10);
                ParamTwoVal = ParametrTwo*Math.pow(PowerTwo,10);

                Nc = densityNumber*Math.pow(MaterialParams.electronEffMass()*Temp, 1.5);
                Nv = densityNumber*Math.pow(MaterialParams.holeEffMass()*Temp,1.5);
                ni = Math.sqrt(Nc*Nv)*Math.exp(-(MaterialParams.Eg(Temp))/(2*Bolcman*Temp));
                Nc_val.setText(formatOut(Nc));
                Nv_val.setText(formatOut(Nv));
                intrinsic_val.setText(formatOut(ni));
                Eg_val.setText(formatOut(MaterialParams.Eg(Temp)));
                Dn.setText(formatOut(MaterialParams.Dn(Temp)));
                Dp.setText(formatOut(MaterialParams.Dp(Temp)));

                flag = GetType.equals("Собственный");
                if (!flag)
                {
                    switch (SpinnerChoiseThree) {
                        case "Resist":
                            resistiv_val.setText(formatOut(ParamOneVal));
                            conductivity_val.setText(formatOut(Math.pow(ParamOneVal,-1)));
                            if (GetType.equals("N_Type"))
                                Conc = 1/(electronCharge*ParamOneVal*MaterialParams.electronMobility());
                            else
                                Conc = 1/(electronCharge*ParamOneVal*MaterialParams.holeMobility());
                            doping_val.setText(formatOut(Conc));
                        break;

                        case "Conduct":
                            resistiv_val.setText(formatOut(Math.pow(ParamOneVal,-1)));
                            conductivity_val.setText(formatOut(ParamOneVal));
                            if (GetType.equals("N_Type"))
                                Conc = ParamOneVal/(electronCharge*MaterialParams.electronMobility());
                            else
                                Conc = ParamOneVal/(electronCharge*MaterialParams.holeMobility());
                            doping_val.setText(formatOut(Conc));
                        break;

                        case "Concentration":
                            doping_val.setText(formatOut(ParamOneVal));
                            Conc = ParamOneVal;
                            if (GetType.equals("N_Type"))
                            {
                                resistiv_val.setText(formatOut(1/(electronCharge*ParamOneVal*MaterialParams.electronMobility())));
                                conductivity_val.setText(formatOut(electronCharge*ParamOneVal*MaterialParams.electronMobility()));
                            }
                            else
                            {
                                resistiv_val.setText(formatOut(1/(electronCharge*ParamOneVal*MaterialParams.holeMobility())));
                                conductivity_val.setText(formatOut(electronCharge*ParamOneVal*MaterialParams.holeMobility()));
                            }
                        break;
                    }
                    if (GetType.equals("N_Type"))
                    {
                        N = Math.sqrt(Nc*Conc/2)*Math.exp(-MaterialParams.EnergyOfDopant(Doping)/(2*Bolcman*Temp));
                        P = Math.pow(ni,2)/N;
                    }
                    else
                    {
                        P = Math.sqrt(Nv*Conc/2)*Math.exp(-MaterialParams.EnergyOfDopant(Doping)/(2*Bolcman*Temp));
                        N = Math.pow(ni,2)/P;
                    }
                    electron_val.setText(formatOut(N));
                    hole_val.setText(formatOut(P));
                }
                else
                {
                    doping_val.setText(formatOut(ni));
                    electron_val.setText(formatOut(ni));
                    hole_val.setText(formatOut(ni));
                }


            }
        };
        Solve.setOnClickListener(Solver);
    }

    //Выбор собственного полупроводника
    public void onCheckClick (View view)
    {
        CheckBox check = (CheckBox) view;
        if (check.isChecked())
        {
            GetType = "Собственный";
            dopant.setText("Ni, см-3");
        }
        else
        {
            GetType = LastChoise;
            if (GetType.equals("N_Type"))
                dopant.setText("Nd, см-3");
            else
                dopant.setText("Na, см-3");
        }
    }

    public String formatOut(double val)
    {
        if ((val<-100)|(val>100))
            return String.format("%.3e",val);
        else
            return String.format("%.3f",val);
    }
}