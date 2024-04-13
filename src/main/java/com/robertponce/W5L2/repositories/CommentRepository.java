package com.robertponce.W5L2.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.robertponce.W5L2.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	List<Comment> findAll();

}
