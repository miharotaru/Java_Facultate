# EXAM - SUCCESS!

# Digital specifications (specs) for the examination - Subject 11:

// Mark 3:
	// Create the interface ElectronicDevices with the method infoDevice() which is returning String, must be inserted

// Mark 3:
	// a. Create public class Phone which must implement ElectronicDevice interface and It must be inserted 3 fields:
	// weight - float, diagonal - double, producer - String
	// b. for this class fields it is mandatory to implement getters and setters, plus the default constructor (without parameters), 
	// there is NO constructor with parameters
	// c. override implementation for the infoDevice() method (from the ElectronicDevices interface) to return the producer String
	// d. the setters should throw Exception if the constraints are not fulfilled:
	// - producer different than null and producer String length greater than 1
	// - diagonal and weight greater than 0 each 
	// e. Implements Serializable, Cloneable and Override the implementation for equals(), hashCode() and clone() methods

// Mark 3:
	// a. Create public class SmartPhone which inherits the Phone class and it adds the batteryDuration - int field
	// b. for this class fields it is mandatory to implement getters and setters
	// c. override virtual implementation for the infoDevice() method (from the ElectronicDevices interface and Phone implementation),
	//    to return the batteryDuration as a String
	// d. the setters should throw Exception if the constraints are not fulfilled:
	// - batteryDuration greater than 0

// Mark 4:
	// Create public class Utils which contains private static list field with interface as type: List<ElectronicDevices>
    // Insert the following methods:
	// a. public static List<ElectronicDevices> createPhones(int n) throws Exception - for creating an ArrayList of n elemnts
    //    which are containing n default Phone objects and it using the static field of the class (list)
    // b. public static List<ElectronicDevices> readPhones(String file) 
    //    - for reading and parsing text files with string lines for creating Phone objects 
    // (e.g. please see for example phonesList.txt file); first line is the weight in grams, second line is screen diagonal and third line is the producer
    //   hint: use RandomAccessFile and read / parse line by line (first is parsing for float - weight, second line is double - diagonal, third is String - producer)
    // c. public static void writeBinaryPhones(String file, List<ElectronicDevices> listP) - for writing binary the phones into the file
    //   hint: use FileOutputStream with FileOurputStream to serialized/save the Phone objects from the ArrayList of the phones objects
    // d. public static List<ElectronicDevices> readBinaryPhones(String file) - for reading binary the Phone objects from the file and creating the ArrayList 

// Mark 4:
	// Beside the JUnit 4 framework running for the official mark,
	// please do your own minimal testing - e.g. create an instance from SmartPhone and call methods in main method from ProgMainPlayGround class:
	// createPhones or readPhones from Utils class, sockets objects, etc.
	// DO NOT spend too much time here - No requirements and a lot of testing code is already into JUnit4 framework

// Mark 5:
	//	a. create the public class VectThread which implements Runnable and contains 2 fields:
	//	- phonesList with interface as type List<ElectronicDevices>
	//  - avgWeight is a real (double) number for storing the average weight of the phones list
	//  b. In the constructor read the file using readBinaryPhones static method from Utils
	//	c. provide get methods for the both fields (phoneList and avgweight) of the class  
	//	d. Within the override run method (with the signature in Runnable interface) 
	//	- the developer should go through the phoneList and calculate the average of the weights from the phone list objects (Phone class - explicit cast) 
	
	
