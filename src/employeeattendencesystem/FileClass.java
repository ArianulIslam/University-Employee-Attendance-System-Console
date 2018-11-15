/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeeattendencesystem;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author NEW
 */
public class FileClass {
     File AttendenceFile = new File("AttendanceReport.txt");
    File TakeLeaveFile = new File("TakeLeaveReport.txt");
    File PresentFile = new File("PresentReport.txt");
    File absentFile = new File("AbsentReport.txt");
     File EmployeeFile = new File("EmployeeList.txt");
    ArrayList<String> AttendanceList = new ArrayList<String>();
    ArrayList<String> TakeLeaveList = new ArrayList<String>();
    ArrayList<String> PresentList = new ArrayList<String>();
    ArrayList<String> AbsentList = new ArrayList<String>();
     ArrayList<String> EmployeeList = new ArrayList<String>();
}
