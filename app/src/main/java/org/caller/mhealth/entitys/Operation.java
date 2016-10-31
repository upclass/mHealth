package org.caller.mhealth.entitys;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xsm on 16-10-31.
 */


public class Operation implements Parcelable{
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;//疾病名称
    @SerializedName("img")
    private String img;//图片
    @SerializedName("department")
    private String department;//疾病科室
    @SerializedName("place")
    private String place;//疾病部位
    @SerializedName("message")
    private String message;//简介，摘要
    @SerializedName("keywords")
    private String keywords;
    @SerializedName("description")
    private String description;
    @SerializedName("symptomtext")
    private String symptomtext;//病状描述
    @SerializedName("symptom")
    private String symptom;//相关症状
    @SerializedName("drug")
    private String drug;//相关药品
    @SerializedName("drugtext")
    private String drugtext;//用药说明
    @SerializedName("food")
    private String food;//相关食品
    @SerializedName("foodtext")
    private String foodtext;//健康保健
    @SerializedName("causetext")
    private String causetext;//病因
    @SerializedName("checks")
    private String checks;//检测项目
    @SerializedName("checktext")
    private String checktext;//检测说明
    @SerializedName("disease")
    private String disease;//并发疾病
    @SerializedName("diseasetext")
    private String diseasetext;//并发症状说明
    @SerializedName("caretext")
    private String caretext;//预防护理
    @SerializedName("count")
    private int count;
    @SerializedName("rcount")
    private int rcount;//回复
    @SerializedName("fcount")
    private int fcount;//收藏


    protected Operation(Parcel in) {
        id = in.readLong();
        name = in.readString();
        img = in.readString();
        department = in.readString();
        place = in.readString();
        message = in.readString();
        keywords = in.readString();
        description = in.readString();
        symptomtext = in.readString();
        symptom = in.readString();
        drug = in.readString();
        drugtext = in.readString();
        food = in.readString();
        foodtext = in.readString();
        causetext = in.readString();
        checks = in.readString();
        checktext = in.readString();
        disease = in.readString();
        diseasetext = in.readString();
        caretext = in.readString();
        count = in.readInt();
        rcount = in.readInt();
        fcount = in.readInt();
    }

    public static final Creator<Operation> CREATOR = new Creator<Operation>() {
        @Override
        public Operation createFromParcel(Parcel in) {
            return new Operation(in);
        }

        @Override
        public Operation[] newArray(int size) {
            return new Operation[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSymptomtext() {
        return symptomtext;
    }

    public void setSymptomtext(String symptomtext) {
        this.symptomtext = symptomtext;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getDrugtext() {
        return drugtext;
    }

    public void setDrugtext(String drugtext) {
        this.drugtext = drugtext;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getFoodtext() {
        return foodtext;
    }

    public void setFoodtext(String foodtext) {
        this.foodtext = foodtext;
    }

    public String getCausetext() {
        return causetext;
    }

    public void setCausetext(String causetext) {
        this.causetext = causetext;
    }

    public String getChecks() {
        return checks;
    }

    public void setChecks(String checks) {
        this.checks = checks;
    }

    public String getChecktext() {
        return checktext;
    }

    public void setChecktext(String checktext) {
        this.checktext = checktext;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDiseasetext() {
        return diseasetext;
    }

    public void setDiseasetext(String diseasetext) {
        this.diseasetext = diseasetext;
    }

    public String getCaretext() {
        return caretext;
    }

    public void setCaretext(String caretext) {
        this.caretext = caretext;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(img);
        dest.writeString(department);
        dest.writeString(place);
        dest.writeString(message);
        dest.writeString(keywords);
        dest.writeString(description);
        dest.writeString(symptomtext);
        dest.writeString(symptom);
        dest.writeString(drug);
        dest.writeString(drugtext);
        dest.writeString(food);
        dest.writeString(foodtext);
        dest.writeString(causetext);
        dest.writeString(checks);
        dest.writeString(checktext);
        dest.writeString(disease);
        dest.writeString(diseasetext);
        dest.writeString(caretext);
        dest.writeInt(count);
        dest.writeInt(rcount);
        dest.writeInt(fcount);
    }
}
