package com.example.semiparams;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MaterialParams
{
    private String nameMaterial;
    private String[] Dopings;
    private double[] activationEnergis;
    private double effMass_electron, effMass_hole, mobility_electron, mobility_hole, Eg0, koefEg, dielectricConst;
    private HashMap<String, Double> DopEnergisDict = new HashMap<>();
    private Context context;
    private String PATH = "/data/data/com.example.semiparams/files/";

    {
        //Инициализатор???
    }

    MaterialParams(String nameMaterial, Context context)
    {
        this.nameMaterial = nameMaterial;
        this.context = context;
        ParamsReader(PATH + nameMaterial + ".txt");
        ActivateDict();
    }

    //Методы для получения параметров, зависящих от температуры
    public double Dn(double T)
    {
        return SemiConstants.Bolcman*T*mobility_electron;
    }
    public  double Dp(double T)
    {
        return SemiConstants.Bolcman*T*mobility_hole;
    }
    public double Eg(double T)
    {
        return Eg0-koefEg*1e-4*T;
    }

    //Внутренние методы для создания полноценного объекта материала
    private void setDopings (int NumberDopings, String[] Dopings)
    {
        this.Dopings = new String[NumberDopings];
        for (int i=0; i<NumberDopings; i++)
            this.Dopings[i] = Dopings[i];
    }
    private void setDopingsEnergy (int NumberDopings, double[] activationEnergis)
    {
        this.activationEnergis = new double[NumberDopings];
        for (int i=0; i<NumberDopings; i++)
            this.activationEnergis[i] = activationEnergis[i];
    }
    private void ActivateDict ()
    {
        for (int i=0; i<Dopings.length; i++)
            DopEnergisDict.put(Dopings[i], activationEnergis[i]);
    }
    private void ParamsReader (String PathToFile)
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
            setDopings(Dopings.size(),dopings);
            setDopingsEnergy(Dopings.size(),energy);
            //Заполняем оставшиеся параметры
            effMass_electron = Double.parseDouble(bufRead.readLine());
            effMass_hole = Double.parseDouble(bufRead.readLine());
            mobility_electron = Double.parseDouble(bufRead.readLine());
            mobility_hole = Double.parseDouble(bufRead.readLine());
            Eg0 = Double.parseDouble(bufRead.readLine());
            koefEg = Double.parseDouble(bufRead.readLine());
            dielectricConst = Double.parseDouble(bufRead.readLine());
            //Проверка после считывания и активация словаря примесь-энергия
            if (dielectricConst !=0 && (bufRead.readLine())==null)
            {
                Toast.makeText(context, nameMaterial+" загружен", Toast.LENGTH_SHORT).show();
                ActivateDict();
            }
            else
            {
                Toast.makeText(context, "Неверный формат файла параметров материала!", Toast.LENGTH_LONG).show();
            }
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(context, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Toast.makeText(context, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //Методы для получения разнообразных парметров материала
    public ArrayList<String> getDopings() {
        ArrayList<String> Type = new ArrayList<>();
        for (int i=0; i<numberOfDopings(); i++)
        {
            Type.add(Dopings[i]);
        }
        return Type;
    }
    public int numberOfDopings()
    {
        return DopEnergisDict.size();
    }
    public Double EnergyOfDopant (String Dopant)
    {
        return DopEnergisDict.get(Dopant);
    }
    public String GiveTypeOfDopant (String Dopant) {
        String Type = "";
        String s;
        String path = PATH + "Dopings_" + nameMaterial;
        ArrayList<String> Dopings_n = new ArrayList<String>();
        ArrayList<String> Dopings_p = new ArrayList<String>();

        try (BufferedReader bufReader = new BufferedReader(new FileReader(path+"_n.txt")))
        {
            while ((s=bufReader.readLine())!=null)
                Dopings_n.add(s);
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try (BufferedReader bufReader = new BufferedReader(new FileReader(path+"_p.txt")))
        {
            while ((s=bufReader.readLine())!=null)
                Dopings_p.add(s);
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (Dopings_n.indexOf(Dopant)!=-1)
        {
            Toast.makeText(context, "N-тип", Toast.LENGTH_SHORT).show();
            Type = "N_Type";
        }
        else if (Dopings_p.indexOf(Dopant)!=-1)
        {
            Toast.makeText(context, "P-тип", Toast.LENGTH_SHORT).show();
            Type = "P_Type";
        }
        else
        {
            Toast.makeText(context, "Примесь не найдена!", Toast.LENGTH_LONG).show();
            Type = null;
        }
        return Type;
    }
    public Double electronEffMass() {return effMass_electron;}
    public Double holeEffMass() {return effMass_hole;}
    public Double electronMobility() {return mobility_electron;}
    public Double holeMobility() {return mobility_hole;}
}
