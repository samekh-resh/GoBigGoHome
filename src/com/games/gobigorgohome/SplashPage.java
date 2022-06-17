package com.games.gobigorgohome;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class SplashPage {

    // read .txt files in resource folder
    public void dataFromFile(String filePath) throws IOException {

        InputStream stream = SplashPage.class.getClassLoader().getResourceAsStream(filePath);
        if (stream == null) {
            throw new IllegalArgumentException("File Not Found");
        }
        List<String> lines = new ArrayList<String>();
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        while((line=streamReader.readLine())!=null) {
            lines.add(line);
            System.out.println(line);
        }
    }


// read .jpg files from resources folder
//    public void imageFromFile(String filePath) throws IOException {
//
//        InputStream stream = SplashPage.class.getClassLoader().getResourceAsStream(filePath);
//        if (stream == null) {
//            throw new IllegalArgumentException("File Not Found");
//        }
//        try {
//            File imageFile = new File(filePath);
//            BufferedImage image = ImageIO.read(imageFile);
//            //BufferedReader streamReader = new BufferedReader(new InputStreamReader(stream));
//            //ImageIO.write(image, "jpg" ,new File(""));
//            //image = null;
//        //while((image=streamReader.read()   !=null) {
//            //images.add(line);
//            PrintStream image;
//        }
//        catch (IOException e){
//
//        }
//
//    }




    // display banner and instructions at the beggining of the game
    public void instructions() throws IOException {
        //String bannerPath = "banner.txt";
        dataFromFile("banner.txt");
        System.out.println("\033[33;1;2mWELCOME TO 'GO BIG OR GO HOME! v.1.1'\033[0m\nA game by \033[33;1;2mGAINZZZ Productions\033[0m where you maybe learn " +
                "to use your gym time wisely and get BIG!");
        System.out.println("\033[31;4;1mINSTRUCTIONS:\033[0m");
        dataFromFile("instructions.txt");
        System.out.println("\033[31;4;1m!!!IMPORTANT!!!:\033[0m");
        dataFromFile("instructions2.txt");
        dataFromFile("mapFD.txt");

    }

}


