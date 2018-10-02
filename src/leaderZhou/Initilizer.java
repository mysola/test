package leaderZhou;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.rtf.RtfWriter2;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.util.*;

import static leaderZhou.StuInfReader.SUMOFLEADER;

public class Initilizer {
    private String basePath;

    private String collegeName;

    private String excelPath;

    private String resultPath;

    private Map<String, List<Student>> leaders;

    private List<Student> unOrderStudents;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    private static final short[] colors = {IndexedColors.AQUA.getIndex(),
            IndexedColors.BRIGHT_GREEN.getIndex(),
            IndexedColors.CORAL.getIndex(),
            IndexedColors.GOLD.getIndex()};

    public Initilizer(String basePath, String collegeName, String excelPath) {
        this.basePath = basePath;
        this.collegeName = collegeName;
        this.excelPath = excelPath;
    }

    public void init() throws Exception {
        //为每个导员挑选SUMOFLEADER名学生
        leaders = StuInfReader.readExcel2007AsStudent(basePath+File.separator+excelPath,readStuNumOfCollege());
        int sum = leaders.size()*SUMOFLEADER;
        unOrderStudents = new ArrayList<>(sum);
        byte[] pic = null;
        for(List<Student> stus: leaders.values()){
            unOrderStudents.addAll(stus);
            //为每个学生读取照片
            for(Student student : stus){
                InputStream is = new BufferedInputStream(new FileInputStream(
                        basePath+File.separator+collegeName+File.separator+student.getStuNum()+".jpg"));
                pic = new byte[is.available()];
                is.read(pic);
                student.setPic(pic);
            }
        }
        Random random = new Random();
        int next = 0;
        Student temp = null;
        //打乱顺序
        for(int i=0;i<sum;i++){
            next = random.nextInt(sum);
            temp = unOrderStudents.get(i);
            unOrderStudents.set(i,unOrderStudents.get(next));
            unOrderStudents.set(next,temp);
        }
        resultPath = basePath+File.separator+collegeName+"res";
        File allLeader = new File(resultPath+File.separator);
        if(!allLeader.mkdir()){
            return;
        }
    }

    public Set<String> readStuNumOfCollege() throws Exception{
        File collegeDir = new File(basePath+File.separator+collegeName);
        if(collegeDir.isDirectory()){
            Set<String> students = new HashSet<>(collegeDir.list().length);
            for(String s : collegeDir.list()){

                    students.add(s.substring(0,s.length()-4));

            }
            return students;
        }
        return null;
    }

