package com.example.semiparams;

import android.nfc.FormatException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    String[] Si_dopants = {"As", "P", "Sb", "Al", "B", "Ga", "In"};
    String[] Type = {"n-тип", "p-тип", "Собственный"};
    String[] Param1 = {"Удельное сопротивление", "Проводимость", "Концентрация примеси"};
    String[] Param2 = {"Диффузионная длина", "Время жизни"};

    double[] Si_dopant_ionization = {0.054, 0.045, 0.043, 0.072, 0.045, 0.074, 0.157};

    int SpinnerChoiseOne, SpinnerChoiseTwo, SpinnerChoiseThree, SpinnerChoiseFoure;
    double Temp, ParamOne, ParamTwo;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semi);
        TextView dopant = (TextView)findViewById(R.id.doping);
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
        ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Type);
        ArrayAdapter<String> adapter_param1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Param1);
        ArrayAdapter<String> adapter_param2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Param2);
        //Настраиваю адаптеры
        adapter_material.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_param1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_param2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Адаптирую спиннеры
        mater.setAdapter(adapter_material);
        TypeOfCond.setAdapter(adapter_type);
        ParamOne.setAdapter(adapter_param1);
        ParamTwo.setAdapter(adapter_param2);
        //Слушатель нажатий на спиннеры
        AdapterView.OnItemSelectedListener SpinnerListener = new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                switch (adapterView.getId())
                {
                    case R.id.mater:
                        switch ((String) adapterView.getItemAtPosition(position))
                        {
                            case "Si":
                                SpinnerChoiseOne = 1;
                                break;
                            case "Ge":
                                SpinnerChoiseOne = 2;
                                break;
                            case "GaAs":
                                SpinnerChoiseOne = 3;
                                break;
                            default:
                                SpinnerChoiseOne = 1;
                        };
                        break;
                    case R.id.conduction:
                        switch ((String) adapterView.getItemAtPosition(position))
                        {
                            case "n-тип":
                                SpinnerChoiseTwo = 1;
                                break;
                            case "p-тип":
                                SpinnerChoiseTwo = 2;
                                break;
                            case "Собственный":
                                SpinnerChoiseTwo = 3;
                                break;
                            default:
                                SpinnerChoiseTwo = 1;
                        };
                        if (SpinnerChoiseTwo==1)
                        {dopant.setText("Nd, см-3");}
                        else if (SpinnerChoiseTwo==2)
                        {dopant.setText("Na, см-3");}
                        else
                        {dopant.setText("Примесь отсутствует");}
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
        //Присваиваю спиннерам слушатель
        mater.setOnItemSelectedListener(SpinnerListener);
        TypeOfCond.setOnItemSelectedListener(SpinnerListener);
        ParamOne.setOnItemSelectedListener(SpinnerListener);
        ParamTwo.setOnItemSelectedListener(SpinnerListener);

        View.OnClickListener Solver = new View.OnClickListener()
        {
            @Override
            public void onClick(View buttonSolve)
            {

            }
        };

        /*MaterialParams mater1 = new MaterialParams();
        mater1.setDopings(dopants);
        String[] afterGet = mater1.getDopings();*/
        ParamsReader(Si, "Si.txt");
    }

    MaterialParams Si = new MaterialParams("Si");
    public void ParamsReader (MaterialParams material ,String FileName)
    {
        ArrayList<String> Dopings = new ArrayList<>();

        try (BufferedReader bufRead = new BufferedReader(new FileReader(FileName)))
        {
            double val = 0;
            Object obj = null;
            boolean flag = true;
            // Читаем из файла до тех пор, пока не закончатся строковые типы
            while (flag)
            {
                try
                {
                    obj = bufRead.readLine();
                    val = Double.parseDouble(obj.toString());
                    flag = false;
                }
                catch (NumberFormatException e)
                {
                    Dopings.add(obj.toString());
                }
            }
            //Инициализируем два массива - примеси и их энергии активации
            material.Dopings = new String[Dopings.size()];
            material.activationEnergis = new double[Dopings.size()];
            //Заполнение массивов
            for (int i=0; i<Dopings.size(); i++)
                material.Dopings[i] = Dopings.get(i);
            material.activationEnergis[0] = val;
            for (int i=1; i<Dopings.size(); i++)
                material.activationEnergis[i] = Double.parseDouble(bufRead.readLine());
            //Заполняем оставшиеся параметры
            material.effMass_electron = Double.parseDouble(bufRead.readLine());
            material.effMass_hole = Double.parseDouble(bufRead.readLine());
            material.mobility_electron = Double.parseDouble(bufRead.readLine());
            material.mobility_hole = Double.parseDouble(bufRead.readLine());
            material.Eg0 = Double.parseDouble(bufRead.readLine());
            material.koefEg = Double.parseDouble(bufRead.readLine());
            material.dielectricConst = Double.parseDouble(bufRead.readLine());
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}