package volunteersservice.models.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "VolunteersService.Volunteers")
public class Volunteers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VolunteerID")
    private int volunteerID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ReportID", nullable = false)
    private SecondPartOfReport report;

    @Column(name = "FIO", nullable = false)
    @NotNull
    private String FIO;

    @Column(name = "WhatWasDone")
    private String whatWasDone;

    @Column(name = "Role")
    private String role;

    public Volunteers(SecondPartOfReport report, @NotNull String FIO, String whatWasDone, String role) {
        this.report = report;
        this.FIO = FIO;
        this.whatWasDone = whatWasDone;
        this.role = role;
    }

    public SecondPartOfReport getReport() {
        return report;
    }

    public void setReport(SecondPartOfReport report) {
        this.report = report;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getWhatWasDone() {
        return whatWasDone;
    }

    public void setWhatWasDone(String whatWasDone) {
        this.whatWasDone = whatWasDone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
