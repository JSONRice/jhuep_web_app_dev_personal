package test;

import java.util.Scanner;
class AmortizationProgram{
 
  public static void main(String[] args){
     double p,iy;
     int n;
     Scanner sc=new Scanner(System.in);
     System.out.print("Enter amount of loan:");
     p=sc.nextFloat();
     System.out.print("Enter interest rate per year:");
     iy=sc.nextFloat();
     System.out.print("Enter number of years:");
     n=sc.nextInt();    
     calAmort(p,iy,n);
     sc.close();
  }

  public static void calAmort(double p,double iy, int ny){
     double newbal;
     double im=(iy/12)/100;
     int nm=ny*12;
     double mp,ip,pp;
     int i;
   
     mp=p*im*Math.pow(1+im,(double)nm)/(Math.pow(1+im,(double)nm)-1);
     printHeader();
     //print amortization schedule for all months except the last month
     for(i=1;i<nm;i++){    
        ip=p*im;//interest paid
        pp=mp-ip; //principal paid
        newbal=p-pp; //new balance                
        printSch(i,p,mp,ip,pp,newbal);
        p=newbal;  //update old balance
     }
     //last month
     pp=p;
     ip=p*im;
     mp=pp+ip;
     newbal=0.0;
     
     printSch(i,p,mp,ip,pp,newbal);   
  }
 
  public static void printSch(int i,double p,double mp,double ip,double pp,double newbal){ 
     System.out.format("%-8d%-12.3f%-10.3f%-10.3f%-10.3f%-12.3f\n",i,p,mp,ip,pp,newbal);
  }

  public static void printHeader(){
      int i;
      System.out.println("\nAmortization Schedule for  Borrower");
      for(i=0;i<62;i++)  System.out.print("-");
      System.out.format("\n%-8s%-12s%-10s%-10s%-10s%-12s"," ","Old","Monthly","Interest","Principle","New","Balance");
      System.out.format("\n%-8s%-12s%-10s%-10s%-10s%-12s\n\n","Month","Balance","Payment","Paid","Paid","Balance");
   } 
}