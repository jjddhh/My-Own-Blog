package com.example.blog.entity;

import com.example.blog.dto.Req.ReqUpdateBoardDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    @ColumnDefault("0")
    private int count;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<Reply> replyList;

    @CreationTimestamp
    private Timestamp createDate;

    public void updateBoard(ReqUpdateBoardDto reqUpdateBoardDto) {
        this.title = reqUpdateBoardDto.getTitle();
        this.content = reqUpdateBoardDto.getContent();
    }
}
