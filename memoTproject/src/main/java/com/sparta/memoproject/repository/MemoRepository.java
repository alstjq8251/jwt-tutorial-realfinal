package com.sparta.memoproject.repository;

import com.sparta.memoproject.model.Heart;
import com.sparta.memoproject.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();/*다 찾아줘(findAll) 정렬해줘(By OrderBy)수정된 날짜를 기준으로 (ModifiedAt) 내림차순으로=최신순 (Desc)  // Memo클래스는
    Timestamped클래스를 상속하고 있고 Timestamped는 modifiedAt을 필드에 멤버 변수로 갖고 있기 때문에 쓸 수 있다.*/

    List<Memo> findAllByMemberName(String nickname);

    List<Memo> findAllById(Long id);

// 처음에 조회할당시 게시글 갯수, 해당 게시글에 달린 댓글 갯수와 좋아요 갯수를 리턴한다~
    // count 는 하게되면 db에있는 pk개수를 리턴한다~ 없으면 0이다~
    // spring data jpa문법을 사용해서 query바꿔서 우리가 count 써야한다~~~


    //~~  좋아요~~ 댓글~~~
}