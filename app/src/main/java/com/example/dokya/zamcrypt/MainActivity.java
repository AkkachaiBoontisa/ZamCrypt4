package com.example.dokya.zamcrypt;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.webkit.MimeTypeMap;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.CAST6Engine;
import org.bouncycastle.crypto.engines.CamelliaEngine;
import org.bouncycastle.crypto.engines.RC6Engine;
import org.bouncycastle.crypto.engines.SerpentEngine;
import org.bouncycastle.crypto.engines.TwofishEngine;
import org.bouncycastle.crypto.generators.SCrypt;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jcajce.provider.digest.BCMessageDigest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import at.gadermaier.argon2.Argon2;
import at.gadermaier.argon2.Argon2Factory;


public class MainActivity extends AppCompatActivity {

    //**********************************
    //-------- Encryption with Zam Crypt --------------

    //AESEngine ag = new AESEngine();
    private Argon2 ag2 =null;

    private byte[] keyFromSetPWcdlg = null;

    //********************
    private EditText showFilekkk;
    private Button setPasword ;

    private Button presss;
    private Button buttonOpenFilekk;

    //************************ Encrypt Part ************
    private static File selested = null; //****************** Store File selected
    private static File fileStore = null;
    private static File fileTestOpen = null;

    private static FileInputStream fin = null;
    private static FileOutputStream fou = null;

    private static String nameOfSelectedFile = null;
    private static String keyString = null;

    private static MessageDigest md = null;
    //*******************

    private Button buttonOPD;
    private Button buttonUp;
    private TextView textFolder;

    private static final int CUSTOM_DIALOG_ID = 0;
    private ListView dialog_listView;

    private File   root;
    private File curFolder;
    private String sssss ="";
    private String strcdlg = "";

    private List<String> fileList = new ArrayList<String>();

    //****************** new method from zam007 **********************
    private void setKeyFromSetPWcdlg(byte[] bt){
        this.keyFromSetPWcdlg=bt.clone();
    }
    private byte[] getKeyFromSetPWcdlg(){
        return keyFromSetPWcdlg;
    }
    private void setKeyFromSetPWcdlg_T0_Null(){
        this.keyFromSetPWcdlg = null;
    }

    /*private void setStrcdlg(String str){
        this.strcdlg = str;
    }
    private String getStrcdlg(){
        return strcdlg;
    }
    private void setSssss(String str){
        this.sssss = str;
    }
    private String getSssss(){
        return sssss;
    }*/

    private EditText editTextPP = null;
    private EditText editTextP1 = null;
    private EditText editTextP2 = null;
    private EditText editTextP3 = null;
    private EditText editTextP4 = null;
    private EditText editTextNumPP = null;
    //**************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainActivity m1 = new MainActivity();

        //***********************************************
        //---------------------------------------------
        Security.addProvider(new BouncyCastleProvider());



        /*aes = new AESEngine();
        sssss= aes.getAlgorithmName()+""+aes.getBlockSize();

        ag2 = Argon2Factory.create()
        .setIterations(8)
        .setMemory(12)
        .setParallelism(4)
        .setOutputLength(256*10);
        sssss=sssss+"---"+ ag2.hash("password".getBytes(),"somesalt".getBytes());*/




        //************************************************

