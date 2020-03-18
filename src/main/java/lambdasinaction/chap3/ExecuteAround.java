package lambdasinaction.chap3;

import java.io.*;
import java.net.URLDecoder;

/**
 * <b>概要：</b>:
 * 	8.1.5 环绕执行
 * <b>作者：</b>SUXH</br>
 * <b>日期：</b>2020/3/17 21:01 </br>
 * @param:
 * @return:
 */
public class ExecuteAround {

	public static void main(String ...args) throws IOException{
		String txtAbsolutePath = ExecuteAround.class.getResource("/lambdasinaction/chap3/data.txt").getPath();
		txtAbsolutePath = URLDecoder.decode(txtAbsolutePath, "utf-8");

		// method we want to refactor to make more flexible
		String result = processFileLimited(txtAbsolutePath);
		System.out.println(result);

		System.out.println("---");

		String oneLine = processFile(txtAbsolutePath,(BufferedReader b) -> b.readLine());
		System.out.println(oneLine);
		String twoLines = processFile(txtAbsolutePath, (BufferedReader b) -> b.readLine() + b.readLine());
		System.out.println(twoLines);

	}

    public static String processFileLimited(String filePath) throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {
            return br.readLine();
        }
    }

	/**
	 * <b>概要：</b>:
	 * 		执行读取文件操作
	 * <b>作者：</b>SUXH</br>
	 * <b>日期：</b>2020/3/17 21:00 </br>
	 * @param filePath 文件路径
	 * @param processor 传递行为模式如何读取
	 * @return 读取内容
	 */
	public static String processFile(String filePath,BufferedReaderProcessor processor) throws IOException {
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
			return processor.process(br);
		}
	}

	/**
	 * <b>概要：</b>:
	 * 		定义一个函数式接口：只有一个抽象方法
	 * <b>作者：</b>SUXH</br>
	 * <b>日期：</b>2020/2/7 15:43 </br>
	 */
	@FunctionalInterface
	public interface BufferedReaderProcessor{
		public String process(BufferedReader b) throws IOException;
	}
}
