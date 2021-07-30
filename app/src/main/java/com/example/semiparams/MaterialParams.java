package com.example.semiparams;

import java.util.HashMap;

public class MaterialParams
{
    String nameMaterial;
    private String[] Dopings;
    private double[] activationEnergis;
    double effMass_electron, effMass_hole, mobility_electron, mobility_hole, Eg0, koefEg, dielectricConst;
    HashMap<String, Double> DopEnergisDict = new HashMap<>();

    {
        //Инициализатор???
    }

    MaterialParams(String nameMaterial)
    {
        this.nameMaterial = nameMaterial;
    }
    MaterialParams(String nameMaterial, String[] Dopings, double[] activationEnergis, double effMass_electron, double effMass_hole, double mobility_electron, double mobility_hole,double Eg0,double koefEg, double dielectricConst)
    {
        int len = Dopings.length;
        this.Dopings = new String[len];
        for (int i=0; i<len; i++)
            this.Dopings[i] = Dopings[i];

        len = activationEnergis.length;
        this.activationEnergis = new double[len];
        for (int i=0; i<len; i++)
            this.activationEnergis[i]=activationEnergis[i];

        this.nameMaterial = nameMaterial;
        this.effMass_electron = effMass_electron;
        this.effMass_hole = effMass_hole;
        this.mobility_electron = mobility_electron;
        this.mobility_hole = mobility_hole;
        this.Eg0 = Eg0;
        this.koefEg = koefEg;
        this.dielectricConst = dielectricConst;
    }

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
    public void setDopings (int NumberDopings, String[] Dopings)
    {
        this.Dopings = new String[NumberDopings];
        for (int i=0; i<NumberDopings; i++)
            this.Dopings[i] = Dopings[i];
    }
    public void setDopingsEnergy (int NumberDopings, double[] activationEnergis)
    {
        this.activationEnergis = new double[NumberDopings];
        for (int i=0; i<NumberDopings; i++)
            this.activationEnergis[i] = activationEnergis[i];
    }
    public void ActivateDict ()
    {
        for (int i=0; i<Dopings.length; i++)
            DopEnergisDict.put(Dopings[i], activationEnergis[i]);
    }
    public String getDopings(int i)
    {
        return this.Dopings[i];
    }
}
