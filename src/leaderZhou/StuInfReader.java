package leaderZhou;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class StuInfReader {

    private static Random random = new Random();

    public static final int SUMOFLEADER = 20;

    public static Set<Integer> generateRandom(int bound) {
        Set<Integer> set = new HashSet<>(SUMOFLEADER);
        while (set.size() < SUMOFLEADER) {
            Integer i = random.nextInt(bound);
            set.add(i);
        }
        return set;
    }

    public static Map<String, List<Student>> readExcel2007AsStudent(String path,Set<String> stuSet) throws IOException {

        //代表整个excel文件
        XSSFWorkbook wb = new XSSFWorkbook(new BufferedInputStream(new FileInputStream(path)));
        //第一个sheet表
        XSSFSheet sheet = wb.getSheetAt(0);
        //一行
        XSSFRow row = null;
        String stuLeader = null;

        Map<String, List<XSSFRow>> allStu = new HashMap<>();

        //遍历每一行
        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            try{
                if(row.getCell(0)==null)
                    break;
                if (!stuSet.contains(row.getCell(0).getStringCellValue().trim())||
                        !"在籍".equals(row.getCell(4).getStringCellValue().trim()))
                    continue;
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(i+" "+row.getCell(0));
            }
            stuLeader = row.getCell(3).getStringCellValue().trim();
            List<XSSFRow> leader = allStu.get(stuLeader);
            if (leader == null) {
                leader = new ArrayList<>(1500);
                allStu.put(stuLeader, leader);
            }
            leader.add(row);
        }
        return studentFilter(allStu);
    }

    public static Map<String, List<Student>> studentFilter(Map<String, List<XSSFRow>> rows) throws IOException {
        Map<String, List<Student>> leaders = new HashMap<>();
        List<Student> students = null;
        Set<Integer> randoms = null;
        XSSFRow row = null;
        String stuNum = null;
        String stuName = null;
        String stuClass = null;
        for (Map.Entry<String, List<XSSFRow>> entry : rows.entrySet()) {
            students = new ArrayList<>(SUMOFLEADER);
            randoms = generateRandom(entry.getValue().size());
            for (Integer i : randoms) {
                row = entry.getValue().get(i);
                stuNum = row.getCell(0).getStringCellValue().trim();
                stuName = row.getCell(1).getStringCellValue().trim();
                stuClass = row.getCell(2).getStringCellValue().trim();
                students.add(new Student(stuNum,stuName,stuClass,entry.getKey()));
            }
            leaders.put(entry.getKey(), students);
        }
        return leaders;
    }
}