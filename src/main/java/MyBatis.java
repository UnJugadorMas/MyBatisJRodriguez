//Imports
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.sql.Date;
import java.io.Reader;
import java.text.ParseException;
import java.util.Scanner;

public class MyBatis {
    public static SqlSessionFactory sqlMapper;


    //call mybatis.xml for the make the connection
    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.xml");
            sqlMapper = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception x) {
            //error no connection database
            throw new RuntimeException("Sorry is not possible the access in the Database" + x, x);
        }
    }

    public static void main(String[] args) throws ParseException {
        //Open the Session
        SqlSession session = sqlMapper.openSession();
        Scanner sc = new Scanner(System.in);

        sqlMapper.getConfiguration().addMapper(Mapper.class);
        Mapper mapper = session.getMapper(Mapper.class);


        boolean keep = true;

    while (keep){
            //1 question
            System.out.print("Enter the action you want to perfom --> select | insert | modify | delete : ");
            String action = sc.nextLine();

            //SELECT
            if (action.equalsIgnoreCase("select")) {
                System.out.print("Enter the group --> people | teams | sports : ");
                String group1 = sc.nextLine();

                //select people
                if (group1.equals("people")) {
                    //question
                    System.out.print("Insert the DNI of the player: ");
                    People p = mapper.getPerson(sc.nextLine());
                    Nationalities n = mapper.getNationality(p.getNationality());
                    System.out.printf(
                            "*-------------*----------*-------------------*-------------------*-------------------*----------*-------*------------------------*\n|         Dni |   Dorsal |              Name |         Surname 1 |         Surname 2 |   Gender |   Age |            Nationality |\n*-------------*----------*-------------------*-------------------*-------------------*----------*-------*------------------------*\n|  %9s  |    %2d    |  %15s  |  %15s  |  %15s  |  %6s  |   %2s  |  %20s  |\n*-------------*----------*-------------------*-------------------*-------------------*----------*-------*------------------------*",
                            p.getDNI(), p.getDorsals(), p.getName(), p.getSurname1(), p.getSurname2(), p.getSex(), p.getAge(), n.getName());

                    //select teams
                } else if (group1.equals("teams")) {
                    System.out.print("Insert the ID of the team: ");
                    Teams t = mapper.getTeam(sc.nextInt());
                    System.out.println("| ID Team: " + t.getId() + " | Name: " + t.getName() + " | ID_Nationality: "
                            + t.getNationality() + " | Gender: " + t.getGender() + " |");

                    //select sports
                } else if (group1.equals("sports")) {
                    System.out.print("Insert the ID of the sport: ");
                    Sports s = mapper.getSport(sc.nextInt());
                    System.out.println("| ID Sport: " + s.getId() + " | Name: " + s.getName() + " |");
                    //error selecting
                } else
                    System.out.println("Invalid group.");

                //INSERT
            } else if (action.equals("insert")) {
                //question
                System.out.print("Enter the group --> people | teams | sports : ");
                String group2 = sc.nextLine();

                //insert player
                if (group2.equals("people")) {
                    System.out.print("DNI: ");
                    String dni = sc.nextLine();
                    System.out.print("Dorsal: ");
                    int dorsal = sc.nextInt();
                    String alert = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Surname: ");
                    String surname = sc.nextLine();
                    System.out.print("Surname2: ");
                    String surname2 = sc.nextLine();
                    System.out.print("Age: ");
                    int age = sc.nextInt();
                    String alert1 = sc.nextLine();
                    System.out.print("Gender: ");
                    String sex = sc.nextLine();
                    System.out.print("ID_Nationality: ");
                    int nat = sc.nextInt();
                    System.out.print("Competitions Wins: ");
                    int comp = sc.nextInt();
                    System.out.print("Salary: ");
                    int salary = sc.nextInt();
                    System.out.print("ID_Type: ");
                    int type = sc.nextInt();
                    System.out.println("DATES:");
                    String dates = sc.nextLine();

                    //Debut
                    System.out.println("Debut date (YYYY/MM/DD): ");
                    String entradaDeb = sc.nextLine();
                    Date debut = Date.valueOf(entradaDeb);

                    //retired
                    System.out.println("Retired date (YYYY/MM/DD): ");
                    String entradaRet = sc.nextLine();
                    Date retired = Date.valueOf(entradaRet);

                    //sport
                    System.out.println("Sport ID: ");
                    int sport = sc.nextInt();

                    //team
                    System.out.println("Team ID: ");
                    int team = sc.nextInt();
                    People insP = new People(dni, dorsal, name, surname, surname2, age, sex, nat, comp, salary, type, debut, retired, sport, team);
                    mapper.insertPerson(insP);
                    //added
                    System.out.println("People successfully added");

                    //Insert team
                } else if (group2.equals("teams")) {
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    String alert = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("ID_Nationality: ");
                    int nat = sc.nextInt();
                    String alert2 = sc.nextLine();
                    System.out.print("Gender: ");
                    String sex = sc.nextLine();
                    Teams insT = new Teams(id, name, nat, sex);
                    mapper.insertTeam(insT);
                    System.out.println("Team successfully added");

                } else if (group2.equals("sports")) {
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    Sports insS = new Sports(id, name);
                    mapper.insertSport(insS);
                    System.out.println("Sport successfully added");
                } else
                    //error insert
                    System.out.println("Incorrect group.");

                //MODIFY
             } else if (action.equals("modify")) {
                    System.out.print("Enter the group --> people | teams | sports : ");
                    String group = sc.next();

                    if (group.equals("people")) {
                            System.out.print("Insert player's DNI: ");
                            People p = mapper.getPerson(sc.next());
                            if (p != null) {
                                if (p.getSurname2() == null) p.setSurname2("");
                                Nationalities n = mapper.getNationality(p.getNationality());
                                System.out.printf(
                                        "*-------------*----------*-------------------*-------------------*-------------------*----------*-------*------------------------*--------------*-------------*--------*---------------------------------*---------------------------------+\n|     DNI     |  DORSAL  |       NAME        |   FIRST SURNAME   |  SECOND SURNAME   |  GENDER  |  AGE  |      NATIONALITY       |  COMP. WINS  |   SALARY    |  TYPE  |              DEBUT              |             RETIRED             |\n*-------------*----------*-------------------*-------------------*-------------------*----------*-------*------------------------*--------------*-------------*--------*---------------------------------*---------------------------------*\n|  %9s  |    %2d    |  %15s  |  %15s  |  %15s  |  %6s  |   %2s  |  %20s  |  %10d  |  %9d  |  %4d  |  %29s  |  %29s  |\n*-------------*----------*-------------------*-------------------*-------------------*----------*-------*------------------------*--------------*-------------*--------*---------------------------------*---------------------------------*\n",
                                        p.getDNI(), p.getDorsals(), p.getName(), p.getSurname1(), p.getSurname2(), p.getSex(), p.getAge(), n.getName(), p.getCompetitions_wins(), p.getSalary(), p.getType(), p.getDebut(), p.getRetired());

                                System.out.print("Dorsal: ");
                                int dorsal = sc.nextInt();
                                System.out.print("Name: ");
                                String name = sc.next();
                                System.out.print("Surname: ");
                                String surname = sc.next();
                                System.out.print("Surname2: ");
                                String surname2 = sc.next();
                                System.out.print("Age: ");
                                int age = sc.nextInt();
                                System.out.print("Gender: ");
                                String sex = sc.next();
                                System.out.print("ID_Nationality: ");
                                int nat = sc.nextInt();
                                System.out.print("Competitions Wins: ");
                                int comp = sc.nextInt();
                                System.out.print("Salary: ");
                                int salary = sc.nextInt();
                                System.out.println("Debut date (YYYY-MM-DD): ");
                                String debutDate = sc.next();
                                Date debut = Date.valueOf(debutDate);
                                System.out.println("Retired date (YYYY-MM-DD): ");
                                String retiredDate = sc.next();
                                Date retired = Date.valueOf(retiredDate);
                                System.out.println("Sport ID: ");
                                int sport = sc.nextInt();
                                System.out.println("Team ID: ");
                                int team = sc.nextInt();

                                mapper.updatePerson(p);
                                System.out.println("Player modified");

                            } else
                                System.out.println("The player's DNI is incorrect or doesn't exists");

                    }  else if (group.equals("sports")) {
                        System.out.println("Insert sport's ID: ");
                        int id = sc.nextInt();

                        Sports s = mapper.getSport(id);
                        if (s != null) {
                            System.out.printf(
                                    "*--------*-------------------*\n|   ID   |       NAME        |\n*--------*-------------------*\n|  %4d  |  %15s  |\n*--------*-------------------*\n",
                                    s.getId(), s.getName());

                            System.out.println("Name: ");
                            String name = sc.next();

                            Sports s2 = new Sports(s.getId(), name);
                            mapper.updateSport(s2);
                            System.out.println("Sport modified");

                        } else
                            System.out.println("The sport's ID is incorrect or doesn't exists");

                    } else
                        System.out.println("Invalid group.");


            //delete
            } else if (action.equals("delete")) {
                System.out.print("Enter the group --> people | teams | sports : ");
                String group = sc.next();

                if (group.equals("people")) {
                    System.out.print("Insert person's DNI: ");
                    People p = mapper.getPerson(sc.next());
                    if (p != null) {
                        if (p.getSurname2() == null) p.setSurname2("");
                        Nationalities n = mapper.getNationality(p.getNationality());
                        System.out.printf(
                                "*-------------*----------*-------------------*-------------------*-------------------*----------*-------*------------------------*--------------*-------------*--------*---------------------------------*--------------------------------*\n|     DNI     |  DORSAL  |       NAME        |   FIRST SURNAME   |  SECOND SURNAME   |  GENDER  |  AGE  |      NATIONALITY       |  COMP. WINS  |   SALARY    |  TYPE  |              DEBUT              |            RETIRED             |\n*-------------*----------*-------------------*-------------------*-------------------*----------*-------*------------------------*--------------*-------------*--------*---------------------------------*--------------------------------*\n|  %9s  |    %2d    |  %15s  |  %15s  |  %15s  |  %6s  |   %2s  |  %20s  |  %10d  |  %9d  |  %4d  |  %28s  |  %28s  |\n*-------------*----------*-------------------*-------------------*-------------------*----------*-------*------------------------*--------------*-------------*--------*---------------------------------*--------------------------------*\n",
                                p.getDNI(), p.getDorsals(), p.getName(), p.getSurname1(), p.getSurname2(), p.getSex(), p.getAge(), n.getName(), p.getCompetitions_wins(), p.getSalary(), p.getType(), p.getDebut());

                        p.getDNI();

                    } else
                        System.out.println("The player's DNI is incorrect or doesn't exists");

                    System.out.println("Are you sure to delete the player? ALERT: This action has no turning back (yes / no)");
                    String conf = sc.next();

                    if (conf.equals("yes")) {
                        mapper.deletePerson(p);
                        System.out.println("Person deleted");

                    } else if (conf.equals("no"))
                        System.out.println("Person not deleted");

                    else
                        System.out.println("Confirmation rejected, person not deleted");

                } else if (group.equals("teams")) {
                    System.out.print("Insert ID of team: ");
                    Teams t = mapper.getTeam(sc.nextInt());
                    if (t != null) {
                        Nationalities n = mapper.getNationality(t.getNationality());
                        System.out.printf(
                                "+--------+-----------------------------+------------------------+----------+\n|   ID   |            NAME             |      NATIONALITY       |  GENDER  |\n+--------+-----------------------------+------------------------+----------+\n|  %4d  |  %25s  |  %20s  |  %6s  |\n+--------+-----------------------------+------------------------+----------+\n",
                                t.getId(), t.getName(), n.getName(), t.getGender());
                    } else
                        System.out.println("Invalid ID");

                    System.out.println("Are you sure to delete the team? ALERT: This action has no turning back (yes / no)");
                    String conf = sc.next();

                    if (conf.equals("yes")) {
                        mapper.deleteTeam(t.getId());
                        System.out.println("Team deleted");
                    } else if (conf.equals("no"))
                        System.out.println("Team not deleted");
                    else
                        System.out.println("Confirmation rejected, team not deleted");

                } else if (group.equals("sports")) {
                    System.out.print("ID of sport: ");
                    Sports s = mapper.getSport(sc.nextInt());
                    System.out.printf(
                            "*--------*-------------------*\n|   ID   |       NAME        |\n*--------*-------------------*\n|  %4d  |  %15s  |\n*--------*-------------------*\n",
                            s.getId(), s.getName());

                    System.out.println("Are you sure to delete the sport? ALERT: This action has no turning back (yes / no)");
                    String conf = sc.next();

                    if (conf.equals("yes")) {
                        mapper.deleteSport(s.getId());
                        System.out.println("Sport deleted");
                    } else if (conf.equals("no"))
                        System.out.println("Sport not deleted");
                    else
                        System.out.println("Confirmation rejected, sport not deleted");

                } else
                    System.out.println("Invalid group");

            } else
                System.out.println("Invalid action.");

            System.out.print("Do you want to continue? (yes, no): ");
            if (sc.next().equals("no")) keep = false;

            //CLOSE SESSION
            session.commit();
            session.close();

        }
    }
}



