package monycalculator_cipo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MonyCalculator_CIPO {
    
    private double amount;
    private double exchangeRate;
    private String currency;
    private String toCurrency;

    public static void main(String[] args) throws Exception {
        MonyCalculator_CIPO monyCalculator = new MonyCalculator_CIPO();
        monyCalculator.execute();
    }
    
    private void execute()throws Exception{
        input();
        process();
        ouput();
    }
    private void input(){
        Scanner input = new Scanner(System.in);
        System.out.println("Introduce una cantidad de dolares");
        amount = Double.parseDouble(input.nextLine());
        
        System.out.println("Introduce la moneda base");
        currency = input.nextLine();
        
        System.out.println("Introduce la moneda de cambio");
        toCurrency = input.nextLine();
    }
    
    private void process() throws Exception{
        exchangeRate = getExchangeRate(currency, toCurrency);
    }
    
    private void ouput(){
        System.out.println(amount +" "+ currency + " equivalen a " + amount*exchangeRate + " " + toCurrency);
    }
    
    private static double getExchangeRate(String from, String to)throws Exception{
        URL url = new URL("http://api.fixer.io/latest?base=" + from + "&symbols=" + to);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        try (BufferedReader reader = new BufferedReader(input)){
            String line = reader.readLine();
            line = line.substring(line.indexOf(to) + 5, line.indexOf("}"));
            return Double.parseDouble(line);
        }
    }
    
}
