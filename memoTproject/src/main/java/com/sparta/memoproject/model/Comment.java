package com.sparta.memoproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.memoproject.Timestamped;
import com.sparta.memoproject.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //GenerationType.IDENTITY : ID값이 서로 영향없이 자기만의 테이블 기준으로 올라간다.
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String memberName;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "MEMO_ID", nullable = false)
    private Memo memo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<Heart> heartlist = new ArrayList<>();

    @OneToMany(mappedBy = "parent", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> children = new ArrayList<>();

//    @Builder
//    public Comment(String author, String content, Authority authority) { //
//        this.author = author;
//        this.content = content;
//        this.authority = authority;
//    }

    public Comment(Memo memo, String memberName, CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
        this.memo = memo;
        this.memberName = memberName;
    }
    public Comment(Comment comment, String memberName, CommentRequestDto commentRequestDto){
        this.memo = comment.getMemo();
        this.content = commentRequestDto.getContent();
        this.parent = comment;
        this.memberName = memberName;
    }

    public void setComment(
            CommentRequestDto commentRequestDto
    ) {
        this.content = commentRequestDto.getContent();
    }
    //memo.addComment(comment);
    public void addComment(
            Comment childcomment
    ){
      this.children.add(childcomment);
    }

    public void addHeart(Heart heart) {
        heartlist.add(heart);
    }

    public void deleteHeart(Heart heart) {
        heartlist.remove(heart);
    }
}
