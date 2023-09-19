import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String originalFilePath = "D:\\code\\Java\\homework2\\orig.txt"; // 原文文件路径
        String plagiarismFilePath = "D:\\code\\Java\\homework2\\orig_add.txt"; // 抄袭版论文文件路径
        String outputFilePath = "D:\\code\\Java\\homework2\\output.txt"; // 输出结果文件路径

        try {
            // 读取原文文件和抄袭版论文文件的内容
            String originalContent = readFileContent(originalFilePath);
            String plagiarismContent = readFileContent(plagiarismFilePath);

            // 计算重复率
            double similarity = calculateSimilarity(originalContent, plagiarismContent);

            // 将结果写入输出结果文件
            writeResult(outputFilePath, similarity);

            // 输出重复率
            System.out.println("检测完成，重复率为: " + String.format("%.2f", similarity));
        } catch (IOException e) {
            System.out.println("发生错误：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String readFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static double calculateSimilarity(String originalContent, String plagiarismContent) {
        // 在这里实现论文查重的算法
        // 这里使用简单的相似度计算方法，计算抄袭版论文与原文的重复率
        int originalLength = originalContent.length();
        int plagiarismLength = plagiarismContent.length();
        int commonLength = longestCommonSubstring(originalContent, plagiarismContent).length();
        return (double) (2 * commonLength) / (originalLength + plagiarismLength);
    }

    private static String longestCommonSubstring(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int maxLength = 0;
        int endIndex = 0;

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        endIndex = i - 1;
                    }
                }
            }
        }

        return str1.substring(endIndex - maxLength + 1, endIndex + 1);
    }

    private static void writeResult(String filePath, double similarity) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.format("%.2f", similarity));
        }
    }
}