    public void wirteAsFiles() throws Exception{
        for(Map.Entry<String,List<Student>> entry : leaders.entrySet()){
            File leader = new File(resultPath+File.separator
                    +entry.getKey()+File.separator);
            if(leader.mkdir()){
                for(Student student : entry.getValue()){
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(
                            leader.getPath() +File.separator+student.getStuNum()+".jpg"));
                    os.write(student.getPic());
                    os.close();
                }
            }
        }
    }

    public Map<String,Short> setColorForLeader(){
        Map<String,Short> result = new HashMap<>(4);
        int index = 0;
        for(Map.Entry<String, List<Student>> leader : leaders.entrySet()){
            result.put(leader.getKey(),colors[index++]);
        }
        return result;
    }

    public void wirteAsWord() throws Exception{

        // 创建word文档,并设置纸张的大小
        Document doc = new Document(PageSize.A4);
        StringBuilder fileName = new StringBuilder(resultPath+File.separator+"试题(");
        for(String s : leaders.keySet()){
            fileName.append(s+"、");
        }
        fileName.deleteCharAt(fileName.length()-1);
        fileName.append(").doc");

        /**
         * 建立一个书写器与document对象关联,通过书写器可以将文档写入到输出流中
         */
        RtfWriter2.getInstance(doc, new BufferedOutputStream(new FileOutputStream(fileName.toString())));
        doc.open();
        // 在列中添加图片
        for(Student student : unOrderStudents){
            Image png = Image.getInstance(student.getPic());
            doc.add(png);
        }
        doc.close();
    }

    public void wirteAsExcel() throws Exception{
        //为每位导员生成颜色
        Map<String,Short> colorOfLeader = setColorForLeader();

        // 第一步：创建workbook工作簿（excel文档）
        // 创建文件输出流
        FileOutputStream out = new FileOutputStream(resultPath+File.separator+"答案.xlsx");

        // 第一步：创建一个工作簿 -1：关闭自动刷新
        // SXSSFWorkbook wb = new SXSSFWorkbook(XXXX);（自动刷新） 保持内存中有XXXX条记录，超过部分写入磁盘
        SXSSFWorkbook wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows

        // 第二步：创建一个工作表 创建一个sheet
        Sheet sh = wb.createSheet();
        int rowSum = leaders.keySet().size()*SUMOFLEADER%10==0?leaders.keySet().size()*SUMOFLEADER/10:leaders.keySet().size()*SUMOFLEADER/10+1;
        String leaderName = null;
        for (int rownum = 0; rownum < rowSum; rownum++) {
            //第三步：在sheet中创建行  创建一个行
            Row row0 = sh.createRow(rownum*4);
            for (int cellnum = 0; cellnum < 10; cellnum++) {
                if(rownum*10+cellnum>=leaders.keySet().size()*SUMOFLEADER)
                    break;
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                //第四步：创建单元格
                Cell cell = row0.createCell(cellnum);
                //第五步：向单元格中写数据
                cell.setCellValue(rownum*10+cellnum+1);
                cell.setCellStyle(cellStyle);
            }

            Row row1 = sh.createRow(rownum*4+1);
            for (int cellnum = 0; cellnum < 10; cellnum++) {
                if(rownum*10+cellnum>=leaders.keySet().size()*SUMOFLEADER)
                    break;
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

                //第四步：创建单元格
                Cell cell = row1.createCell(cellnum);
                //第五步：向单元格中写数据
                leaderName = unOrderStudents.get(rownum*10+cellnum).getLeaderName();
                cell.setCellValue(leaderName);
                cellStyle.setFillForegroundColor(colorOfLeader.get(leaderName));
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(cellStyle);
            }

            Row row2 = sh.createRow(rownum*4+2);
            for (int cellnum = 0; cellnum < 10; cellnum++) {
                if(rownum*10+cellnum>=leaders.keySet().size()*SUMOFLEADER)
                    break;

                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                //第四步：创建单元格
                Cell cell = row2.createCell(cellnum);
                //第五步：向单元格中写数据
                cell.setCellValue(unOrderStudents.get(rownum*10+cellnum).getStuName());
                cell.setCellStyle(cellStyle);
            }

            Row row3 = sh.createRow(rownum*4+3);
            for (int cellnum = 0; cellnum < 10; cellnum++) {
                if(rownum*10+cellnum>=leaders.keySet().size()*SUMOFLEADER)
                    break;

                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                //第四步：创建单元格
                Cell cell = row3.createCell(cellnum);
                //第五步：向单元格中写数据
                cell.setCellValue(unOrderStudents.get(rownum*10+cellnum).getStuClass());
                cell.setCellStyle(cellStyle);
            }
        }
        //第七步：输出文件
        wb.write(out);// 将临时文件合并，写入最终文件

        out.close();
    }

    public static void main(String[] args) throws Exception {
        String basePath = "E:\\leaderZhou\\1";
        Initilizer initilizer = new Initilizer("","","");
        File leaderDir = new File(basePath);
        for(File college : leaderDir.listFiles()){
            for(File index : college.listFiles()){
                initilizer.setBasePath(index.getAbsolutePath());
                for(File f : index.listFiles()){
                    if(f.isDirectory())
                        initilizer.setCollegeName(f.getName());
                    if(f.getName().indexOf("xlsx")!=-1)
                        initilizer.setExcelPath(f.getName());
                }
                initilizer.init();
                initilizer.wirteAsFiles();
                initilizer.wirteAsWord();
                initilizer.wirteAsExcel();
            }
        }

    }


}
