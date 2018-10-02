package leaderZhou;

public class Student {
    private String stuNum;
    private byte[] pic;
    private String stuClass;
    private String stuName;
    private String leaderName;

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public Student(String stuNum, String stuName, String stuClass, String leaderName) {
        this.stuNum = stuNum;
        this.stuClass = stuClass;
        this.stuName = stuName;
        this.leaderName = leaderName;
    }
}
