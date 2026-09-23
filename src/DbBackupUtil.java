import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DbBackupUtil {
    //备份 mysqldump
    public static boolean backup(String savePath) throws Exception {
        String cmd = "mysqldump -uroot -proot cangchu -r " + savePath;
        Process p = Runtime.getRuntime().exec(cmd);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String line;
        while ((line = br.readLine()) != null) {}
        int res = p.waitFor();
        return res == 0;
    }

    //恢复 mysql
    public static boolean restore(String filePath) throws Exception {
        String cmd = "mysql -uroot -proot cangchu < " + filePath;
        Process p = Runtime.getRuntime().exec(cmd);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String line;
        while ((line = br.readLine()) != null) {}
        int res = p.waitFor();
        return res == 0;
    }
}