package Bankers_Alg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Bankers {
    
    int n; // Number of processes
    int m; // Number of resources
    int[][] need;
    int[][] max;
    int[][] allocation;
    int[] available;
    int safeSequence[];
 
    void initializeValues() {
    
    //RECEIVE USER INPUTS
    Scanner scan = new Scanner(System.in);
    
    // get file name from user
    //System.out.println("Enter path to input file:");
        // Task_2\Task2_Input2.txt
    //File inputFile = new File(scan.nextLine());
    scan.close();
    File inputFile = new File("Bankers_Alg\\inClassExample.txt");
    
    try {
        Scanner fileScan = new Scanner(inputFile);

        n = Integer.parseInt(fileScan.nextLine());
        System.out.println("Number of processes: " + n);
        m = Integer.parseInt(fileScan.nextLine());
        System.out.println("Number of resources: " + m);
        need = new int[n][m];
        allocation = new int[n][m];
        max = new int[n][m];
        available = new int[m];
        safeSequence = new int[n];

        fileScan.nextLine(); // empty line

        String[] numbers;

        // populate allocation matrix
        System.out.println("\nALLOCATION MATRIX: ");
        for(int i = 0; i < n; i++) {
            numbers = fileScan.nextLine().split(" ");
            for(int j = 0; j < m; j++) {
                allocation[i][j] = Integer.parseInt(numbers[j]);
                System.out.print(allocation[i][j] + " ");
            }
            System.out.println();
        }

        fileScan.nextLine(); // empty line

        // populate max matrix
        System.out.println("\nMAX MATRIX:");
        for(int i = 0; i < n; i++) {
            numbers = fileScan.nextLine().split(" ");
            for(int j = 0; j < m; j++) {
                max[i][j] = Integer.parseInt(numbers[j]);
                System.out.print(max[i][j] + " ");
            }
            System.out.println();
        }
        fileScan.nextLine(); // empty line
        
        // populate available matrix
        System.out.println("\nAVAILABLE MATRIX:");
        numbers = fileScan.nextLine().split(" ");
        for(int i = 0; i < m; i++) {
            available[i] = Integer.parseInt(numbers[i]);
            System.out.print(available[i] + " ");
        }
        System.out.println();

        fileScan.close();
    } catch (FileNotFoundException e) {
        System.out.println("File not found");
        e.printStackTrace();
    }
    }
 
    void isSafe() {
        int count=0;
        
        //visited array to find the already allocated process
        boolean visited[] = new boolean[n];
        for (int i = 0;i < n; i++) {
            visited[i] = false;
        }
            
        //work array to store the copy of available resources
        int work[] = new int[m];   
        for (int i = 0;i < m; i++) {
            work[i] = available[i];
        }
    
        while (count<n) {
            boolean flag = false;
            for (int i = 0;i < n; i++) {
                if (visited[i] == false) {
                    int j;
                    for (j = 0; j < m; j++) {       
                        if(need[i][j] > work[j]) {
                            break;
                        }
                        
                    }

                    if (j == m) {
                        safeSequence[count++]=i;
                        visited[i]=true;
                        flag=true;                            
                        for (j = 0;j < m; j++) {
                            work[j] = work[j]+allocation[i][j];
                        }
                    }
                }
            }
            if (flag == false) {
                break;
            }
        }
        if (count < n) {
            System.out.println("\nTHE SYSTEM IS UNSAFE");
        }
        else {
            System.out.println("\nTHE SYSTEM IS SAFE");
            System.out.print("SAFE SEQUENCE: ");
            for (int i = 0;i < n; i++) {
                System.out.print("P" + safeSequence[i] + " ");
            }
        }
    }
     
    void calculateNeed() {
        for (int i = 0;i < n; i++) {
            for (int j = 0;j < m; j++) {
            need[i][j] = max[i][j]-allocation[i][j];
            }
        }       
    }
 
    public static void main(String[] args) { 
      Bankers bank = new Bankers();
          
      bank.initializeValues();   
      //Calculate the Need Matrix   
      bank.calculateNeed();           
             
       // Check whether system is in safe state or not
      bank.isSafe();       
    }
}
