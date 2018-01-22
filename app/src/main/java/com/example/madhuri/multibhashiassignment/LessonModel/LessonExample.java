package com.example.madhuri.multibhashiassignment.LessonModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public class LessonExample implements Parcelable{

    @SerializedName("lesson_data")
    @Expose
    private List<LessonDatum> lessonData = null;

    public LessonExample(List<LessonDatum> lessonData) {
        this.lessonData = lessonData;
    }

    protected LessonExample(Parcel in) {
        lessonData = in.createTypedArrayList(LessonDatum.CREATOR);
    }

    public static final Creator<LessonExample> CREATOR = new Creator<LessonExample>() {
        @Override
        public LessonExample createFromParcel(Parcel in) {
            return new LessonExample(in);
        }

        @Override
        public LessonExample[] newArray(int size) {
            return new LessonExample[size];
        }
    };

    public List<LessonDatum> getLessonData() {
        return lessonData;
    }

    public void setLessonData(List<LessonDatum> lessonData) {
        this.lessonData = lessonData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(lessonData);
    }
}
