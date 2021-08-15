package com.example.semiparams;

import android.content.Context;
import android.nfc.FormatException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Semi_act extends AppCompatActivity
{
    String[] material = {"Si", "Ge", "GaAs"};
    //String[] Type;
    ArrayList<String> Type = new ArrayList<String>();
    String GetType, LastChoise;
    String[] Param1 = {"Удельное сопротивление", "Проводимость", "Концентрация примеси"};
    String[] Param2 = {"Диффузионная длина", "Время жизни"};
    String PATH_TO_DATA = "/data/data/com.example.semiparams/files/";
    MaterialParams MaterialParams;
    TextView dopant;

    int SpinnerChoiseOne, SpinnerChoiseTwo, SpinnerChoiseThree, SpinnerChoiseFoure;
    double Temp, ParamOne, ParamTwo;

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
        EditText Temper = (EditText)findViewById(R.id.temper_val);
        EditText paramOne = (EditText)findViewById(R.id.text1);
        EditText paramTwo = (EditText)findViewById(R.id.text2);
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
                ParamOne = Double.parseDouble(charSequence.toString());
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
                ParamTwo = Double.parseDouble(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
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
        AdapterView.OnItemSelectedListener SpinnerListener = new AdapterView.OnItemSelectedListener()
        {
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
                                SpinnerChoiseThree = 1;
                                break;
                            case "Проводимость":
                                SpinnerChoiseThree = 2;
                                break;
                            case "Концентрация примеси":
                                SpinnerChoiseThree = 3;
                                break;
                            default:
                                SpinnerChoiseThree = 1;
                        };
                        break;
                    case R.id.param2:
                        switch ((String) adapterView.getItemAtPosition(position))
                        {
                            case "Диффузионная длина":
                                SpinnerChoiseFoure = 1;
                                break;
                            case "Время жизни":
                                SpinnerChoiseFoure = 2;
                                break;
                            default:
                                SpinnerChoiseFoure = 1;
                        };
                        if (SpinnerChoiseFoure==1)
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
                if ((parent.getId() == R.id.conduction)&&(GetType!="Собственный"))
                {
                    GetType = MaterialParams.GiveTypeOfDopant((String)parent.getItemAtPosition(position));
                    LastChoise = GetType;
                    if (LastChoise=="N_Type")
                        dopant.setText("Nd, см-3");
                    else if(LastChoise=="P_Type")
                        dopant.setText("Na, см-3");
                }
                else
                    LastChoise = MaterialParams.GiveTypeOfDopant((String)parent.getItemAtPosition(position));
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
                if(GetType=="N_Type")
                    dopant.setText("Nd, см-3");
                else if (GetType=="P_Type")
                    dopant.setText("Na, см-3");
                else
                    dopant.setText("Ni, см-3");
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
            if (GetType=="N_Type")
                dopant.setText("Nd, см-3");
            else
                dopant.setText("Na, см-3");
        }
    }
}