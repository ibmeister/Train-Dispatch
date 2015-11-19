package trainDispatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
 
public class TestCase {
    
    public TestCase(int train_count, int spawn_length){
 
        Random rand = new Random();

                try { 
                
                    File file = new File("TrainSequence.txt");
                    file.createNewFile();
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);      
                        
                    for (int k = 1; k < train_count + 1; k++){ // generates for 50 trains (1-51)
                        int starting = rand.nextInt(13) + 1;
                        int destination = rand.nextInt(13) + 1; 
                        while (destination==starting){
                           destination = rand.nextInt(13) + 1; // check that start does not equal destination
                        }
                        bw.write(String.valueOf(starting)); //starting
                        bw.write(" " + String.valueOf(destination)); //destination
                        bw.write(" " + String.valueOf(rand.nextInt(spawn_length))); //time
                        if(k != train_count){
                        	bw.write ("\r\n");
                        }
                    }
        
                    bw.close();     
                    System.out.println("Done Creating Test Case Text File ");
 
                }catch (IOException e){             
                    e.printStackTrace();
                }
    }
}
 