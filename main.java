import java.sql.*;
import java.util.Scanner;


public class Main {

    private static PreparedStatement getAvailInsSTM;
    private static PreparedStatement getAllSTM;
    private static PreparedStatement nrRentalsSTM;
    private static PreparedStatement addRentalSTM;
    private static PreparedStatement revertSTM;
    private static PreparedStatement getStudIdSTM;
    private static PreparedStatement addHistorySTM;

    public Connection accessDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/sg","test", "test");
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        Connection c = new Main().accessDB();
        c.setAutoCommit(false);
        Statement stmt = c.createStatement();
        preparedStatements(c);

        System.out.println("Welcome to the SoundGood music school's website.");
        System.out.println("We are able to do the following:");
        System.out.println(
                        "1: List of available instruments by type.\n" +
                        "2: Show rented instruments.\n" +
                        "3: Add rental.\n" +
                        "4: Terminate rental.");
        System.out.println("What would you like to do? Please pick a number.");
        int choice = scan.nextInt();

        Scanner input = new Scanner(System.in);
        switch (choice){
            default:
                System.out.println("ERROR: Number does not exist, please choose a number between 1 to 5.");
                break;

            case 1:
                System.out.println("You picked 'List of available instruments by type.'");
                System.out.println("Choose instrument out of the following:" +
                        "\n \n \n \n \n \n \n");
                printInstrumentType(input.next());
                break;

            case 2:
                System.out.println("You picked 'Show rented instruments.'");
                System.out.println("The list of instruments is shown below:");
                printAllRentals();
                break;

            case 3:
                System.out.println("You picked 'Add rental.'");
                System.out.println("Please state the studentID:");
                int studentID = input.nextInt();
                System.out.println("Please state the instrumentID:");
                int instrumentID = input.nextInt();
                addRental(studentID, instrumentID, c);
                break;

            case 4:
                System.out.println("You picked 'Terminate rental.'");
                System.out.println("Please state the instrumentID:");
                terminateRental(input.nextInt(),c);
                break;

        }
    }
    
    // Main functions of the program

    private static void printInstrumentType(String instrument) throws SQLException {
        ResultSet rental = availableInstruments(instrument);
        while (rental.next()){
            System.out.println("Type of Instrument: " + rental.getString("type_instrument") +
                            " | Instrument ID: "+ rental.getString("instrument_id") +
                            " | Price: " + rental.getString("monthly_fee") + ":- per month");
        }
    }

    private static void printAllRentals() throws SQLException {
        ResultSet rental = getAllSTM.executeQuery();
        while (rental.next()) {
            System.out.println("StudentID: " + rental.getString("student_id") +
                            " | InstrumentID: " + rental.getString("instrument_id"));
        }
    }

    private static void addRental(int studID, int instID,Connection c) throws SQLException {
        if (nrRental((studID)) == 2 ) {
            System.out.println("The maximum amount of rental has been reached for this student.");
        } else {
            addRentalSTM.setInt(1,studID);
            addRentalSTM.setInt(2,instID);
            addRentalSTM.executeUpdate();
            c.commit();
        }
    }

    private static void terminateRental(int instrumentID,Connection c) throws SQLException {
        int studId = getStudId(instrumentID);
        revert(instrumentID,c);
        addHistorySTM.setInt(1,instrumentID);
        addHistorySTM.setInt(2,studId);
        addHistorySTM.executeUpdate();
        c.commit();
    }

    // Side functions to help navigate with the SQL
    
    public static ResultSet availableInstruments(String inst) throws SQLException{
        getAvailInsSTM.setString(1,inst);
        return getAvailInsSTM.executeQuery();
    }

    private static int nrRental(int id) throws SQLException {
        ResultSet rental = nrRentals(id);
        rental.next();
        return rental.getInt(1);
    }

    private static ResultSet nrRentals(int id) throws SQLException{
        nrRentalsSTM.setInt(1,id);
        return nrRentalsSTM.executeQuery();
    }
    
    private static int getStudId(int instID) throws SQLException{
        getStudIdSTM.setInt(1,instID);
        ResultSet r = getStudIdSTM.executeQuery();
        r.next();
        return r.getInt(8);
    }
    
    private static void revert(int instId,Connection c) throws SQLException {
        revertSTM.setInt(1, instId);
        revertSTM.executeUpdate();
        c.commit();
    }


    // Prepared statements for our SQL.

    private static void preparedStatements(Connection c) throws SQLException {
        addHistorySTM = c.prepareStatement("insert into renting_history(rented_instrument_id,return_date,student_id) values(?,CURRENT_DATE,?)");
        addRentalSTM = c.prepareStatement("update rental_instrument set is_rented = TRUE, student_id = ? where instrument_id = ?");
        getAvailInsSTM = c.prepareStatement("select * from rental_instrument where is_rented = FALSE and type_instrument = ? ");
        getAllSTM = c.prepareStatement("select * from rental_instrument where is_rented = TRUE");
        getStudIdSTM = c.prepareStatement("select * from rental_instrument where instrument_id = ? ");
        nrRentalsSTM = c.prepareStatement("select count(*) from rental_instrument where student_id = ?");
        revertSTM = c.prepareStatement("update rental_instrument set is_rented = FALSE, student_id = null where instrument_id = ?");
        }
}
