import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
	static List<String> tmpArray = new ArrayList<>();
	
	public static void readFile(String fileName) throws IOException {
		BufferedReader tmpReader = new BufferedReader(new FileReader(fileName));
		String line = "";
		line = tmpReader.readLine();
		while(line != null) {
			tmpArray.add(line);
			line = tmpReader.readLine();
		}
		tmpReader.close();
	}
	public static void readFile(String fileName, String tag) throws IOException {
		BufferedReader tmpReader = new BufferedReader(new FileReader(fileName));
		String line = "";
		line = tmpReader.readLine();
		tmpArray.add("<"+ tag +">");
		while(line != null) {
			tmpArray.add(line);
			line = tmpReader.readLine();
		}
		tmpReader.close();
		tmpArray.add("</"+ tag +">");
	}
	
	public static void writeFile(String fileName) throws IOException{
		FileWriter writer = new FileWriter(fileName, true);
		for(String s:tmpArray) {
			writer.write(s + System.getProperty("line.separator"));
		}
		writer.close();
		}
	
	public static void main(String[] args) throws IOException{
		String clientBaseConf, caFile, tlsAutKey, clientCrt, clientKey;
		List<String> settings = new ArrayList<>();
		File file = new File("settings");
	
	BufferedReader br = new BufferedReader(new FileReader(file)); 
	String line = br.readLine();
	//creating ArrayList from configuration file
	while (line != null) {
		settings.add(line);
		line = br.readLine();
	}
	br.close();
	//getting paths from configuration file
	clientBaseConf = "";
	clientBaseConf = settings.get(0).split("=")[1].split("#")[0].trim();
	caFile = "";
	caFile = settings.get(1).split("=")[1].split("#")[0].trim();
	tlsAutKey ="";
	tlsAutKey = settings.get(2).split("=")[1].split("#")[0].trim();
	clientCrt = "";
	clientCrt = settings.get(3).split("=")[1].split("#")[0].trim();
	clientKey = "";
	clientKey = settings.get(4).split("=")[1].split("#")[0].trim();
		
	System.out.println("------------Test paths to your files------------");
	System.out.println("Client base configuration is "+ clientBaseConf);
	System.out.println("Server CA is                 "+ caFile);
	System.out.println("TLS key is                   "+ tlsAutKey);
	System.out.println("Client sertificate is        "+ clientCrt);
	System.out.println("Client key is                "+ clientKey);
	
	//creating .ovpn file
	Scanner sc = new Scanner(System.in);
	String desision ="";	
		while (!(desision.equals("y")||desision.equals("n"))) { 
			System.out.print("Is everything right(y/n)  ");
			desision = sc.nextLine();
			}
		if(desision.equals("n")) {
			System.out.println("Correct your configuration file and restart the program");
			System.exit(-1);
		}
	System.out.println("Creating .ovpn file " + (clientKey.split("\\\\")[clientKey.split("\\\\").length -1]).split(".key")[0]+".ovpn");
	String result = (clientKey.split("\\\\")[clientKey.split("\\\\").length -1]).split(".key")[0]+ ".ovpn";
	readFile(clientBaseConf);
	readFile(caFile, "ca");
	readFile(clientCrt, "cert");
	readFile(clientKey, "key");
	if (tlsAutKey.length() > 0 ) {
	tmpArray.add("key-direction 1");	
	readFile(tlsAutKey, "tls-auth");
	}
	
	writeFile(result);
		
	}
}
