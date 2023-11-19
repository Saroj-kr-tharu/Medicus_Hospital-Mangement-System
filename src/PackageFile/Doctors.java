package PackageFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
    private int id;
    private String name;
    private String speciliazation;

    Scanner scanner = new Scanner(System.in);
    public void viewDoctor(Connection connection){

        try{
            String query="SELECT * FROM doctors";
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();


            System.out.println("\t\t\t +--------------+----------------------+--------------------+");
            System.out.println("\t\t\t |  Doctors Id  | Name                 | Speciliazation     |");
            System.out.println("\t\t\t +--------------+----------------------+--------------------+");

            while(resultSet.next()){
                id=resultSet.getInt("doctor_id");
                name=resultSet.getString("doctor_name");
                speciliazation= resultSet.getString("speciliazation");

                System.out.printf("\t\t\t |%-14s|%-22s|%-20s|", id,name,speciliazation);
                System.out.println("\n\t\t\t +--------------+----------------------+--------------------+");
            }

            //closing
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public int getDoctorId() {
        return id;
    }

    public boolean checkDoctorById(Connection connection){

        try{
            System.out.print("\t\t\t Enter Doctors id ----> ");
             id=scanner.nextInt();
            String query = "SELECT * FROM doctors WHERE doctor_id = ?";
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
//            scanner.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return  false;
    }
}
