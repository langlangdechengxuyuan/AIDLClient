package com.example.aidlservice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yangchao on 2018/3/17.
 */

public class Person implements Parcelable {
    private Integer id;
    private String name;
    public Person() {
    }
    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
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
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 把javanbean中的数据写到Parcel
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }
    // 添加一个静态成员,名为CREATOR,该对象实现了Parcelable.Creator接口
    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            // 从Parcel中读取数据，返回person对象
            return new Person(source.readInt(), source.readString());
        }
        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
