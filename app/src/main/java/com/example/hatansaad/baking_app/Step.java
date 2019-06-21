package com.example.hatansaad.baking_app;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {

    private int stepId;
    private String stepShortDescription;
    private String stepLongDescription;
    private String stepVideoUrl;
    private String stepThumbnailUrl;

    public Step() { }

    public Step(int stepId,String stepShortDescription,String stepLongDescription,String stepVideoUrl){
        this.stepId=stepId;
        this.stepShortDescription=stepShortDescription;
        this.stepLongDescription=stepLongDescription;
        this.stepVideoUrl=stepVideoUrl;
    }

    protected Step(Parcel in) {
        stepId = in.readInt ();
        stepShortDescription = in.readString ();
        stepLongDescription = in.readString ();
        stepVideoUrl = in.readString ();
        stepThumbnailUrl = in.readString ();
    }

    public static final Creator<Step> CREATOR = new Creator<Step> () {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step (in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public void setStepId(int stepId) { this.stepId = stepId; }
    public void setStepLongDescription(String stepLongDescription) { this.stepLongDescription = stepLongDescription; }
    public void setStepShortDescription(String stepShortDescription) { this.stepShortDescription = stepShortDescription; }
    public void setStepThumbnailUrl(String stepThumbnailUrl) { this.stepThumbnailUrl = stepThumbnailUrl; }
    public void setStepVideoUrl(String stepVideoUrl) { this.stepVideoUrl = stepVideoUrl; }

    public int getStepId() { return stepId; }
    public String getStepLongDescription() { return stepLongDescription; }
    public String getStepShortDescription() { return stepShortDescription; }
    public String getStepVideoUrl() { return stepVideoUrl; }
    public String getStepThumbnailUrl() { return stepThumbnailUrl; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt (stepId);
        dest.writeString (stepLongDescription);
        dest.writeString (stepShortDescription);
        dest.writeString (stepVideoUrl);
        dest.writeString (stepThumbnailUrl);
    }
}
