//package com.kivi.esport.ConsoleUI;
//
//
//import com.kivi.esport.Database.SQLFunctions;
//import com.kivi.esport.Database.SQLProcedures;
//import com.kivi.esport.Database.SQLTable;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Scanner;
//
/**
 * Created on 22.05.2016.
 * This class used in first version of this program. Perhaps it will come in handy later
 */
//public class ConsoleMenu {
//
//    private SQLTable sqlT;
//    private SQLFunctions sqlF;
//    private SQLProcedures sqlP;
//
//    private Scanner scanner;
//    private int lastChoise;
//
//
//    ConsoleMenu(Connection con) {
//        sqlT = new SQLTable(con);
//        sqlF = new SQLFunctions(con);
//        sqlP = new SQLProcedures(con);
//
//        scanner = new Scanner(System.in);
//    }
//
//    public void start(){
//        menuCycle:{
//            while (true) {
//                System.out.println("ConsoleApplication menu:");
//                System.out.println("1) Print Table");
//                System.out.println("2) Use Procedures");
//                System.out.println("3) Use Functions");
//                System.out.println("0) Exit program");
//                lastChoise = getChoise();
//                switch (lastChoise) {
//                    case 1:
//                        getRSofTable();
//                        break;
//                    case 2:
//                        useProcedures();
//                        break;
//                    case 3:
//                        useFunctions();
//                        break;
//                    case 0:
//                        break menuCycle;
//                    default:
//                        System.out.println("You have selected nothing. Try again");
//                        break;
//                }
//            }
//        }
//        System.out.println("Program is closing");
//        return;
//    }
//
//    private void getRSofTable() {
//        System.out.println("Print table's name");
//        String s;
//        s = scanner.next();
//        getRSofTable(sqlT.getRSofTable(s));
//    }
//
//    private void useProcedures(){
//        System.out.println("Procedures menu:");
//        menuCycle:{
//            while (true) {
//                System.out.println("1) Add player");
//                System.out.println("2) Add team");
//                System.out.println("3) Get average skill points for best N players");
//                System.out.println("4) Get first N best players");
//                System.out.println("5) Add new entity in player roster table");
//
//                System.out.println("0) Back to main menu");
//                lastChoise = getChoise();
//                switch (lastChoise) {
//                    case 1:
//                        sqlP.addPlayer();
//                        break;
//                    case 2:
//                        sqlP.addTeam();
//                        break;
//                    case 3:
//                        sqlP.getAvgSPforNbestPlayers();
//                        break;
//                    case 4:
//                        sqlP.getBestNPlayers();
//                        break;
//                    case 5:
//                        sqlP.addPlayerRoster();
//                        break;
//                    case 0:
//                        break menuCycle;
//                    default:
//                        System.out.println("You have selected nothing. Try again");
//                        break;
//                }
//            }
//        }
//    }
//
//    private void useFunctions() {
//        System.out.println("Functions menu:");
//        menuCycle:{
//            while (true) {
//                System.out.println("1) Get Skill Points of Player");
//                System.out.println("0) Back to main menu");
//                lastChoise = getChoise();
//                switch (lastChoise) {
//                    case 1:
//                        getSkillPoints();
//                        break;
//                    case 0:
//                        break menuCycle;
//                    default:
//                        System.out.println("You have selected nothing. Try again");
//                        break;
//                }
//            }
//        }
//        return;
//    }
//
//    private void getSkillPoints(){
//        System.out.println("Print nickname");
//        String s;
//        s = scanner.next();
//        sqlF.getSkillPoints(s);
//    }
//
//    private int getChoise() {
//        Integer a;
//        a = Integer.parseInt(scanner.next());
//        return a;
//    }
//
//    public static void getRSofTable(ResultSet rs){
//        try {
//            int x = 0;
//            x = rs.getMetaData().getColumnCount();
//            while(rs.next()){
//                System.out.printf("%4s", rs.getString(1) + "|");
//                for(int i=2; i<=x;i++){
//                    if (rs.getString(i)!=null&&rs.getString(i).length()>19) {
//                        System.out.printf("%20s", rs.getString(i).substring(0, 19) + "|");
//                    }
//                    else{
//                        System.out.printf("%20s", rs.getString(i) + "|");
//                    }
//                }
//                System.out.println();
//            }
//            System.out.println();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
