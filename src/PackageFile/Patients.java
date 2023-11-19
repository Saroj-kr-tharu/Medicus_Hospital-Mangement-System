package PackageFile;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {
    private int id;
    private int age;
    private String name;
    private String gender;
    Scanner scanner = new Scanner(System.in);

    public int getPatientId() {
        return id;
    }

    public void displayPatient(Connection connection){

        try {

        String query = "SELECT * FROM patient";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet =  preparedStatement.executeQuery();


            System.out.println("\t\t\t +--------------+----------------------+-----------+------------+");
            System.out.println("\t\t\t |  Patient Id  | Name                 | Age       | Gender     |");
            System.out.println("\t\t\t +--------------+----------------------+-----------+------------+");
//                                            14                  22                  11         13
        while (resultSet.next()){
            int id = resultSet.getInt("patient_id");
            name= resultSet.getString("patient_name");
            age= resultSet.getInt("age");
            gender = resultSet.getString("gender");

            System.out.printf("\t\t\t |%-14s|%-22s|%-11s|%-12s|", id,name,age,gender);
            System.out.println("\n\t\t\t +--------------+----------------------+-----------+------------+");
        }
            // closing
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }



    }

    public void addPatient(Connection connection){
       try{

           System.out.print("\t\t\t Enter name ----> ");
           name=scanner.nextLine();
           System.out.print("\t\t\t Enter Age ----> ");
           age=scanner.nextInt();

           System.out.print("\t\t\t Enter Gender ----> ");
           gender=scanner.nextLine();
           gender=scanner.nextLine();

           String query= "INSERT INTO Patient(patient_name,age,gender) VALUES(?,?,?)";
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setString(1,name);
           preparedStatement.setInt(2,age);
           preparedStatement.setString(3,gender);

           int rowAffected= preparedStatement.executeUpdate();

           if(rowAffected>0 ){
               System.out.println("\t\t\t <------- Sucessfully Added Patient -----> ");
           }
           else{
               System.out.println("\t\t\t <------- Fail to Add -----> ");
           }

           // closing
           preparedStatement.close();

       } catch (SQLException e){
           System.out.println(e.getMessage());
       }

    }

    public boolean checkPatientById(Connection connection){

        try{
            System.out.print("\t\t\t Enter Patient id ----> ");
             id=scanner.nextInt();
            String query = "SELECT * FROM patient WHERE patient_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet  resultSet  = preparedStatement.executeQuery();

            if(resultSet.next()){
//                System.out.printf("\n\t\t\t Yes Patient is Admitted %d",id);
                return  true;
            }

            // closing
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return  false;
    }
}
