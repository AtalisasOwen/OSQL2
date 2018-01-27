package domain;

/**
 * Created by 顾文涛 on 2017/12/12.
 */
public class Temp {
    private Integer id;
    private Long hiTemp;
    public Temp(){}
    public Temp(Integer id, Long hiTemp) {
        this.id = id;
        this.hiTemp = hiTemp;
    }

    @Override
    public String toString() {
        return "Temp{" +
                "id=" + id +
                ", hiTemp=" + hiTemp +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getHiTemp() {
        return hiTemp;
    }

    public void setHiTemp(Long hiTemp) {
        this.hiTemp = hiTemp;
    }
}
