import java.sql.*;
import java.util.Scanner;


public class Main {

    private static PreparedStatement getAvailInsSTM;
    private static PreparedStatement getallSTM;
    private static PreparedStatement nrRentalsSTM; //nr of rentals per student
    private static PreparedStatement addRentalSTM; //nr of rentals per student
    private static PreparedStatement revertSTM; //gör så att instrumentet blir unrented true -> false
    private static PreparedStatement getStudIdSTM; //hömtar student id för terminate och lägger till history
    private static PreparedStatement addHistorySTM;    //private PreparedStatement;

    public Connection accessDB() throws SQLException, ClassNotFoundException {
      Class.forName("org.postgresql.Driver");
      return DriverManager.getConnection("jdbc:postgresql://localhost:5432/sg", "test", "test");
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner scan = new Scanner(System.in);  
        System.out.println("Welcome to the Soundgood music school's website.");  
        Scanner db = new Scanner(System.in);

//Below is not my code
        Connection c = new Main().accessDB();
        c.setAutoCommit(false);
        Statement stmt = c.createStatement();
        prepareStatements(c);
//end

        //Scanner in = new Scanner(System.in);
        boolean status = true;
        while(status) {
            System.out.println("\nWe are able to do the following:");
            System.out.println("1: List all instruments. \n2: Rent instruments. \n3: Terminate rental.\n 4. To end the program");
            System.out.println("What would you like to do? Please pick a number.");
            int choice = scan.next();
            
            switch (choice){
                default:
                    System.out.println("Error, incorect value. \n Please write a correct value");
                    break;

                case 1:
                    Scanner instrument_Type = new Scanner(System.in);
                    System.out.println("You picked 'List all instruments.'");
                    System.out.println("Which instrument would you like to list?");
                    rentalPrint(instrument_Type.next());
                    break;

                case "show":
                    showRented();
                    break;
                    
                case 2:
                    System.out.println("You picked 'Rent instruments.'");
                    System.out.println("Please state the studentID:");
                    int student_ID = scan.next();
                    System.out.println("Please state the instrumentID:");
                    int instrument_Id2 = scan.next();
                    rentInstruments(student_ID,instrument_Id2, c);
                    break;

                case 3:
                    Scanner instrumentId3 = new Scanner(System.in);
                    System.out.println("You picked 'Terminate rental.'");
                    System.out.println("Please state the instrument ID which you want to terminate:")
                    terminate(instrumentId3.nextInt(),c);
                    break;
                
                case 4:
                    System.out.println("You picked 'Quit program.'");
                    System.out.println("Bye Bye");
                    status=false
                    break;
                }
            }
        }



    private static void rentInstruments(int studID, int instID,Connection c) throws SQLException {
        if (nrRental(studID) == 2){
            System.out.println("Student has already the maximum amount of rentals");
        }
        else{
            addrentals(studID,instID,c);
        }

    }

    private static void showRented() throws SQLException {
            ResultSet r = getall();
            while (r.next()){
                System.out.println("StudentID: " + r.getString("student_id") + " IntrumentID: " + r.getString("id") );
            }
    }

    private static void rentalPrint(String inst) throws SQLException {
        ResultSet r = getAvailIns(inst);
            System.out.println("--------------------------------------------------");
        while (r.next()){
            System.out.println("Brand: " + r.getString("brand")
                    + " price: " + r.getString("price"));
            }
            System.out.println("---------------------------------------------------");
        }

    private static boolean checkTable(Connection c, String s) throws SQLException {
        DatabaseMetaData metaData= c.getMetaData();
        ResultSet tablemMetaData=metaData.getTables(null,null,null,null);
        while(tablemMetaData.next()){
            String tableName=tablemMetaData.getString(3);
            if(tableName.equals(s))
                return true;
                }
        return false;
        }

    private static void printTable(Statement s) throws SQLException {
        ResultSet r = s.executeQuery("select * from ensemble");
        while (r.next()){
            System.out.println("ensembleID: " + r.getString(3));
        }
    }
    private static int nrRental(int id) throws SQLException {
        ResultSet r = nrRentals(id);
        r.next();
        return r.getInt(1);
    }

//------------------------------------------------------------------------------------------------------------
    // STATEMENTS
    public static ResultSet getAvailIns(String inst){
        try{
            getAvailInsSTM.setString(1,inst);
            return getAvailInsSTM.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static ResultSet getall(){
        try {
            return getallSTM.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ResultSet nrRentals(int id){
        try {
            nrRentalsSTM.setInt(1,id);
            return nrRentalsSTM.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void addrentals(int studID, int instID,Connection c) {
        try{
            addRentalSTM.setInt(1,studID);
            addRentalSTM.setInt(2,instID);
            addRentalSTM.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private static int getStudId(int instID) {
        try{
            getStudIdSTM.setInt(1,instID);
            ResultSet r = getStudIdSTM.executeQuery();
            r.next();
            return r.getInt(6);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    private static void revert(int instId,Connection c)  {
        try {
            revertSTM.setInt(1, instId);
            revertSTM.executeUpdate();
            c.commit();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void terminate(int instId,Connection c) {
        int studId = getStudId(instId);
        revert(instId,c);
        try{
            addHistorySTM.setInt(1,instId);
            addHistorySTM.setInt(2,studId);
            addHistorySTM.executeUpdate();
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void prepareStatements(Connection c) throws SQLException {

        getAvailInsSTM = c.prepareStatement("select * from instrument_renting where is_rented = FALSE and instrument = ? ");

        getallSTM = c.prepareStatement("select * from instrument_renting where is_rented = TRUE");

        nrRentalsSTM = c.prepareStatement("select count(*) from instrument_renting where student_id = ?");

        addRentalSTM = c.prepareStatement("update instrument_renting set is_rented = TRUE, student_id = ? where id = ?");

        revertSTM = c.prepareStatement("update instrument_renting set is_rented = FALSE, student_id = null where id = ?");

        getStudIdSTM = c.prepareStatement("select *  from instrument_renting where id = ? ");

        addHistorySTM = c.prepareStatement("insert into instrument_renting_history(instrument_renting_id,return_date,student_id) values(?,CURRENT_DATE,?)");
    }

}