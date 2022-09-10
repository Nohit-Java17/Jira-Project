package com.nohit.jira_project.model;

import javax.persistence.*;

import lombok.*;

import static javax.persistence.GenerationType.*;

@Entity(name = "subcribe")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Subcribe {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "thu_dien_tu")
    private String email;
}
