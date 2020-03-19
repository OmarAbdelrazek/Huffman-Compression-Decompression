package huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Huffman{
	
	
	
	public static void main(String[] args) throws Exception {
		
		Scanner n = new Scanner(System.in);
        int d2 = 0;
        //
        System.out.println("1- Compressing.\n2- Decompressing.");
          int d1 = n.nextInt();
          n.nextLine();
          switch (d1) {
            case 1:
            System.out.println("1- Compress a file\n2- Compress a binary fill\n3- Compress a folder");
             d2 = n.nextInt();
             n.nextLine();
                break;
        
            case 2:
            System.out.println("1- Decompress a file\n2- Decompress a binary fill\n3- Decompress a folder");
             d2 = n.nextInt();
             n.nextLine();

                break;
            
        }
        String filename = "";
        int j;

        if(d1 == 1){
            if(d2 == 1){
            	System.out.println("Enter file name: ");
        		 filename = n.nextLine();
        		String path = new File(filename).getAbsolutePath();
        		Compress c = new Compress(path);

            }
            else if(d2 == 2){
            	System.out.println("Enter file name: ");
        		 filename = n.nextLine();
        		String path = new File(filename).getAbsolutePath();
        		Compress c = new Compress(path);


            }
            else if(d2 == 3){
            	System.out.println("Enter Folder name: ");
            	String folderName = n.nextLine();
            	File folder = new File(folderName);
            	CompressFolder c = new CompressFolder(folder);


            }
        }

        if(d1 == 2){
            if(d2 == 1){
            	System.out.println("Enter file name: ");
        		 filename = n.nextLine();
        		String path = new File(filename).getAbsolutePath();
        		Decompression d = new Decompression(path);

            }
            else if(d2 == 2){
            	System.out.println("Enter file name: ");
        		 filename = n.nextLine();
        		String path = new File(filename).getAbsolutePath();
        		Decompression d = new Decompression(path);


            }
            else if(d2 == 3){
            	System.out.println("Enter Folder name: ");
            	String folderName = n.nextLine();
            	File folder = new File(folderName);
            	DecompressFolder c = new DecompressFolder(folder);
             
                

            }
        }
		
	}

}
