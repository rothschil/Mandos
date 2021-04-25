package xyz.wongs.weathertop.java.concurrent.completable;

import java.io.Serializable;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName UserInfo
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/25 8:53
 * @Version 1.0.0
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -3372444913908418915L;
    private Integer id;
    private String name;
    private Integer jobId;
    private String jobDes;
    private Integer carId;
    private String carDes;
    private Integer homeId;
    private String homeDes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobDes() {
        return jobDes;
    }

    public void setJobDes(String jobDes) {
        this.jobDes = jobDes;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarDes() {
        return carDes;
    }

    public void setCarDes(String carDes) {
        this.carDes = carDes;
    }

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    public String getHomeDes() {
        return homeDes;
    }

    public void setHomeDes(String homeDes) {
        this.homeDes = homeDes;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobId=" + jobId +
                ", jobDes='" + jobDes + '\'' +
                ", carId=" + carId +
                ", carDes='" + carDes + '\'' +
                ", homeId=" + homeId +
                ", homeDes='" + homeDes + '\'' +
                '}';
    }
}
