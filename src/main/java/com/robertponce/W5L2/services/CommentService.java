package com.robertponce.W5L2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robertponce.W5L2.models.Comment;
import com.robertponce.W5L2.repositories.CommentRepository;


@Service
public class CommentService {
	
	
	@Autowired
	private CommentRepository cRepo;

	public Comment createComment(Comment c) {
		
		return cRepo.save(c);
		
	}
	
	public List<Comment> getAllComments(){
		return cRepo.findAll();
	}
	
	public Comment getOneComment(Long id) {
		Optional<Comment> exists = cRepo.findById(id);
		
		if(exists.isEmpty()) {
			return null;
		}
		
		return exists.get();
	}
	
	public void deleteOne(Long id) {
		cRepo.deleteById(id);
	}
	
	
	
	
	
	
	
}
