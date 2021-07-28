package com.example.semiparams;

public class MaterialParams
{
    String nameMaterial;
    String[] Dopings;
    double[] activationEnergis;
    double effMass_electron, effMass_hole, mobility_electron, mobility_hole, Eg0, koefEg, dielectricConst;

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
}
