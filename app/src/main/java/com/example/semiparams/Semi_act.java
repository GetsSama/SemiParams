package com.example.semiparams;

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
        CheckBox Check = (CheckBox)findViewById(R.id.Native);


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
        //ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Type);
        ArrayAdapter<String> adapter_param1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Param1);
        ArrayAdapter<String> adapter_param2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Param2);
        //Настраиваю адаптеры
        adapter_material.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_param1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_param2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Адаптирую спиннеры
        mater.setAdapter(adapter_material);
        //TypeOfCond.setAdapter(adapter_type);
        ParamOne.setAdapter(adapter_param1);
        ParamTwo.setAdapter(adapter_param2);


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
                        MaterialParams = new MaterialParams(ItemText);
                        ParamsReader(MaterialParams,PATH_TO_DATA+ItemText+".txt");
                        Type.clear();
                        //Type = MaterialParams.DopEnergisDict.keySet().toArray(new String[MaterialParams.DopEnergisDict.size()]);
                        for (int i=0; i<MaterialParams.DopEnergisDict.size(); i++)
                        {
                            Type.add(MaterialParams.getDopings(i));
                        }
                        //TypeOfCond.notify();
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

        ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Type);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeOfCond.setAdapter(adapter_type);
        //Слушатель нажатий на выбор примеси
       AdapterView.OnItemSelectedListener DopingListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if ((parent.getId() == R.id.conduction)&&(GetType!="Собственный"))
                {
                    GetType = GiveType(MaterialParams.nameMaterial, (String)parent.getItemAtPosition(position));
                    LastChoise = GetType;
                }
                else
                    LastChoise = GiveType(MaterialParams.nameMaterial, (String)parent.getItemAtPosition(position));
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
        View.OnClickListener Solver = new View.OnClickListener()
        {
            @Override
            public void onClick(View buttonSolve)
            {

            }
        };

    }

    //Функция для считывания констант материала полупроводника из файла для этого материала
    public void ParamsReader (MaterialParams material ,String PathToFile)
    {
        ArrayList<String> Dopings = new ArrayList<>();

        try (BufferedReader bufRead = new BufferedReader(new FileReader(PathToFile)))
        {
            double val = 0;
            double[] energy;
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
            //Создаем локальный массив энергий и примесей
            energy = new double[Dopings.size()];
            String[] dopings = new String[Dopings.size()];
            Dopings.toArray(dopings);
            //Заполнение локального массива энергий активации
            energy[0] = val;
            for (int i=1; i<Dopings.size(); i++)
                energy[i] = Double.parseDouble(bufRead.readLine());
            //Передаем считанные данные в объект MaterialParams
            material.setDopings(Dopings.size(),dopings);
            material.setDopingsEnergy(Dopings.size(),energy);
            //Заполняем оставшиеся параметры
            material.effMass_electron = Double.parseDouble(bufRead.readLine());
            material.effMass_hole = Double.parseDouble(bufRead.readLine());
            material.mobility_electron = Double.parseDouble(bufRead.readLine());
            material.mobility_hole = Double.parseDouble(bufRead.readLine());
            material.Eg0 = Double.parseDouble(bufRead.readLine());
            material.koefEg = Double.parseDouble(bufRead.readLine());
            material.dielectricConst = Double.parseDouble(bufRead.readLine());
            //Проверка после считывания и активация словаря примесь-энергия
            if (material.dielectricConst !=0 && (bufRead.readLine())==null)
            {
                Toast.makeText(this,material.nameMaterial+" загружен", Toast.LENGTH_SHORT).show();
                material.ActivateDict();
            }
            else
            {
                Toast.makeText(this, "Неверный формат файла параметров материала!", Toast.LENGTH_LONG).show();
            }
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
    //Функция для определения типа примеси
    public String GiveType (String MaterialName, String Doping)
    {
        String Type = "";
        String s;
        String path = PATH_TO_DATA + "Dopings_" + MaterialName;
        ArrayList<String> Dopings_n = new ArrayList<String>();
        ArrayList<String> Dopings_p = new ArrayList<String>();

        try (BufferedReader bufReader = new BufferedReader(new FileReader(path+"_n.txt")))
        {
            while ((s=bufReader.readLine())!=null)
                Dopings_n.add(s);
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try (BufferedReader bufReader = new BufferedReader(new FileReader(path+"_p.txt")))
        {
            while ((s=bufReader.readLine())!=null)
                Dopings_p.add(s);
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (Dopings_n.indexOf(Doping)!=-1)
        {
            Toast.makeText(this, "N-тип", Toast.LENGTH_SHORT).show();
            Type = "N_Type";
        }
        else if (Dopings_p.indexOf(Doping)!=-1)
        {
            Toast.makeText(this, "P-тип", Toast.LENGTH_SHORT).show();
            Type = "P_Type";
        }
        else
        {
            Toast.makeText(this, "Примесь не найдена!", Toast.LENGTH_LONG).show();
            Type = null;
        }
        return Type;
    }
    //Выбор собственного полупроводника
    public void onCheckClick(View view)
    {
        CheckBox check = (CheckBox) view;
        if (check.isChecked())
            GetType = "Собственный";
        else
            GetType = LastChoise;
    }
}