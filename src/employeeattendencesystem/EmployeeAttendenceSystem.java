/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeeattendencesystem;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author NEW
 */
interface Attendence {

    void submitAttendence();

    String getAndTime();

    void showPersonalInfo();
    
     

}
 

interface LogIn  {

    boolean checkValidity(String user, String pass);
} 
 


class Student {

    private int id;
    private String name;
    private float cgpa;
    private String major;

     Student() {
    }

     Student(int i, String name, float cgpa, String major) {
        this.id = i;
        this.name = name;
        this.cgpa = cgpa;
        this.major = major;
    }

    public void setID(int x) {
        this.id = x;
    }

    public void setName(String st) {
        this.name = st;
    }

    public void setCgpa(float cg) {
        this.cgpa = cg;
    }

    public void setMajor(String st) {
        this.major = st;
    }

    int getID() {
        return id;
    }

    String getName() {
        return name;
    }

    float getCgpa() {
        return cgpa;
    }

    String getMajor() {
        return major;
    }

    void ShowStudentInfo() {
        
       System.out.println("Name : " + this.name);
       System.out.println("ID : "+this.id);
       System.out.println("Major : "+this.major);
       System.out.println("CGPA : "+this.cgpa);

    }

}

abstract class Employee implements Attendence, LogIn {
FileClass F = new FileClass();
    String Name, ID, Phone, Salary, Department;
     public void setID(String x) {
        this.ID = x;
    }

    public void setName(String st) {
        this.Name = st;
    }
    
    public void  setDepartment(String dp){
        this.Department = dp ;
        
    }
     public  String getID() {
        return this.ID;
    }

    public  String getName() {
       return this.Name;
    }
    
