import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class  Logger{

    private final String logFileName = "log.txt";
    private static Logger logger;

    private Logger(){

    }

    public synchronized static Logger getLogger(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    public void writeToLogFile(String logMessage){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String logEntry = "[" + formatter.format(date) + "] " + logMessage + "\n";
        File logFile = new File(logFileName);
        try{
            if(logFile.createNewFile()) {
                Files.write(Paths.get(logFileName), logEntry.getBytes(), StandardOpenOption.WRITE);
            }
            else{
                Files.write(Paths.get(logFileName), logEntry.getBytes(), StandardOpenOption.APPEND);
            }
        }
        catch(Exception e){
            System.out.println("Failed to write to log file. Error: " + e.getMessage());
        }

    }
}
