/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group2.g2store;

import java.util.Date;

/**
 *
 * @author Luu Bao
 */
public class Statics {

    private String dateStatics;
    private String MonthStatics;
    private int YearStatics;
    private Float funds, revenue, profit;

    public Statics(String MonthStatics, int YearStatics, Float funds, Float revenue, Float profit) {
        this.MonthStatics = MonthStatics;
        this.YearStatics = YearStatics;
        this.funds = funds;
        this.revenue = revenue;
        this.profit = profit;
    }

    public String getMonthStatics() {
        return MonthStatics;
    }

    public void setMonthStatics(String MonthStatics) {
        this.MonthStatics = MonthStatics;
    }

    public Statics(String dateStatics, Float funds, Float revenue, Float profit) {
        this.dateStatics = dateStatics;
        this.funds = funds;
        this.revenue = revenue;
        this.profit = profit;
    }

   
    public Statics(int YearStatics, Float funds, Float revenue, Float profit) {
        this.YearStatics = YearStatics;
        this.funds = funds;
        this.revenue = revenue;
        this.profit = profit;
    }

    public Statics() {
        
    }
    
    

    public String getDateStatics() {
        return dateStatics;
    }

    public void setDateStatics(String dateStatics) {
        this.dateStatics = dateStatics;
    }

    public Float getFunds() {
        return funds;
    }

    public void setFunds(Float funds) {
        this.funds = funds;
    }

    public Float getRevenue() {
        return revenue;
    }

    public void setRevenue(Float revenue) {
        this.revenue = revenue;
    }

    public Float getProfit() {
        return profit;
    }

    public void setProfit(Float profit) {
        this.profit = profit;
    }
     public int getYearStatics() {
        return YearStatics;
    }

    public void setYearStatics(int YearStatics) {
        this.YearStatics = YearStatics;
    }

   

}
