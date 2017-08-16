package com.vruzeda.wanikani.api.model;

import java.io.Serializable;
import java.util.Date;

public class StudyQueue implements Serializable {

    private int lessonsAvailable;
    private int reviewsAvailable;
    private Date nextReviewDate;
    private int reviewsAvailableNextHour;
    private int reviewsAvailableNextDay;

    public StudyQueue(int lessonsAvailable, int reviewsAvailable, Date nextReviewDate, int reviewsAvailableNextHour, int reviewsAvailableNextDay) {
        this.lessonsAvailable = lessonsAvailable;
        this.reviewsAvailable = reviewsAvailable;
        this.nextReviewDate = nextReviewDate;
        this.reviewsAvailableNextHour = reviewsAvailableNextHour;
        this.reviewsAvailableNextDay = reviewsAvailableNextDay;
    }

    public int getLessonsAvailable() {
        return lessonsAvailable;
    }

    public int getReviewsAvailable() {
        return reviewsAvailable;
    }

    public Date getNextReviewDate() {
        return nextReviewDate;
    }

    public int getReviewsAvailableNextHour() {
        return reviewsAvailableNextHour;
    }

    public int getReviewsAvailableNextDay() {
        return reviewsAvailableNextDay;
    }
}