    public  String getDepartment(){
      return  this.Department ;
        
    }
    //All Abstract Mathod
    @Override
    public void submitAttendence(){
         
        
         
        try {
            PrintWriter writer = new PrintWriter(F.AttendenceFile);
            PrintWriter writer2 = new PrintWriter(F.PresentFile);
             
            writer.println(this.Name+" ; "+this.ID+ " ; Present "+  getAndTime()  );
            writer2.println(this.Name+" ; "+this.ID+ " ; Present "+  getAndTime()  );
             String at = this.Name+" ; "+this.ID+ " ; Present "+  getAndTime();
             F.AttendanceList.add(at);
             F.PresentList.add(at);
            writer.close();
              writer2.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
    }

    @Override
      public String getAndTime(){
        DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yy");
         Date dateobj = new Date();
        return df.format(dateobj);
    }

    @Override
    public abstract void showPersonalInfo();

   

    
    public boolean checkValidity(String user, String pass){
       
        File passFile = new File("password.txt");
        ArrayList<String> Bpassword = new ArrayList<String>();
        Bpassword.add("admin;admin");
        Bpassword.add("dean;dean");
        Bpassword.add("faculty;faculty");
        Bpassword.add("register;register");
        Bpassword.add("accountant;accountant");
        Bpassword.add("manager;manager");
        try{
        FileOutputStream fo = new FileOutputStream(passFile );
        ObjectOutputStream output = new ObjectOutputStream(fo);
          for( String a: Bpassword ){
            output.writeObject(a);
        }
          output.close();
          fo.close();
        }catch(Exception e){}
        ArrayList<String> passw = new ArrayList<String>();
       
        
        try{
        FileInputStream fi = new FileInputStream(passFile);
        ObjectInputStream input = new ObjectInputStream(fi);
       
       
            while(true)
            {
                String pas =  (String) input.readObject();
                 passw.add(pas);
            }
           
       
        
        }catch(Exception e){}
         String[] ar;
           
            for (String p : passw) {
                  String str = p;
                  ar = str.split(";");
                  if(ar[0].equals(user) && ar[1].equals(pass)){
                      return true;
                  }
               
            }
       
      
        
      return false;
    
    }

    //EmployeeClassOwnMathod
     void takeLeave(){
         
         Scanner read = new Scanner(System.in);
         System.out.println("You want take Leave today? Y\\N");
          String s = read.nextLine();
          
          if(s.equals("Y")){
               
         
        try {
           PrintWriter writer = new PrintWriter(F.TakeLeaveFile);
            writer.println(this.Name+" ; "+this.ID+ " ; TakeLeave "+  getAndTime()  );
            String tl=this.Name+" ; "+this.ID+ " ; TakeLeave "+  getAndTime() ;
            F.TakeLeaveList.add(tl);
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
              
          }
          else {
              
          }
     }

    public abstract void ShowSalary();

    void EditPersonalInfo(){
        System.out.println("You can edit your info");
    }
    
    
  

}

class Admin extends Employee {
 SignUp   adEmplyoee;
    Admin(){
        
    }
    Admin(String name,String EID,String department){
        
         Name = name;
         ID =EID;
         Department =department;
    }
 
    public void showPersonalInfo() {
        System.out.println("Name : "+Name);
        System.out.println("ID : "+ID);
        System.out.println("Department : "+Department);
    
 

    }
 
    public void ShowSalary() {
        
        System.out.println("Salary : 100000tk");

    }

    public void EditPersonalInfo() {
     System.out.println("You can edit your info");
    }

    void viewAttendenceReport() {
         System.out.println("Attendace Report : ");
        for(String va : F.AttendanceList){
            System.out.println(va);
        }
         
        

    }

    void viewTakeLeaveReport() {
        System.out.println("Takeleave Report : ");
        for(String Tl : F.TakeLeaveList){
             System.out.println(Tl);
        }
    }

    void ViewAbsentReport() {
    System.out.println("No absent Report Generated");
    }

    void ViewPresentReport() {
    System.out.println("Present Report : ");
        for(String p : F.PresentList){
             System.out.println(p);
        }
    }


     void AddEmployee() throws FileNotFoundException{
        adEmplyoee = new SignUp();
        adEmplyoee.CreatAccount();
    }

 
  

}
 class SignUp {

    private String Name, Id, Department, Phone, Address,  BloodGroup;

    SignUp() {

    }

    SignUp(String name, String id, String department, String phone, String address) {

        Name = name;
        Id = id;
        Department = department;
        Phone = phone;
        Address = address;
        

    }

    void SetName(String name) {
        this.Name = name;
    }

    String GetName() {
        return this.Name;
    }

    void SetId(String id) {
        this.Id = id;
    }

    String GetId() {
        return this.Id;
    }

    void SetDepartment(String department) {
        this.Department = department;

    }

    String GetDepartment() {
        return this.Department;
    }

    void SetPhoneNumber(String phone) {
        this.Phone = phone;
    }

    String GetPhoneNumber() {
        return this.Phone;
    }

    void SetAddress(String address) {
        this.Address = address;
    }

    String GetAddress() {
        return this.Address;
    }

     
 

    void  CreatAccount() throws FileNotFoundException {
        File CreatNewAccountFile = new File("NewEmployeeList.txt");
        PrintWriter writer = new PrintWriter(CreatNewAccountFile);
        Scanner read = new Scanner(System.in);
         System.out.println("Enter Name : ");
         this.SetName(read.nextLine());
         System.out.println("Enter Employee ID : ");
         this.SetId(read.nextLine());
         System.out.println("Enter Department : ");
         this.SetDepartment(read.nextLine());
         System.out.println("Enter Phone : ");
         this.SetPhoneNumber(read.nextLine());
         System.out.println("Enter Adress : ");
         this.SetAddress(read.nextLine());
         writer.println(this.Name+";"+this.Id + ";"+this.Department +" ; "+ this.Phone+";"+this.Address);
         writer.close(); 
         

    }
}
class Register extends Employee {
   
    SignUp   adEmplyoee;
       
 
   Register(String name,String EID,String department){
        
         Name = name;
         ID =EID;
         Department =department;
   }

    public void showPersonalInfo() {
        
        System.out.println("Name : "+Name);
        System.out.println("ID : "+ID);
        System.out.println("Department : "+Department);
    }

    public void ShowSalary() {
            System.out.println("Salary : 80000tk");
    }

    public void EditPersonalInfo() {
        System.out.println("You can edit your info");
    }
     
    void EditEmployee(){
        
    }
    void AddEmployee() throws FileNotFoundException{
        adEmplyoee = new SignUp();
        adEmplyoee.CreatAccount();
    }
    void EditStudentInfo(){
          Scanner read = new Scanner(System.in);
        Student std = new Student();
        std.setName(read.nextLine());
        std.setID(read.nextInt());
        std.setCgpa(read.nextFloat());
        read.nextLine();
        std.setMajor(read.nextLine());
    }
    
}

class SchoolManger extends Employee {

    SchoolManger(){
        
    }
  SchoolManger(String name,String EID,String department){
        
         Name = name;
         ID =EID;
         Department =department;
   }
    
    public void showPersonalInfo() {
    
        System.out.println("Name : "+Name);
        System.out.println("ID : "+ID);
        System.out.println("Department : "+Department);
    }
 
 
    public void ShowSalary() {
        System.out.println("Salary : 75000tk");
    }

    public void EditPersonalInfo() {
       System.out.println("You can edit your info");
    }

  
  
    
    
}

class Accountant extends Employee {

  Accountant(){
      
  }
  Accountant(String name,String EID,String department){
        
         Name = name;
         ID =EID;
         Department =department;
   }
    
    public void showPersonalInfo() {
    
        System.out.println("Name : "+Name);
        System.out.println("ID : "+ID);
        System.out.println("Department : "+Department);
    }
 

    public void ShowSalary() {
    System.out.println("Salary : 70000tk");
    }

    public void EditPersonalInfo() {
       System.out.println("You can edit your Information");
    }
    
    void PaySalary(){
        System.out.println("you can Generat PaySlip");
    }
    void ViewPayment(){
        System.out.println("you can ViewPayment History");
    }

 
}

class Faculty extends Employee {

  Faculty(){
      
  }
   Faculty(String name,String EID,String department){
        
         Name = name;
         ID =EID;
         Department =department;
   }
    
    public void showPersonalInfo() {
    
        System.out.println("Name : "+Name);
        System.out.println("ID : "+ID);
        System.out.println("Department : "+Department);
    }

    public void ShowSalary() {
        System.out.println("Salary : 68000tk");
    }

    public void EditPersonalInfo() {
      System.out.println("You can edit Your Personal Info");
    }
    void ShowStudentInfo(){
        Student std = new Student();
        std.ShowStudentInfo();
    }
   void ViewAllClassSchedule(){
         System.out.println("You class Schedule :\n CSC101->Section->4->ST->11.20pm\n CSC203->Section->1->MW->1.30pm\n");
    }
void TakeAttendence(){
        System.out.println("You can Student Attendance");
    }
  
}

class Dean extends Faculty {

    
Dean(){
    
}
  
    Dean(String name,String EID,String department){
        
         Name = name;
         ID =EID;
         Department =department;
   }
    
    public void showPersonalInfo() {
    
        System.out.println("Name : "+Name);
        System.out.println("ID : "+ID);
        System.out.println("Department : "+Department);
    }
 
 

    public void ShowSalary() {
        System.out.println("Salary : 90000tk");
    }

    public void EditPersonalInfo() {
       System.out.println("You can edit Your Personal Info");
    }
    void TakeAttendence(){
        System.out.println("You can Student Attendance");
    }
    void ShowStudentInfo(){
        Student std = new Student(1522435,"Ariyan",3.02f,"CSE");
        std.ShowStudentInfo();
    }
    void ViewAllClassSchedule(){
        System.out.println("You Have no class Today");
    }
    void ViewCourseList(){
       System.out.println("You can see all offer courses");
   }
    void AddnewCourse(){
        System.out.println("You can add new courses");
    }
    
    
}

class Head extends Faculty {

    Head(){
        
    }
        Head(String name,String EID,String department){
        
         Name = name;
         ID =EID;
         Department =department;
   }
    
    public void showPersonalInfo() {
    
        System.out.println("Name : "+Name);
        System.out.println("ID : "+ID);
        System.out.println("Department : "+Department);
    }
 

    public void ShowSalary() {
     System.out.println("Salary : 80000tk");
    }

    public void EditPersonalInfo() {
     System.out.println("You can edit Your Personal Info");
    }
     void TakeAttendence(){
        System.out.println("You can Take Student Attendance");
    }
    void ShowStudentInfo(){
        Student std = new Student(1522435,"Ariyan",3.02f,"CSE");
        std.ShowStudentInfo();
        
    }
   void ViewAllClassSchedule(){
        System.out.println("You class Schedule :\n CSC101->Section->4->MW->11.20pm\n CSC203->Section->1->ST->1.30pm\n");
    }
   void ViewCourseList(){
       System.out.println("You can see all offer courses");
   }
}

public class EmployeeAttendenceSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner read = new Scanner(System.in);
        // TODO code application logic here
       // SchoolManger em = new  SchoolManger();
       // em.setID("15");
       // em.setName("Ariyan");
       // em.submitAttendence();
        //1. Select Group :  1.admin 2.Dean 3.Head 4.Faculty 5.School Manager 6.Register 7.quit
        //try log in
        //1.show all menu refer to group he/she belong to.
        //SignUp up = new SignUp();
        //up.CreatAccount();
        String username;
        String password;
        System.out.println("1.Admin\n2.Register\n3.School Manager\n4.Accountant\n5.Dean\n6.Faculty\n7.Head\n8)quit");
        System.out.println("Choose Option : ");
        int option = read.nextInt();
        read.nextLine();
        switch(option){
         
            case 1 : Admin ad = new Admin("Dr.Salim Rahman","012","Management");
                     System.out.println("LogIn Panel");
                     System.out.println("Enter username: ");
                     username =read.nextLine();

                     System.out.println("Enter password: ");
                     password = read.nextLine();
                     int n;
                     if(ad.checkValidity(username, password)==true){
                          System.out.println("You are logged in");
                          do{
                          System.out.println("Menu :\n1)Show Info \n2)Edit Info \n3)Submit Attendace\n4)Take Leave \n5)ViewReport \n6)quit\n");
                           n =read.nextInt();
                          switch(n){
                                case  1 :
                                    ad.showPersonalInfo();
                                    ad.ShowSalary();
                                    
                                    
                                    break;
                                case 2 : 
                                    ad.EditPersonalInfo();
                                    break;
                                case 3 :
                                    ad.submitAttendence();
                                    break;
                                case 4 :
                                    ad.takeLeave();
                                    break;
                                    
                                case 5 :
                                    ad.viewAttendenceReport();
                                    ad.ViewAbsentReport();
                                    ad.ViewPresentReport();
                                    ad.takeLeave();
                                    break;
                                case 6 : break;
                                
                              
                              
                          }
                            
                          
                          }while(n!=6);
                         
                     }
                     else {
                          System.out.println("Wrong Password try again.");
                     }
                      
                
                break;
            case 2 :
                 Register r = new Register("Dr.Mahbub Alam","0133","Management");
                     System.out.println("LogIn Panel");
                     System.out.println("Enter username: ");
                     username =read.nextLine();

                     System.out.println("Enter password: ");
                     password = read.nextLine();
                     int n1;
                     if(r.checkValidity(username, password)==true){
                          System.out.println("You are logged in");
                          do{
                          System.out.println("Menu :\n1)Show Info \n2)Edit Info \n3)Submit Attendace\n4)Take Leave \n5)Add Employee \n 6)Edit Student Info \n7)quit\n");
                           n1 =read.nextInt();
                          switch(n1){
                                case  1 :
                                    r.showPersonalInfo();
                                    r.ShowSalary();
                                    
                                    
                                    break;
                                case 2 : 
                                    r.EditPersonalInfo();
                                    break;
                                case 3 :
                                    r.submitAttendence();
                                    break;
                                case 4 :
                                    r.takeLeave();
                                    break;
                                    
                                case 5 :
                                    r.AddEmployee();
                                    break;
                                case 6 : 
                                    r.EditStudentInfo();
                                    break;
                                case 7 : break;
                              
                              
                          }
                            
                          
                          }while(n1!=7);
                         
                     }
                     else {
                          System.out.println("Wrong Password try again.");
                     }
                
                
                break;
            case 3 :   SchoolManger m = new SchoolManger("Dr.Naim Hossain","0123","Management");
                     System.out.println("LogIn Panel");
                     System.out.println("Enter username: ");
                     username =read.nextLine();

                     System.out.println("Enter password: ");
                     password = read.nextLine();
                     int n2;
                     if(m.checkValidity(username, password)==true){
                          System.out.println("You are logged in");
                          do{
                          System.out.println("Menu :\n1)Show Info \n2)Edit Info \n3)Submit Attendace\n4)Take Leave \n5)Quit\n");
                           n2 =read.nextInt();
                          switch(n2){
                                case  1 :
                                    m.showPersonalInfo();
                                    m.ShowSalary();
                                    
                                    
                                    break;
                                case 2 : 
                                    m.EditPersonalInfo();
                                    break;
                                case 3 :
                                    m.submitAttendence();
                                    break;
                                case 4 :
                                    m.takeLeave();
                                    break;
                                case 5 :break;
                              
                              
                          }
                            
                          
                          }while(n2!=7);
                         
                     }
                     else {
                          System.out.println("Wrong Password try again.");
                     }
                
                
                
                break;
            case 4 :Accountant a = new Accountant ("Mahbub Rahman jayan","01233","Account");
                     System.out.println("LogIn Panel");
                     System.out.println("Enter username: ");
                     username =read.nextLine();

                     System.out.println("Enter password: ");
                     password = read.nextLine();
                     int n3;
                     if(a.checkValidity(username, password)==true){
                          System.out.println("You are logged in");
                          do{
                          System.out.println("Menu :\n1)Show Info \n2)Edit Info \n3)Submit Attendace\n4)Take Leave \n5)View Pay History\n6)Quit\n");
                           n3 =read.nextInt();
                          switch(n3){
                                case  1 :
                                    a.showPersonalInfo();
                                    a.ShowSalary();
                                    
                                    
                                    break;
                                case 2 : 
                                    a.EditPersonalInfo();
                                    break;
                                case 3 :
                                    a.submitAttendence();
                                    break;
                                case 4 :
                                    a.takeLeave();
                                    break;
                                case 5 :
                                    a.ViewPayment();
                                    break;
                                case 6 :break;
                              
                              
                          }
                            
                          
                          }while(n3!=7);
                         
                     }
                     else {
                          System.out.println("Wrong Password try again.");
                     }
                
                
                break;
            case 5 : Dean d = new  Dean ("Rajia Sultana Shimu","01213","Engineering");
                     System.out.println("LogIn Panel");
                     System.out.println("Enter username: ");
                     username =read.nextLine();

                     System.out.println("Enter password: ");
                     password = read.nextLine();
                     int n4;
                     if(d.checkValidity(username, password)==true){
                          System.out.println("You are logged in");
                          do{
                          System.out.println("Menu :\n1)Show Info \n2)Edit Info \n3)Submit Attendace\n4)Take Leave \n5)Add and View Course\n6)Show Student info \n7)Quit\n");
                           n4 =read.nextInt();
                          switch(n4){
                                case  1 :
                                    d.showPersonalInfo();
                                    d.ShowSalary();
                                    
                                    
                                    break;
                                case 2 : 
                                    d.EditPersonalInfo();
                                    break;
                                case 3 :
                                    d.submitAttendence();
                                    break;
                                case 4 :
                                    d.takeLeave();
                                    break;
                                case 5 :
                                    d.AddnewCourse();
                                    d.ViewCourseList();
                                    break;
                                case 6 :
                                    d.ShowStudentInfo();
                                    d.TakeAttendence();
                                    break;
                                case 7 :
                                    
                                    break;
                              
                              
                              
                          }
                            
                          
                          }while(n4!=7);
                         
                     }
                     else {
                          System.out.println("Wrong Password try again.");
                     }
                
                
                break;
            case 6 : Faculty f = new  Faculty ("Javed Omar","01113","Engineering");
                     System.out.println("LogIn Panel");
                     System.out.println("Enter username: ");
                     username =read.nextLine();

                     System.out.println("Enter password: ");
                     password = read.nextLine();
                     int n5;
                     if(f.checkValidity(username, password)==true){
                          System.out.println("You are logged in");
                          do{
                          System.out.println("Menu :\n1)Show Info \n2)Edit Info \n3)Submit Attendace\n4)Take Leave \n5)Course Schedule\n6)Show Student info \n7)Quit\n");
                           n5 =read.nextInt();
                          switch(n5){
                                case  1 :
                                    f.showPersonalInfo();
                                    f.ShowSalary();
                                    
                                    
                                    break;
                                case 2 : 
                                    f.EditPersonalInfo();
                                    break;
                                case 3 :
                                    f.submitAttendence();
                                    break;
                                case 4 :
                                    f.takeLeave();
                                    break;
                                case 5 :
                                    f.ViewAllClassSchedule();
                                    
                                    break;
                                case 6 :
                                    f.ShowStudentInfo();
                                    f.TakeAttendence();
                                    break;
                                case 7 :
                                    
                                    break;
                              
                              
                              
                          }
                            
                          
                          }while(n5!=7);
                         
                     }
                     else {
                          System.out.println("Wrong Password try again.");
                     }
                
                
                break;
                
               
            case 7 :
                Head h = new  Head ("Rajia Sultana Shimu","01213","Engineering");
                     System.out.println("LogIn Panel");
                     System.out.println("Enter username: ");
                     username =read.nextLine();

                     System.out.println("Enter password: ");
                     password = read.nextLine();
                     int n6;
                     if(h.checkValidity(username, password)==true){
                          System.out.println("You are logged in");
                          do{
                          System.out.println("Menu :\n1)Show Info \n2)Edit Info \n3)Submit Attendace\n4)Take Leave \n5) View Course\n6)Show Student info \n7)Quit\n");
                           n6 =read.nextInt();
                          switch(n6){
                                case  1 :
                                    h.showPersonalInfo();
                                    h.ShowSalary();
                                    
                                    
                                    break;
                                case 2 : 
                                    h.EditPersonalInfo();
                                    break;
                                case 3 :
                                    h.submitAttendence();
                                    break;
                                case 4 :
                                    h.takeLeave();
                                    break;
                                case 5 :
                                     
                                    h.ViewCourseList();
                                    break;
                                case 6 :
                                    h.ShowStudentInfo();
                                    h.TakeAttendence();
                                    break;
                                case 7 :
                                    
                                    break;
                              
                              
                              
                          }
                            
                          
                          }while(n6!=7);
                         
                     }
                     else {
                          System.out.println("Wrong Password try again.");
                     }
                
                
                break;
            case 8 : break;
            
        
        }

    

}
}