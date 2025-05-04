package Elkhadema.khadema.domain;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Experience {
    private long id;
    private String description;
    private String mission;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String technologie;

    public Experience(long id, String description, String mission, String type, LocalDate startDate, LocalDate endDate,
            String technologie) {
        this.id = id;
        this.description = description;
        this.mission = mission;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.technologie = technologie;
    }

    public Experience(long id, String description, String mission, String type, String technologie) {
        this.description = description;
        this.mission = mission;
        this.type = type;
        this.technologie = technologie;
    }

    public Experience(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTechnologie() {
        return technologie;
    }

    public void setTechnologie(String technologie) {
        this.technologie = technologie;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDateExperience() {
        String dateString = getStartDate().format(DateTimeFormatter.ofPattern("MMM yyyy"));
        String tmp;
        LocalDate localDate = LocalDate.now();
        if (getEndDate() == null) {
            tmp = "present";
        } else {
            localDate = getEndDate();
            tmp = getEndDate().format(DateTimeFormatter.ofPattern("MMM yyyy"));
        }
        dateString = dateString.concat(" - " + tmp + "     ");
        Period period = Period.between(getStartDate(), localDate);
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        if (years != 0)
            dateString = dateString.concat(String.valueOf(years)).concat(" year").concat(years != 1 ? "s" : "")
                    .concat(" ");
        if (months != 0)
            dateString = dateString.concat(String.valueOf(months)).concat(" month").concat(months != 1 ? "s" : "")
                    .concat(" ");
        if (years > 0)
            dateString = dateString.concat(String.valueOf(days)).concat(" day").concat(days != 1 ? "s" : "");
        return dateString;
    }


}
