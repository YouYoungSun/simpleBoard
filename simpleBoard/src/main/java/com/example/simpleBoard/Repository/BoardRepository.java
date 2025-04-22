package com.example.simpleBoard.Repository;

import com.example.simpleBoard.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Post,Long> {

}
