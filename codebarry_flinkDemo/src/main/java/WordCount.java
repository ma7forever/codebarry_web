import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import javax.sql.DataSource;
import java.lang.reflect.Executable;

/**
 * @author :cjh
 * @date : 16:46 2021/3/26
 */
//批处理word count
public class WordCount {
    public static void main(String[] args) {

        //创建执行环境
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        //从文件中获取数据
        String inputPath = "C:\\Users\\cjh\\IdeaProjects\\codebarry_web\\codebarry_flinkDemo\\src\\main\\resources\\hello.txt";
        DataSet<String> inputDataSet = environment.readTextFile(inputPath);

    }
}
