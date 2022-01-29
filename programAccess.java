import java.util.Scanner;


public class programAccess {
  private void accessDB() {

  }

  public static void main(String[] args){
    Scanner scan = new Scanner(System.in);

    System.out.println("Welcome to the Soundgood music school's website.");
    System.out.println("We are able to do the following:");
    System.out.println("1: List all instruments. \n2: Rent instruments. \n3: Terminate rental.");
    System.out.println("What would you like to do? Please pick a number.");
    int choice = scan.next();

    switch (choice)
    case 1 {
      System.out.println("You picked 'List all instruments.'");
      System.out.println("Below is a list of all avaliable instruments.");
      int i = 0;
      for () {
        print(i + instrument_Type + ", ");
      }
      System.out.println("Which instrument would you like to list? Please pick a number.");
      int instrument_Type = scan.next();
      listInstruments(instrument_Type)
    }
    case 2 {
      System.out.println("You picked 'Rent instruments.'");
      System.out.println("Please state the studentID:");
      int student = scan.next();
      
    }
    case 3 {
      System.out.println("You picked 'Terminate rental.'");
      System.out.println("Please state the studentID:");
      int studentId = scan.next();
      if(/*Student has a rental*/) {
        System.out.println("The student has rented the following instruments:");
        for(){
          System.out.println(/*INSTRUMENTId*/)
        } 
        System.out.println("Please state which rental you want to terminate:")
        int instrumentId = scan.next();
        terminateRental(studentId, instrumentId)
      } else System.out.println("the student has not rented an instrument, please try again.");
    }
  }

  public List<Instrument> listInstruments(String instrument) throws SQLException { 
    
    List<Instrument> instruments = new ArrayList<>();
    ResultSet result = null;

    try{
      showRentedInstrumentStmt.setString(1, instrument);
      result = showRentedInstrumentStmt.executeQuery();
      if() {
        System.out.println("No instrument avliable of that type.");
      } while (result.next()) {

      }

    } catch (SQLException sqle) {
      handleException(failureMsh, sqle);
    } finally {
      closeResultSet(FailureMsg, result);
    }

    return instruments;
  }

  public void rentInstruments() {
    try {
      addRentalForStudent.setString(1, studentId);
      addRentalForStudent.setString(2, instrumentId);
      connection.commit();

    } catch (SQLEception throwables) {
      handleDatabaseException("Could not rent instrument", throwables)
    }
  }

  public void terminateRental(int studentId, int instrumentId) {
    instrumentId.status = terminated
    studentID =

  }
}