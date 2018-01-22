package com.example.madhuri.multibhashiassignment.LessonModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ankitv1988 on 22/01/18.
 */

public class LessonDatum implements Parcelable{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("conceptName")
    @Expose
    private String conceptName;
    @SerializedName("pronunciation")
    @Expose
    private String pronunciation;
    @SerializedName("targetScript")
    @Expose
    private String targetScript;
    @SerializedName("audio_url")
    @Expose
    private String audioUrl;

    protected LessonDatum(Parcel in) {
        type = in.readString();
        conceptName = in.readString();
        pronunciation = in.readString();
        targetScript = in.readString();
        audioUrl = in.readString();
    }

    public static final Creator<LessonDatum> CREATOR = new Creator<LessonDatum>() {
        @Override
        public LessonDatum createFromParcel(Parcel in) {
            return new LessonDatum(in);
        }

        @Override
        public LessonDatum[] newArray(int size) {
            return new LessonDatum[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getTargetScript() {
        return targetScript;
    }

    public void setTargetScript(String targetScript) {
        this.targetScript = targetScript;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(conceptName);
        parcel.writeString(pronunciation);
        parcel.writeString(targetScript);
        parcel.writeString(audioUrl);
    }
}
