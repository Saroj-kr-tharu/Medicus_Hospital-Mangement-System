import PackageFile.Database;
import PackageFile.Doctors;
import PackageFile.Patients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    // input
    Scanner scan = new Scanner(System.in);

    // data type for functionality of menu
    int ch;
    String holdScreen;

    private void mainMenu() {

        // variable for db connection
        final String url = "jdbc:mysql://localhost:3306/hospital";
        final String user = "root";
        final String password = "Root@admin";

        // instance
        Database db = new Database(url, user, password);
        Connection connection = db.db_connect();

        Patients patient = new Patients();
        Doctors doctors = new Doctors();

        while (true) {
            System.out.print("\033[H\033[2J");

            System.out.println("\t\t\t <-------------------------------------------------------> ");
            System.out.println("\t\t\t <-------    Welcome To Hospital Management System  -------->  ");
            System.out.println("\t\t\t\t <-------    1. Add Patient          -------->  ");
            System.out.println("\t\t\t\t <-------    2. View Patient         -------->  ");
            System.out.println("\t\t\t\t <-------    3. View Doctors         -------->  ");
            System.out.println("\t\t\t\t <-------    4. Appointment          -------->  ");
            System.out.println("\t\t\t\t <-------    5. View Appointment     -------->  ");
            System.out.println("\t\t\t\t <-------    6. Check Appointment     -------->  ");
            System.out.println("\t\t\t\t <-------    7. Help                 -------->  ");
            System.out.println("\t\t\t\t <-------    9. Exit System          -------->  ");
            System.out.println("\t\t\t <-------------------------------------------------------> ");

            ch = scan.nextInt();

            switch (ch) {
                case 1: // Add patient
                    System.out.println("\t\t\t\t <------- Welcome to Add Patient Function -------->  ");
                    patient.addPatient(connection);

                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;

                case 2: // View Patient
                    System.out.println("\t\t\t\t <------- Welcome to View Patient Function -------->  ");
                    patient.displayPatient(connection);
                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;

                case 3: // View Doctors
                    System.out.println("\t\t\t\t <------- Welcome to View Doctors Function -------->  ");
                    doctors.viewDoctor(connection);

                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;

                case 4: // Appointment
                    System.out.println("\t\t\t\t <------- Welcome to Appointment Function -------->  ");
                    appointment(connection, patient, doctors);
                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;

                case 5: // view all Appointment
                    System.out.println("\t\t\t\t <------- Welcome to View Appointment Function -------->  ");
                    viewAllAppointment(connection);
                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;
                case 6: // check Appointment
                    System.out.println("\t\t\t\t <------- Welcome to Check Appointment Function -------->  ");
                    checkAppointment(connection,patient);
                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;

                case 7: // Help Section
                    System.out.println("\n\n\t\t\t <-------------------------------------------------------> ");
                    System.out.println("\t\t\t\t <------- Welcome To Help Section   --------->   ");
                    System.out.println("\t\t\t\t <-------    May Be You Found Bug   --------->  ");
                    System.out.println("\t\t\t\t <-------    Program By Saroj Kumar Tharu --->  ");
                    System.out.println("\t\t\t\t <-------    Sarojc11345@gmail.com ---------->  ");
                    System.out.println("\t\t\t <-------------------------------------------------------> ");
                    holdScreen = scan.nextLine();
                    break;
                case 9: // Exit System
                    System.out.println("\t\t\t\t <------- Thanks for using our System -------->  ");
                    System.out.println("\t\t\t\t <------- Exiting .. Exiting  -------->  ");

                    System.exit(1);
                    break;

                default:
                    System.out.println("\t\t\t\t <------ Invalid Options -------> ");
                    holdScreen = scan.nextLine();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.mainMenu();
    }

    private void checkAppointment(Connection connection,Patients patient){
        try{

            if (!patient.checkPatientById(connection)) {
                System.out.println("\t\t\t <--- Invalid Patient Id ----> ");
                return;
            }
            int id=patient.getPatientId();


            String query=
                    "select app.appointement_Date, " +
                    " doc.doctor_name,doc.speciliazation,app.patient_id, pa.patient_name " +
                    " from doctors as doc inner join appointments as app  " +
                    " on doc.doctor_id = app.doctor_id   " +
                    " join patient as pa on pa.patient_id = app.patient_id " +
                    " where pa.patient_id = ?" +
                    " order by appointement_Date";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("\t\t\t +--------------------+----------------------+------------+---------------------+---------------------+");
            System.out.println("\t\t\t |  Appointment Date  | Patient Name         | Patient Id |  Doctor Name        | Speciliazation      |  ");
            System.out.println("\t\t\t +--------------------+----------------------+------------+---------------------+---------------------+");
//                                                    20                   22               13            21                18

           while(resultSet.next()){

               String date= resultSet.getString("appointement_Date");
               String doc_name= resultSet.getString("doctor_name");
               String doc_spec = resultSet.getString("speciliazation");
               int pat_id = resultSet.getInt("patient_id");
               String pat_name=resultSet.getString("patient_name");

               System.out.printf("\t\t\t |%-20s|%-22s|%-12s|%-21s|%-21s| ", date,pat_name,pat_id,doc_name,doc_spec);
               System.out.println("\t\t\t +--------------------+----------------------+------------+---------------------+---------------------+");

           }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void viewAllAppointment(Connection connection){
        try{
            String query ="select app.appointement_Date, doc.doctor_id, doc.doctor_name,doc.speciliazation,app.patient_id,pa.patient_name from doctors as doc inner join appointments as app  on doc.doctor_id = app.doctor_id   join patient as pa on pa.patient_id = app.patient_id order by appointement_Date";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("\t\t\t +-------------------+---------------------+-------------------+------------+--------------------+------------+");
            System.out.println("\t\t\t |  Appointment Date | Doctor Name         | speciliazation    | Doctors Id | Patient Name       | Patient Id |");
            System.out.println("\t\t\t +-------------------+---------------------+-------------------+------------+--------------------+------------+");

            while(resultSet.next()){
                String date= resultSet.getString("appointement_Date");
                int doc_id=resultSet.getInt("doctor_id");
                String doc_name= resultSet.getString("doctor_name");
                String doc_spec = resultSet.getString("speciliazation");
                int pat_id = resultSet.getInt("patient_id");
                String pat_name=resultSet.getString("patient_name");


                System.out.printf("\t\t\t |%-19s|%-21s|%-19s|%-12s|%-20s|%-13s| ", date,doc_name,doc_spec,doc_id,pat_name,pat_id);

                System.out.println("\n\t\t\t +-------------------+---------------------+-------------------+------------+--------------------+-------------+");
            }

            preparedStatement.close();
            resultSet.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void appointment(Connection connection, Patients patient, Doctors doctors) {
        try {

            if (!patient.checkPatientById(connection)) {
                System.out.println("\t\t\t <--- Invalid Patient Id ----> ");
                return;
            }

            if (!doctors.checkDoctorById(connection)) {
                System.out.println("\t\t\t <--- Invalid Doctor Id ----> ");
                return;
            }

            System.out.print("\t\t\t Enter Appointment Date(YYYY-MM-DD) ----> ");
            String date = scan.nextLine();
            date = scan.nextLine();

            int doctor_id = doctors.getDoctorId();
            int patient_id = patient.getPatientId();

            String query = "SELECT * FROM appointments WHERE appointement_Date=? AND doctor_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, doctor_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("\t\t\t <---- Failed Doctor is not avaiable in this Date -----> ");
                System.out.println("\t\t\t <---- Please Try in Another Date  -----> ");

            } // here
            else {
                String query1 = "INSERT INTO appointments(patient_id,doctor_id,appointement_Date) VALUES(?,?,?)";
                PreparedStatement query_Statement = connection.prepareStatement(query1);
                query_Statement.setInt(1, patient_id);
                query_Statement.setInt(2, doctor_id);
                query_Statement.setString(3, date);

                int rowAffected = query_Statement.executeUpdate();
                if (rowAffected > 0) {
                    System.out.println("\t\t\t <----- Sucessfully Appointment  ------> ");
                }
                query_Statement.close();
            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
