package kr.co.ezenac.ss.household.model;

/**
 * Created by Administrator on 2017-11-21.
 */

public class History {

    private Integer id;
    private Integer type;
    private Integer money;
    private Integer year;
    private Integer month;
    private Integer day;

    public History(Integer id, Integer type, Integer money, Integer year, Integer month, Integer day){
        this.id = id;
        this.money = money;
        this.type = type;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public History(Integer type, Integer money) {
        this.type = type;
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
