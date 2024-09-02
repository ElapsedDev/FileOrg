package me.elapsed.fileorg;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class FileOrg extends TimerTask {

    @Getter
    private static FileOrg instance;

    private String DOWNLOAD_PATH;
    private Map<String, String> extensions;
    protected Timer timerTask;

    public static void main(String[] args) {
        new FileOrg().init();
    }

    public void init() {

        System.out.println("_______________________________________");

        System.out.println("FileOrg");

        instance = this;

        this.DOWNLOAD_PATH = System.getProperty("user.home") + "\\Downloads";

        this.extensions = new HashMap<>();

        /**
         * Extension Whitelist Format
         * this.extensions.put("extension file name", "designated folder");
         */
        this.extensions.put("jar", "Jars");
        this.extensions.put("jpg", "Photos");
        this.extensions.put("png", "Photos");
        this.extensions.put("pdf", "PDF Files");
        this.extensions.put("mp4", "Videos");
        this.extensions.put("webp", "WEBPs");
        this.extensions.put("gif", "GIFS");

        // Init the Timer
        this.timerTask = new Timer();

        // Init the Scheduled Timer
        this.timerTask.scheduleAtFixedRate(this, 1000 * 5, 1000 * 5);

        System.out.println("_______________________________________");

        setup();
    }

    public void setup() {

        for (String fileName : this.extensions.values()) {

            File file = new File(this.DOWNLOAD_PATH + "/" + fileName);

            // Skipping if the directory exists
            if (file.exists()) continue;

            System.out.println(file.mkdirs() ? fileName + " directory created!" : fileName + " directory failed to create...");
        }
    }

    public void run() {

        int counter = 0;

        for (File file : new File(this.DOWNLOAD_PATH).listFiles()) {

            // Skipping if the file object is a directory
            if (file.isDirectory()) continue;

            // A safety check for file extension
            if (!file.getName().contains(".")) continue;

            // Checking if the file extension is whitelisted
            if (!this.extensions.containsKey(getFileExtension(file.getName()))) continue;

            // Grabbing the paths for the file and target
            Path source = Paths.get(file.getPath());
            Path target = Paths.get(this.DOWNLOAD_PATH + "\\" + this.extensions.get(getFileExtension(file.getName())));

            // Moving the files
            try {
                Files.move(source, target.resolve(source.getFileName()));
                System.out.println("We have moved " + file.getName());
            } catch (IOException e) {
                System.out.println("Failed to move file " + file.getName());
            }

            counter++;
        }

        System.out.println("We have moved " + counter + " files to their specific folders!");
        System.exit(0);
    }

    public String getFileExtension(String file) {
        String[] index = file.split("\\.");
        return index[index.length - 1];
    }

}