// Subject of + 2 points <=> Mark 6 or 7 (and parts of the mark 8):
	//	a. Create public class TCPServerSocketMultiT which handles multi-threading TCP server socket connections
	//	for implementing a proprietary communication protocol (set of rules)
	//	b. The class contains the following private fields:
	//	- serverSocket as ServerSocket, port = 50001 as int, f as File and vt as VectThread ("has a" relationship)
	//	c. The class contains the following methods and constructor:
	//		c.1 - constructor which get the port as parameter and create the serverSocket:
	//		public TCPServerSocketMultiT(int port) throws Exception
	//		c.2 - getter and setter for the field port
	//		c.3 - public void setFileName(String newFName) method which allocate memory for the field f, if and only if, 
	//			the String parameter is different than null, otherwise is setting null
	//		c.4.- public void startTCPServer() throws IOException method which is having the infinite processing loop and is implementing 3 commands from the proprietary protocol
	//		HINTS for startTCPServer method:
	//		-create multi-threading by using lambda expressions from Runnable functional interface after the blocking accept() method from serverSocket object
	//		-get the input stream as BufferedReader and output stream as ObjectOutputStream
	//		-initialize the vt field from class VectThread by passing the file absolute path from f field as parameters AND OBTAIN the list (ArrayList of Phone objects) from the file 
	//		-parse line by line the TCP request
	//			- if EXIT text command is received over the socket, then break the infinite loop of the processing and send TCP FIN packet back to the TCP client (e.g. by closing socket, etc.)
	//			- (mark 6) if GETFILE text command is received over the socket, then reply back the serialized list encapsulated in the vt object field
	//			- (mark 7) if GETJSON text command is received over the socket, then reply back with the list in JSON format
	//			- (mark 8) if GETDB text command is received over the socket, then reply back with the list as String produced by UtilsDAO.selectData() (please also take into account, you have to initialize JDBC connection and close it with static methods from UtilsDAO);
	//	YOU MAY NOT create the TCP client because it is already created into JUnit test framework; for mark 8, please also see UtilsDAO class (without UtilsDAO class, mark 8 can not be achieved)

// Mark 8: create public class UtilsDAO with only one static field c from class Connection (SQL/JDBC)
	//	This class contains the following 3 static methods:
	//	a.	public static void setConnection() - set the connection by using org.sqlite.JDBC driver and connection string: jdbc:sqlite:test.db
	//	b.	public static void closeConnection() - close the SQL/JDBC connection
	//	c.	public static String selectData() throws SQLException - for SQL selecting all the phones from the already created SQLite DB file
	//		- select * from PHONES
	//		- PHONES table is already created with the following columns id - INT, PRODUCER - TEXT, DIAGONAL - REAL and WEIGHT - REAL
	//		- the String containing the view after selecting the entire table is having each table line separated with "\r\n" and each column value will be separated by ":"


// Subject+2 points <=> Mark 9/10:
// Create public class UDPServerSocket which implements a proprietary communication protocol and implements AutoCloseable (override specific method)
// It has 2 fields: socket - DatagramSocket and bindPort - int
// It contains the following constructor methods:
// a. public UDPServerSocket() throws SocketException - init bindPort on 60001
// b. public int getBindPort() - returns the bindPord field
// c. public void processRequest() throws IOException - receive UDP packets and process them (without infinite loop) with the following rules:
//		- if the request contains W? , then the reply UDP packet contains as pay-load "UDPS".
//		- if the request contains BYE , then the reply UDP packet contains as pay-load "BYE ACK" and close the resources (e.g. socket)
//		- if the request contains any other pay-load , then the reply UDP packet contains as pay-load "ACK".

// Subject+2 points <=> Mark 9/10:
// Create public class UDPClientSocket which implements a proprietary communication protocol and implements AutoCloseable (override specific method)
// It has 2 fields: socket - DatagramSocket
// It contains the following constructor methods:
// a. public UDPClientSocket() throws SocketException - init socket WITHOUT bind port
// b. public String sendAndReceiveMsg(String msg, String ipAddr, int port) throws UnknownHostException 
//   - send UDP packets and process them (without infinite loop) with the following rules:
//		- when the sent request contains W? , then the response UDP packet from server contains as pay-load "UDPS".
//		- when the sent request contains BYE , then the response UDP packet from server contains as pay-load "BYE ACK" 
//		- when the sent request contains any other pay-load , then the response UDP packet contains as pay-load "ACK".
