package top.daisy.template.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 演示用对象
 */
public class Dept implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer deptId;
  private String deptName;
  private String deptInfo;
  private Date lastupdate;

  public Dept() {
  }

  public Dept(Integer deptId, String deptName, String deptInfo, Date lastupdate) {
    this.deptId = deptId;
    this.deptName = deptName;
    this.deptInfo = deptInfo;
    this.lastupdate = lastupdate;
  }

  public Integer getDeptId() {
    return deptId;
  }

  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getDeptInfo() {
    return deptInfo;
  }

  public void setDeptInfo(String deptInfo) {
    this.deptInfo = deptInfo;
  }

  public Date getLastupdate() {
    return lastupdate;
  }

  public void setLastupdate(Date lastupdate) {
    this.lastupdate = lastupdate;
  }
}