        buttonOPD = (Button)findViewById(R.id.opendialog);
        buttonOPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CUSTOM_DIALOG_ID);
            }
        });

        //root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());


        curFolder = root;

        //**************************************************
        showFilekkk = (EditText)findViewById(R.id.showFile); //********** Plan text for show "any TEXT"
        setPasword = (Button) findViewById(R.id.setPW);//**************** set password //---
        setPasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custum_dialog);
                dialog.setCancelable(true);

                //***** variable in custom dialog ************

                ag2 = Argon2Factory.create()
                        .setIterations(10)
                        .setMemory(12)//************************* ????????????????????? "care" // max 14  norm 12  ??????????????????????????
                        .setParallelism(4)
                        .setOutputLength(1024/8);

                editTextPP  = (EditText)dialog.findViewById(R.id.PP);
                editTextPP.setText("");
                editTextP1 = (EditText)dialog.findViewById(R.id.P1);
                editTextP1.setText("");
                editTextP2 = (EditText)dialog.findViewById(R.id.P2);
                editTextP2.setText("");
                editTextP3 = (EditText)dialog.findViewById(R.id.P3);
                editTextP3.setText("");
                editTextP4 = (EditText)dialog.findViewById(R.id.P4);
                editTextP4.setText("");
                editTextNumPP = (EditText)dialog.findViewById(R.id.numPP);


                Button buttonGenPWcsdlg = (Button)dialog.findViewById(R.id.gen_PW_cslg); //* gen PW PP in custom dialog
                buttonGenPWcsdlg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SecureRandom rndS = new SecureRandom();
                        byte[] byteRnd = new byte[Integer.parseInt(editTextNumPP.getText().toString())];
                        rndS.nextBytes(byteRnd);
                        editTextPP.setText(org.bouncycastle.util.encoders.Base64.toBase64String(byteRnd));
                    }
                });

                Button buttonSetPW_cdlg = (Button)dialog.findViewById(R.id.setPW_cdlg);//****** set "final" password
                buttonSetPW_cdlg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            //m1.setStrcdlg( editTextPP.getText().toString());
                            //showFilekkk.setText(ag2.hash("ghfhgffggjg".getBytes(),"ghgfhgfhfhfg".getBytes()));m1.getStrcdlg()
                            /*m1.setSssss( ag2.hash(
                                    (       editTextP1.getText().toString()+
                                            editTextP2.getText().toString()+
                                            editTextP3.getText().toString()+
                                            editTextP4.getText().toString()
                                    ).getBytes()
                                    ,editTextPP.getText().toString().getBytes()));*/ //-ok
                            //showFilekkk.setText(getSssss()+" : 555");

                            m1.setKeyFromSetPWcdlg( Hex.decode( ag2.hash(
                                    (       editTextP1.getText().toString()+
                                            editTextP2.getText().toString()+
                                            editTextP3.getText().toString()+
                                            editTextP4.getText().toString()
                                    ).getBytes()
                                    , Hex.toHexString( org.bouncycastle.util.encoders.Base64.decode(
                                            editTextPP.getText().toString())).getBytes()) ));//****** set password to "the var"
                            showFilekkk.setText("set pw already");

                        }catch (Exception e){
                            showFilekkk.setText(e.toString());
                        }finally {
                            ag2 = null;
                        }
                        //showFilekkk.setText("set pw already");

                        //showFilekkk.setText(Hex.toHexString(m1.getKeyFromSetPWcdlg()));

                        dialog.cancel();
                    }
                });

                //********** last line
                dialog.show();
            }
        });

        presss = (Button)findViewById(R.id.buttomclick555);//*********** PROcess crypt *****************
        presss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //*********** build key (by scrypt vs argon2) for the block ciphers **************
                byte[] bt2 = null;
                try {

                    //********** SCrypt ----------------------
                    SCrypt scEng = new SCrypt();
                    byte[] bt = scEng.generate(Arrays.copyOfRange(m1.getKeyFromSetPWcdlg(),0,m1.getKeyFromSetPWcdlg().length/2)
                            , Arrays.copyOfRange(m1.getKeyFromSetPWcdlg(),m1.getKeyFromSetPWcdlg().length/2,m1.getKeyFromSetPWcdlg().length),
                            1024, 8, 4, 1024/8);
                    scEng = null;
                    Toast.makeText(MainActivity.this,"1. process SCrypt already.",Toast.LENGTH_LONG).show();

                    //********* Argon2 -----------------------
                    ag2 = Argon2Factory.create()
                            .setIterations(8)
                            .setMemory(10)//************************* ????????????????????? "care" // max 14  norm 12  ??????????????????????????
                            .setParallelism(4)
                            .setOutputLength((6*256+512)/8);
                     bt2 = Hex.decode(ag2.hash( Arrays.copyOfRange(bt,0,bt.length/2),
                            Arrays.copyOfRange(bt,bt.length/2,bt.length)));
                    bt=null;
                    Toast.makeText(MainActivity.this,"2. process Argon2 already.",Toast.LENGTH_LONG).show();

                    }catch (Exception e){
                    showFilekkk.setText(e.toString());
                }finally {
                    m1.setKeyFromSetPWcdlg_T0_Null();
                    ag2 = null;
                }

                //*********** process block cipher to file
                try {
                    //******************* Crypt **********************
                    //showFilekkk.setText(Hex.toHexString(bt2));

                    BlockCipher aes = new AESEngine();
                    BlockCipher camellia = new CamelliaEngine();
                    BlockCipher cast6 = new CAST6Engine();
                    BlockCipher rc6 = new RC6Engine();
                    BlockCipher serpent = new SerpentEngine();
                    BlockCipher twofish = new TwofishEngine();

                    byte[]  aesKey       = Arrays.copyOfRange(bt2,0,32*1);
                    byte[]  camelliaKey  = Arrays.copyOfRange(bt2,32*1,32*2);
                    byte[]  cast6Key     = Arrays.copyOfRange(bt2,32*2,32*3);
                    byte[]  rc6Key       = Arrays.copyOfRange(bt2,32*3,32*4);
                    byte[]  serpentKey   = Arrays.copyOfRange(bt2,32*4,32*5);
                    byte[]  twofishKey   = Arrays.copyOfRange(bt2,32*5,32*6);

                    String nouce = Hex.toHexString(Arrays.copyOfRange(bt2,32*6,256));

                    bt2 = null;

                        //----------- Init Cipher Engine with Their key ----------
                    aes.init(true,new KeyParameter(aesKey));
                    camellia.init(true,new KeyParameter(camelliaKey));
                    cast6.init(true,new KeyParameter(cast6Key));
                    rc6.init(true,new KeyParameter(rc6Key));
                    serpent.init(true,new KeyParameter(serpentKey));
                    twofish.init(true,new KeyParameter(twofishKey));

                    //**************** Encryption ***********************


                    //keyString = "";//getPasword.getText().toString();      //**** key for encryption
                    nameOfSelectedFile  = selested.getAbsolutePath(); //**** path of selected file

                    fin = new FileInputStream(selested);              //**** create file input stream from selected file

                    fileStore = new File(selested.getAbsolutePath()+".555"); // create file store for compute
                    fou = new FileOutputStream(fileStore);            //**** create file output stream from store file

                    Toast.makeText(MainActivity.this,"Process Encryption already",Toast.LENGTH_LONG);

                    //Security.addProvider(new BouncyCastleProvider());
                    //md = BCMessageDigest.getInstance("SHA3-224");//224 bit 28 byte  //**** create message digest
                    MessageDigest mm = MessageDigest.getInstance("md5");

                    int ln = 0;
                    byte[] buf = new byte[16];

                    byte[] inputBlock  = new byte[16];
                    byte[] outputBlock = new byte[16];
                    int counter = 1;



                    while ( (ln=fin.read(buf)) != -1){

                        aes.reset();camellia.reset();cast6.reset();rc6.reset();serpent.reset();twofish.reset();

                        mm.reset();
                        mm.update( ( nouce+Integer.toString(counter) ).getBytes()  );
                        counter++;
                        inputBlock = Arrays.copyOfRange(mm.digest() ,0,16);

                        //---------- kkk ----------
                        twofish.processBlock(inputBlock,0,outputBlock,0);
                        inputBlock = outputBlock.clone();

                        serpent.processBlock(inputBlock,0,outputBlock,0);
                        inputBlock = outputBlock.clone();

                        rc6.processBlock(inputBlock,0,outputBlock,0);
                        inputBlock = outputBlock.clone();

                        cast6.processBlock(inputBlock,0,outputBlock,0);
                        inputBlock = outputBlock.clone();

                        camellia.processBlock(inputBlock,0,outputBlock,0);
                        inputBlock = outputBlock.clone();

                        aes.processBlock(inputBlock,0,outputBlock,0);

                        //----------- XOR with PT. ------------------
                        for (int i=0;i<ln;i++){
                            buf[i] = (byte) (buf[i]^outputBlock[i]);//
                        }

                        fou.write(buf,0,ln);
                    }

                    fin.close();
                    fou.flush();
                    fou.close();
                    fin = null;
                    fou = null;

                    selested.renameTo(new File(nameOfSelectedFile+".Old"));

                    //selested.delete();
                    //selested = null;
                    fileStore.renameTo(new File(nameOfSelectedFile));
                    //fileStore = null;

                    //fileTestOpen = selested;

                    showFilekkk.setText("ok!!");      ///**/

                    //**************************** set all to null +++++++++++++++++++++++
                    /*aes=null;camellia=null;cast6=null;rc6=null;serpent=null;twofish=null;
                    aesKey=null;camelliaKey=null;cast6Key=null;rc6Key=null;serpentKey=null;twofishKey=null;
                    nouce=null;
                    buf=null;inputBlock=null;outputBlock=null;*/



                }catch (Exception e){
                    showFilekkk.setText(e.toString());
                }finally {
                    //aes=null;
                }

            }

        });

        buttonOpenFilekk = (Button)findViewById(R.id.buttomOpenFile);//******** test open file
        buttonOpenFilekk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    fileTestOpen = selested;
                    String url =fileTestOpen.getName();
                    String type = null;
                    String extension = url.substring(url.lastIndexOf(".") + 1);
                    if (extension != null) {
                        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    }


                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    //File file = new File("/sdcard/test.mp3");
                    intent.setDataAndType(Uri.fromFile(fileTestOpen), type);//
                    startActivity(intent);

                    showFilekkk.setText("ok!!!! when try open file");
                }catch (Exception e){
                    showFilekkk.setText("errrrrrrr when try open file");
                }
            }
        });

        Button buttomDelTmpFilekk = (Button)findViewById(R.id.buttomDelTmpFile);
        buttomDelTmpFilekk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    (new File(nameOfSelectedFile+".Old")).delete();

                    showFilekkk.setText("Delete tmp file already");//+m1.getSssss()//+Hex.toHexString(m1.getKeyFromSetPWcdlg())
                }catch (Exception e555) {
                    showFilekkk.setText(e555+"" );
                }
            }
        });


        //*****************************************************
    }


    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog = null;

        switch (id){
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialoglayout);
                dialog.setTitle("custom dialog");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                textFolder = (TextView)dialog.findViewById(R.id.folder);
                buttonUp = (Button)dialog.findViewById(R.id.up);
                buttonUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListDir(curFolder.getParentFile());
                    }
                });

                dialog_listView = (ListView)dialog.findViewById(R.id.dialoglist);
                dialog_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selested = new File(fileList.get(position));//**************************************************************************
                        if (selested.isDirectory()){
                            ListDir(selested);
                        }else {
                            showFilekkk.setText(selested.toString()+" selected");
                            Toast.makeText(MainActivity.this,selested.toString()+" selected",
                                    Toast.LENGTH_LONG).show();
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }
                    }
                });

                break;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id){
            case CUSTOM_DIALOG_ID:
                ListDir(curFolder);
                break;
        }
    }

    void ListDir(File f){
        if (f.equals(root)){
            buttonUp.setEnabled(false);
        }else {
            buttonUp.setEnabled(true);
        }

        curFolder = f;
        textFolder.setText(f.getPath());//++++++++++++++++++++++++++++++++

        File[] files = f.listFiles();
        fileList.clear();

        for (File file : files){
            fileList.add(file.getPath());//+++++++++++++++++++++++++++++++++++ getPath
        }
        ArrayAdapter<String> directorylist = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,fileList);
        dialog_listView.setAdapter(directorylist);
    }
}
