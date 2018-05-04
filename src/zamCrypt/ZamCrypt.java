package zamCrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class ZamCrypt {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String sfile = "data.pdf";
		File file = new File(sfile);
		
		File filestore = new File(sfile+".ZC");
		FileOutputStream fou = new FileOutputStream(filestore);
		fou.write((byte)1);
		fou.close();		
		
		file.delete();
		
		filestore.renameTo(new File(sfile));
		
		
		
		
		
		//FileInputStream fin = new FileInputStream(file);
		//FileOutputStream fou = new FileOutputStream("");
		
		//System.out.println(file.getName());


		/*
		
		FileInputStream fin = new FileInputStream(file);
		//FileOutputStream fou = new FileOutputStream("");

		int ln;
		byte[] bt = new byte[64];
		while( (ln=fin.read(bt)) != -1)  {
			//fou.write(bt,0,ln);
		}
		
		fin.close();
		//fou.close();
		//file.delete();
		
		//Files.move(file.toPath(), file.toPath().resolveSibling(sfile+".555"));
		
		
		*/
		
		
		
		//************************************
		
		File oldfile =new File("test.txt"); //********** real file
        File newfile =new File("test1.txt");//********** abstract file

        if(oldfile.renameTo(newfile)){
            //System.out.println("File renamed");
        }else{
            //System.out.println("Sorry! the file can't be renamed");
        }
		
		

	}
}
