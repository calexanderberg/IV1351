import java.sql.*;
import java.util.Scanner;


public class Main {

    private static PreparedStatement getAvailabilityInstrumentStatement;
    private static PreparedStatement getAllStatement;
    private static PreparedStatement numberOfRentalsStatement;
    private static PreparedStatement addRentalStatement;
    private static PreparedStatement terminateStatement;
    private static PreparedStatement getStudentIdStatement;
    private static PreparedStatement addHistoryStatement;

    public Connection accessDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/sg","test", "test");
    }

    public static void main(String[] args) throws SgDBException, SQLException, ClassNotFoundException {
        try {
            Scanner scan = new Scanner(System.in);
            Connection c = new Main().accessDB();
            c.setAutoCommit(false);
            Statement Statementt = c.createStatement();
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
            switch (choice) {
                default:
                    System.out.println("ERROR: Number does not exist, please choose a number between 1 to 5.");
                    break;

                case 1:
                    System.out.println("You picked 'List of available instruments by type.'");
                    System.out.println("Choose instrument out of the following:\n" +
                            "harp\n" +
                            "saxophone\n" +
                            "guitar\n" +
                            "ukelele\n" +
                            "bongo drums\n" +
                            "violin\n" +
                            "keyboard \n");
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
                    terminateRental(input.nextInt(), c);
                    break;
            }
        } 
        catch(SgDBException e)  {
            e.printStackTrace();
            try {
                c.Rollback();
            }
            catch (Exception ex2)
            {
                ex2.printStackTrace();
            }
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
        ResultSet rental = getAllStatement.executeQuery();
        while (rental.next()) {
            System.out.println("StudentID: " + rental.getString("student_id") +
                            " | InstrumentID: " + rental.getString("instrument_id"));
        }
    }

    private static void addRental(int studentID, int instrumentID,Connection c) throws SQLException {
        if (numberRental((studentID)) == 2 ) {
            System.out.println("The maximum amount of rental has been reached for this student.");
        } else {
            try{
                addRentalStatement.setInt(1,studentID);     
                addRentalStatement.setInt(2,instrumentID);
                addRentalStatement.executeUpdate();
                c.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void terminateRental(int instrumentID,Connection c) throws SQLException {
        int studentId = getStudentId(instrumentID);
        terminate(instrumentID,c);
        addHistoryStatement.setInt(1,instrumentID);
        addHistoryStatement.setInt(2,studentId);
        addHistoryStatement.executeUpdate();
        c.commit();
    }

    
    // Side functions to help navigate with the SQL

    public static ResultSet availableInstruments(String inst) throws SQLException{
        getAvailabilityInstrumentStatement.setString(1,inst);
        return getAvailabilityInstrumentStatement.executeQuery();
    }

    private static int numberRental(int id) throws SQLException {
        ResultSet rental = numberRentals(id);
        rental.next();
        return rental.getInt(1);
    }

    private static ResultSet numberRentals(int id) throws SQLException{
        numberOfRentalsStatement.setInt(1,id);
        return numberOfRentalsStatement.executeQuery();
    }

    private static int getStudentId(int instID) throws SQLException{
        getStudentIdStatement.setInt(1,instID);
        ResultSet rental = getStudentIdStatement.executeQuery();
        rental.next();
        return rental.getInt(8);
    }

    private static void terminate(int instrumentId,Connection c) throws SQLException {
        terminateStatement.setInt(1, instrumentId);
        terminateStatement.executeUpdate();
        c.commit();
    }


    // Prepared statements for our SQL.

    private static void preparedStatements(Connection c) throws SQLException {
        addHistoryStatement = c.prepareStatement("insert into renting_history(rented_instrument_id,return_date,student_id) values(?,CURRENT_DATE,?)");
        addRentalStatement = c.prepareStatement("update rental_instrument set is_rented = TRUE, student_id = ? where instrument_id = ?");
        getAvailabilityInstrumentStatement = c.prepareStatement("select * from rental_instrument where is_rented = FALSE and type_instrument = ? ");
        getAllStatement = c.prepareStatement("select * from rental_instrument where is_rented = TRUE");
        getStudentIdStatement = c.prepareStatement("select * from rental_instrument where instrument_id = ? ");
        numberOfRentalsStatement = c.prepareStatement("select count(*) from rental_instrument where student_id = ?");
        terminateStatement = c.prepareStatement("update rental_instrument set is_rented = FALSE, student_id = null where instrument_id = ?");
        }
}
