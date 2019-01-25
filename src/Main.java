import com.weibo.ui.MyFrame;
import org.python.util.InterpreterTest;
import org.python.util.PythonInterpreter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author CasterWx  AntzUhl
 * @site https://github.com/CasterWx
 * @company Henu
 * @create 2019-01-24-22:03
 */
public class Main {
    public static void main(String[] args) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("src\\com\\pythonMood\\run.py");

//        try {
//            RandomAccessFile randomAccessFile = new RandomAccessFile("outlog.txt","rw");
//            randomAccessFile.write("Î¢²©RSSÍÆËÍ\n".getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        MyFrame myFrame = new MyFrame("Weibo");
    }
}
