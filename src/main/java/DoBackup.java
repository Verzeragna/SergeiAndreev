import java.io.File;

public class DoBackup {
    public static void main(String[] args) {
        if(args.length==4) {
            CopyMethodsInterface cm = new CopyMethods();
            switch (args[0]) {
                case "1":
                    cm.firstMethod(args);
                    break;
                case "2":
                    cm.secondMethod(args);
                    break;
            }
        }
    }
}
